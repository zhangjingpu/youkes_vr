package com.youkes.vr.vr.play2d;

import java.io.IOException;
import java.nio.ByteBuffer;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

public class VideoPlayer {
	public static final int MEDIA_ENGINE_PLAY = 1;
	public static final int MEDIA_ENDINE_PAUSE = 2;
	public static final int MEDIA_ENGINE_STOP = 3;
	public static final int MEDIA_ENGINE_NEW = 0;
	
	private static final String TAG = "VideoPlayer";

	VideoSurfaceView mView;
	VideoMediaEngine mVideoPlayer;
	AudioMediaEngine mAudioPlayer;
	AudioTiming mAudioTiming;

	//Video Player includes 2 threads, one for video; the other for audio
	public VideoPlayer(VideoSurfaceView curSurface) {
		mView = curSurface;
		mVideoPlayer = null;
		mAudioPlayer = null;
	}
	
	public void start(String source) {
		//Create threads
 		mVideoPlayer = new VideoMediaEngine(mView.mViewSurface, source);
 		mAudioPlayer = new AudioMediaEngine(source);
 		
 		//This is the object for A/V sync.
 		mAudioTiming = new AudioTiming();

 		//Start running threads
		mVideoPlayer.start();
		mAudioPlayer.start();

	}

	public void stop() {
		
		//We need to make sure the thread is removed completely.
		if (mVideoPlayer != null && mAudioPlayer != null) {
			mVideoPlayer.stopEngine();
			mAudioPlayer.stopEngine();
			
			if (mVideoPlayer.isAlive()) {
				try 
				{
					Thread.sleep(500);
				} 
				catch (InterruptedException e) 
				{
				}
				
				mVideoPlayer.interrupt();
			}
			mVideoPlayer = null;
			
			if (mAudioPlayer.isAlive()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					mAudioPlayer.interrupt();
				}
				mAudioPlayer.interrupt();
			}
			mAudioPlayer = null;
		}
	}
	
	private class AudioTiming {
		long startMs;
		long realUs;
		boolean v_config_error;
		
		public AudioTiming() {
			startMs = 0;
			realUs = 0;
			v_config_error = false;
		}
	}

	//The video thread
	private class VideoMediaEngine extends Thread {
		MediaExtractor mVideoExtractor;
		MediaCodec mVideoDecoder;
		ByteBuffer[] mVideoInputBuffers;
		ByteBuffer[] mVideoOutputBuffers;
		String mFilePath = null;
		int mVideoState;
		
		public VideoMediaEngine(Surface surface, String filename) {
			mFilePath = filename;
			
			prepareEngine(surface);
			mVideoState = MEDIA_ENGINE_NEW;
		}
		
		public void stopEngine() {
			synchronized(this) {
				mVideoState = MEDIA_ENGINE_STOP;
			}
		}
		
		// Prepare the pipeline before running the decoder:
		// 1. Set the extractor to parse the video file
		// 2. Get the track count and setup the decoder
		// 3. Config the decoder with the surface created from the activity class.
		private void prepareEngine(Surface surface) {
			mVideoExtractor = new MediaExtractor();
			try {
				mVideoExtractor.setDataSource(mFilePath);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			int numTracks = mVideoExtractor.getTrackCount();
			
			for (int i = 0; i < numTracks; i++) {
				MediaFormat extratedMediaFormat = mVideoExtractor.getTrackFormat(i);
				String mime = extratedMediaFormat.getString(MediaFormat.KEY_MIME);
				
				if (mime.startsWith("video/")) {
					mVideoExtractor.selectTrack(i);
					try {
						mVideoDecoder = MediaCodec.createDecoderByType(mime);

						mVideoDecoder.configure(extratedMediaFormat, surface, null, 0);
						surface.release();
					} catch (IllegalArgumentException e) {
						Log.e(TAG, "Decoder configuration error!");
						mAudioTiming.v_config_error = true;
						return;
					} catch (IOException e) {

						return;
					}
					break;
				}
			}
		}

		@Override
		public void run() {
			
			if (mVideoDecoder == null) {
				Log.e(TAG, "VideoEngine: Can't find video info!");
				return;
			}			

			mVideoDecoder.start();
			
			mVideoInputBuffers = mVideoDecoder.getInputBuffers();
			mVideoOutputBuffers = mVideoDecoder.getOutputBuffers();
				
			BufferInfo vid_buf_info = new BufferInfo();
			boolean isVidEOS = false;
			int waitTime = 10000;
			
			//This is the main loop of playback
			while (!Thread.interrupted()) {
				synchronized(this) {
					if ( mVideoState == MEDIA_ENGINE_STOP) {
						break;
					}
				}
				
				if (!isVidEOS) {
					int inIdx = mVideoDecoder.dequeueInputBuffer(10000);
					if (inIdx >= 0) {
						ByteBuffer dstVidBuf = mVideoInputBuffers[inIdx];
						int sampleSize = mVideoExtractor.readSampleData(dstVidBuf, 0);
						if (sampleSize < 0) {
							Log.d(TAG, "VideoEngine: InputBuffer BUFFER_FLAG_END_OF_STREAM");
							mVideoDecoder.queueInputBuffer(inIdx, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
							isVidEOS = true;
						} else {
							mVideoDecoder.queueInputBuffer(inIdx, 0, sampleSize, mVideoExtractor.getSampleTime(), 0);
							mVideoExtractor.advance();
						}
					}
				}
				
				//Each time the decueueOutputBuffer() is called, one video frame is posted by the decoder
				final int outIdx = mVideoDecoder.dequeueOutputBuffer(vid_buf_info, waitTime);
				
				switch (outIdx) {
					case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
						Log.d(TAG, "VideoEngine: INFO_OUTPUT_BUFFERS_CHANGED");
						break;
					case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
						Log.d(TAG, "VideoEngine: New Format: " + mVideoDecoder.getOutputFormat());
						break;
					case MediaCodec.INFO_TRY_AGAIN_LATER:
						Log.d(TAG, "VideoEngine: dequeueOutputBuffer timed out");
						break;
					default:
						//This is the normal case, the AV sync algorithm is here, each time the time stamp is
						//calculated against the audio time stamp, if the video time is too late, the frame will be 
						// dropped; if the video time is too early, the waiting time will be added.
						ByteBuffer buffer = mVideoOutputBuffers[outIdx];
						long realVideoTimeUs = vid_buf_info.presentationTimeUs + mAudioTiming.startMs*1000;
						Log.v(TAG, "VideoEngine: We can't use this buffer but render it due to API limit," + buffer);
						int av_diff;

						synchronized(mAudioTiming) {
							av_diff = (int)(realVideoTimeUs - mAudioTiming.realUs - 300000);
						}
						
						if (av_diff > 15000) {
							try {
								sleep(av_diff/1000 + 15); // sleep more than 15 mil second
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
						} else if (av_diff < -15000) { //Drop frame as quick as possible to catch up
							mVideoDecoder.releaseOutputBuffer(outIdx, true);
							waitTime = 1000; //Set shorter wait time in order to catch up audio
							continue;
						} else {
							try {
								sleep(15); // sleep 15 mil second
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
						}
						waitTime = 10000; //The waiting time can be tuned to be more accurate
						mVideoDecoder.releaseOutputBuffer(outIdx, true);
						break;
				}
				
				if ((vid_buf_info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
					Log.d(TAG, "VideoEngine: OutputBuffer BUFFER_FLAG_END_OF_STREAM");
					break;
				}
			}
			
			mVideoDecoder.stop();
			mVideoDecoder.release();
			mVideoExtractor.release();
		}
	}
	
	private class AudioMediaEngine extends Thread {
		MediaExtractor mAudioExtractor;
		MediaCodec mAudioDecoder;
		ByteBuffer[] mAudioInputBuffers;
		ByteBuffer[] mAudioOutputBuffers;
		int audioTrackNum;
		MediaFormat mAudioFormat;
		AudioTrack mAudioTrack;
		String mFilePath;
		int mAudioState;
		
		public AudioMediaEngine(String filename) {
			audioTrackNum = 0;
			mFilePath = filename;
			mAudioState = MEDIA_ENGINE_NEW;
		}
		
		public void stopEngine() {
			synchronized(this) {
				mAudioState = MEDIA_ENGINE_STOP;
			}
		}

		@Override
		public void run() {
			mAudioExtractor = new MediaExtractor();
			try {
				mAudioExtractor.setDataSource(mFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int numTracks = mAudioExtractor.getTrackCount();
			
			for (int i = 0; i < numTracks; i++) {
				MediaFormat extratedMediaFormat = mAudioExtractor.getTrackFormat(i);
				String mime = extratedMediaFormat.getString(MediaFormat.KEY_MIME);
				
				if (mime.startsWith("audio/")) {
					audioTrackNum = i;
					mAudioFormat = extratedMediaFormat;
					try {
						mAudioDecoder = MediaCodec.createDecoderByType(mime);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Log.d(TAG, "AudioEngine: Created audio decoder, mime="+mime);
					mAudioDecoder.configure(extratedMediaFormat, null, null, 0);
					break;
				}
			}

			if (mAudioDecoder == null) {
				Log.e(TAG, "AudioEngine: Can't find audio info!");
				return;
			}
			
			mAudioDecoder.start();

			mAudioExtractor.selectTrack(audioTrackNum); //Select audio as the time standard
			mAudioInputBuffers = mAudioDecoder.getInputBuffers();
			mAudioOutputBuffers = mAudioDecoder.getOutputBuffers();

			int initSize = AudioTrack.getMinBufferSize(8000,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);
			int initSampleRate = mAudioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
			
			//Initialize the audio track, this allocates the hardware for audio stream playback
			mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 
					initSampleRate,
					AudioFormat.CHANNEL_OUT_STEREO,
					AudioFormat.ENCODING_PCM_16BIT,
					initSize,
					AudioTrack.MODE_STREAM);
			Log.i(TAG, "AudioEngine: Create AudioTrack, sample rate =" + initSampleRate + "; size = "+ initSize);
			
			BufferInfo aud_buf_info = new BufferInfo();

			boolean isAudEOS = false;
			mAudioTiming.startMs = System.currentTimeMillis();
			
			mAudioTrack.play();
			while (!Thread.interrupted()) {
				synchronized(this) {
					if (mAudioState == MEDIA_ENGINE_STOP) {
						break;
					}
				}
				mAudioExtractor.selectTrack(audioTrackNum);
				int inAudIdx = mAudioDecoder.dequeueInputBuffer(1000);
				if (inAudIdx >= 0) {
					ByteBuffer dstAudBuf = mAudioInputBuffers[inAudIdx];
					int audSampleSize = mAudioExtractor.readSampleData(dstAudBuf, 0);
					long presentationTimeUs = 0;
					if (audSampleSize < 0) {
						isAudEOS = true;
						audSampleSize = 0;
					} else {
						presentationTimeUs = mAudioExtractor.getSampleTime();
						mAudioTiming.realUs = presentationTimeUs + mAudioTiming.startMs*1000;
						Log.i("AVSync", "AudioEngine: AudioTimeStampUs = " + presentationTimeUs + "; mRealAudioUs = " + mAudioTiming.realUs);
					}
					mAudioDecoder.queueInputBuffer(inAudIdx, 
							0, 
							audSampleSize, 
							presentationTimeUs, 
							isAudEOS ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);
					
					if (!isAudEOS) {
						mAudioExtractor.advance();
					}
				}
				
				final int resAudio = mAudioDecoder.dequeueOutputBuffer(aud_buf_info, 10000);
				if (resAudio > 0) {
					int outAudBufIdx = resAudio;
					ByteBuffer outAudBuffer = mAudioOutputBuffers[outAudBufIdx];
					
					final byte[] chunk = new byte[aud_buf_info.size];
					outAudBuffer.get(chunk);
					outAudBuffer.clear();
					
					if (chunk.length > 0) {
						Log.i(TAG, "AudioEngine: Write one audio track");
						mAudioTrack.write(chunk, 0, chunk.length);
					}
					
					mAudioDecoder.releaseOutputBuffer(outAudBufIdx, false);
					
					if ((aud_buf_info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM)!=0) {
						isAudEOS = true;
					}
				} else if (resAudio == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
					mAudioOutputBuffers = mAudioDecoder.getOutputBuffers();
					Log.i(TAG, "AudioEngine: Output buffer changed, get output buffer");
				} else if (resAudio == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
					final MediaFormat oformat = mAudioDecoder.getOutputFormat();
					int newRate = oformat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
					mAudioTrack.setPlaybackRate(newRate);
					Log.i(TAG, "AudioEngine: Output format change, change sample rate: " + newRate);
				}
				
				//Stop the playback if the video engine was failed to configure.
				synchronized(mAudioTiming) {
					if (mAudioTiming.v_config_error) {
						mAudioTiming.v_config_error = false;
						Log.e(TAG, "AudioEngine stopped because the video engine configuration failed!");
						return;
					}
				}
			}
			
			mAudioDecoder.stop();
			mAudioTrack.stop();
			mAudioTrack.release();
			mAudioDecoder.release();
			mAudioExtractor.release();
		}
	}
}

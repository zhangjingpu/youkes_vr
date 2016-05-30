package com.youkes.vr.vr.sidebyside;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by xuming on 2016/4/15.
 */

public class VideoDisplay extends SurfaceView implements SurfaceHolder.Callback{

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

    AudioTiming mAudioTiming;
    public static final int MEDIA_ENGINE_PLAY = 1;
    public static final int MEDIA_ENDINE_PAUSE = 2;
    public static final int MEDIA_ENGINE_STOP = 3;
    public static final int MEDIA_ENGINE_NEW = 0;

    public final static String TAG="VideoDisplay";

    private static final int MAGIC_TEXTURE_ID = 10;
    public static final int DEFAULT_WIDTH=800;
    public static final int DEFAULT_HEIGHT=480;
    public static final int BLUR = 0;
    public static final int CLEAR = BLUR + 1;
    //public static final int PAUSE = PLAY + 1;
    //public static final int EXIT = PAUSE + 1;
    public SurfaceHolder gHolder;
    public SurfaceTexture gSurfaceTexture;
    //public Camera gCamera;
    public byte gBuffer[];
    public int textureBuffer[];

    private int bufferSize;
   // private Camera.Parameters parameters;
    public int previewWidth, previewHeight;
    public int screenWidth, screenHeight;
    public Bitmap gBitmap;
    private Rect gRect;
    // timer
    private Timer sampleTimer;
    private TimerTask sampleTask;
    public Surface mViewSurface;

    public VideoDisplay(Context context, int screenWidth, int screenHeight) {
        super(context);
        gHolder=this.getHolder();
        gHolder.addCallback(this);
        gHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        gSurfaceTexture=new SurfaceTexture(MAGIC_TEXTURE_ID);
        mViewSurface = new Surface(gSurfaceTexture);

        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        gRect=new Rect(0,0,screenWidth,screenHeight);
        Log.v(TAG, "GameDisplay initialization completed");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.v(TAG, "GameDisplay surfaceChanged");

        previewWidth = 200;
        previewHeight = 200;

        gBitmap= Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);
        //parameters.setPreviewSize(previewWidth, previewHeight);
       // gCamera.setParameters(parameters);
        bufferSize = previewWidth * previewHeight;
        textureBuffer=new int[bufferSize];
        bufferSize  = bufferSize ;
        gBuffer = new byte[bufferSize];


    }
    MediaCodec mVideoDecoder;
    MediaPlayer player;
    MediaExtractor mVideoExtractor=null;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(TAG, "GameDisplay surfaceCreated");


        mVideoExtractor = new MediaExtractor();
        try {
            mVideoExtractor.setDataSource("http://youkesfile.oss-cn-qingdao.aliyuncs.com/upload/public/1bba65a0da30ec3090820b428da56fa237cced33");
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

                    mVideoDecoder.configure(extratedMediaFormat, VideoDisplay.this.mViewSurface, null, 0);
                    this.mViewSurface.release();

                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Decoder configuration error!");
                    //mAudioTiming.v_config_error = true;
                    return;
                } catch (IOException e) {

                    return;
                }
                break;
            }
        }


        player=new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //player.setSurface(surface);
        //gSurfaceTexture.getH
        player.setDisplay(this.getHolder());
        //设置显示视频显示在SurfaceView上
        try {
            player.setDataSource("http://youkesfile.oss-cn-qingdao.aliyuncs.com/upload/public/1bba65a0da30ec3090820b428da56fa237cced33");
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(TAG, "GameDisplay surfaceDestroyed");
        player.stop();
        player.release();

    }

    //@Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.v(TAG, "GameDisplay onPreviewFrame");

        camera.addCallbackBuffer(gBuffer);


        for(int i=0;i<textureBuffer.length;i++)
        {
            int R=data[i]<<16;
            int G=data[i]<<8;
            int B=data[i];
            textureBuffer[i]=0xff000000|(R+G+B);
        }
        gBitmap.setPixels(textureBuffer, 0, previewWidth, 0, 0, previewWidth, previewHeight);
        synchronized (gHolder)
        {
            Canvas canvas = this.getHolder().lockCanvas();
            canvas.drawBitmap(gBitmap, null,gRect, null);
            this.getHolder().unlockCanvasAndPost(canvas);
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

            MediaCodec.BufferInfo vid_buf_info = new MediaCodec.BufferInfo();
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


    public void sampleStart() {
        Log.v(TAG, "GameDisplay sampleStart");
        sampleTimer = new Timer(false);
        sampleTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        sampleTimer.schedule(sampleTask,0, 80);
    }
}

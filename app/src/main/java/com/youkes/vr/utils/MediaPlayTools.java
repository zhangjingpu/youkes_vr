/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class MediaPlayTools {

	private static MediaPlayTools mInstance = null;
	MyHandler myHandler=null;
	public MediaPlayTools() {
		HandlerThread handlerThread = new HandlerThread("MediaPlayTools_Handler");
		handlerThread.start();
		 myHandler = new MyHandler(handlerThread.getLooper());
	}

	public void playVoice(String fileVoicePath, boolean b) {
		Message msg = myHandler.obtainMessage();
		//将msg发送到目标对象，所谓的目标对象，就是生成该msg对象的handler对象
		Bundle bundle= new Bundle();
		bundle.putInt("method", Method_Play);
		bundle.putString("path", fileVoicePath);
		bundle.putBoolean("b",b);
		msg.setData(bundle);
		//发送消息对象
		msg.sendToTarget();
		this.status = STATUS_PLAYING;;
	}


	class MyHandler extends Handler {

		public MyHandler(Looper looper){
			super(looper);
			MediaPlayTools2.getInstance().setOnVoicePlayCompletionListener(new MediaPlayTools2.OnVoicePlayCompletionListener() {
				@Override
				public void OnVoicePlayCompletion() {
					status = STATUS_STOP;

					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							doOnVoicePlayCompletion(); //Shows view
						}
					}, 500);


				}
			});

		}
		public void handleMessage(Message msg){
			Bundle b = msg.getData();
			int method = b.getInt("method");

			switch (method){
				case Method_Play:
					String path=b.getString("path");
					boolean ib=b.getBoolean("b");
					playMusic(path,ib);
					break;
				case Method_Stop:
					stopMusic();
					break;

			}

		}


		public void playMusic(String path,boolean b){
			MediaPlayTools2.getInstance().playVoice(path,b);
		}


		public void stopMusic(){
			MediaPlayTools2.getInstance().stop();
		}

	}

	private void doOnVoicePlayCompletion() {
		if (mListener != null) {
			mListener.OnVoicePlayCompletion();
		}

	}

	synchronized public static MediaPlayTools getInstance() {
		if (null == mInstance) {
			mInstance = new MediaPlayTools();
		}
		return mInstance;
	}


	public final static int Method_Play=1;
	public final static int Method_Stop=2;

	/**
	 * The definition of the state of play
	 * Play error
	 */
	private static final int STATUS_ERROR 				= -1;

	/**
	 * Stop playing
	 */
	private static final int STATUS_STOP 				= 0;

	/**
	 * Voice playing
	 */
	private static final int STATUS_PLAYING 			= 1;

	/**
	 * Pause playback
	 */
	private static final int STATUS_PAUSE 				= 2;

	private int status = 0;
	public boolean isPlaying () {

		if(this.status == STATUS_PLAYING) {
			return true;
		}

		return false;
	}

	final Handler handler = new Handler();

	public interface OnVoicePlayCompletionListener {
		void OnVoicePlayCompletion();
	}

	private OnVoicePlayCompletionListener mListener;

	public void setOnVoicePlayCompletionListener(OnVoicePlayCompletionListener l) {

		mListener = l;

	}

	public void stop() {

		Message msg = myHandler.obtainMessage();
		//将msg发送到目标对象，所谓的目标对象，就是生成该msg对象的handler对象
		Bundle b = new Bundle();
		b.putInt("method", Method_Stop);
		msg.setData(b);
		//发送消息对象
		msg.sendToTarget();
		this.status = STATUS_STOP;

	}
}

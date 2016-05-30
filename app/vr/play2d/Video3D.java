package com.youkes.vr.vr.play2d;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Menu;
import android.view.GestureDetector.OnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;

import com.youkes.vr.R;

//This activity depends on 2 objects:
// 1. A customized GLSurfaceView which can do specialized rendering like a spinning plan in 3D space.
// 2. A customized Video Player which parses the streams from the container and do the AV sync to make a smooth playback.
//
//This activity also implements the gesture listener to spin the video surface manually by user
public class Video3D extends Activity  implements OnGestureListener {
	// 3 run mode
	public static final int DEMO_DEFAULT = 0; //Normal video playback
	public static final int DEMO_SPIN = 1;    //The surface with the video playing will be spun randomly in the 3D space.
	public static final int DEMO_TOUCH = 3;   //The user can spin the video surface with finger
	
	private static final String VIDEO_PATH = "Current Video";
	//private static final String PERSIST_FILE = "my_pref.xml";
	private static final String TAG = "Video3D";
	//private SharedPreferences mPrefs;
	//SharedPreferences.Editor mPrefEditor;

	String mCurrentVideo = null;
	private GestureDetectorCompat mDetector;
	VideoPlayer mVideoPlayer;
	boolean mRestart = false;
	private VideoSurfaceView mVideoView;
	private int mScreenWidth;
	private int mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video3_d);
		
		//Instantiate the surface and the video player
		mVideoView = (VideoSurfaceView) findViewById(R.id.myplaysurface);
		
		//Save the screen size for late use when changing orientation
		Point ssize = new Point();
		getWindowManager().getDefaultDisplay().getRealSize(ssize);
		mScreenWidth = ssize.x;
		mScreenHeight = ssize.y;
		changeVideoViewSize(getResources().getConfiguration());
		
		//Create the video player
		mVideoPlayer = new VideoPlayer(mVideoView);
		mRestart = false;
		
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);

        //Get the saved the video file path, this will be null at the first time
        //mPrefs =getApplicationContext().getSharedPreferences(PERSIST_FILE, Context.MODE_PRIVATE);
        if (mCurrentVideo == null) {
	        mCurrentVideo = "http://youkesfile.oss-cn-qingdao.aliyuncs.com/upload/public/53b08360c7127da3b0dc466c4808ce3db74dcb54";
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video3_d, menu);
		return true;
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// We have turn on the programmable check for the orientation change
		// This function will be called when user rotate the device, we need
		// to change the view size according
		changeVideoViewSize(newConfig);
	}

	private void changeVideoViewSize(Configuration config) {
		// TODO There should be a way to get the parent layout padding programmable
		// The parent layout set the padding to be 16 in the layout file
		int padding_l = 16;
		LayoutParams params = (LayoutParams)mVideoView.getLayoutParams();
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			params.height = LayoutParams.WRAP_CONTENT;
			params.width = LayoutParams.WRAP_CONTENT;
		} else if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
			params.width = mScreenWidth - 2*padding_l;
			params.height = params.width*9/16;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.show_default:

			break;
		case R.id.show_randspin:

			break;
		case R.id.show_touch:

			break;
		case R.id.select_file:
			Intent intent = new Intent();
			
		    intent.setType("video/*");
		    intent.setAction(Intent.ACTION_GET_CONTENT);
		    
		    startActivityForResult(Intent.createChooser(intent, "Video File to Play"), 0);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		
		return true;
	}

	@Override
	protected void onRestart() {
		mRestart = true;
		super.onRestart();
	}

	@Override
	protected void onStart() {
		//When we got an null value from the saved video path, we need to start the media file chooser
		// to get the video clips in the current device.
		if (mCurrentVideo == null) {
			Intent intent = new Intent();
			
		    intent.setType("video/*");
		    intent.setAction(Intent.ACTION_GET_CONTENT);
		    
		    startActivityForResult(Intent.createChooser(intent, "Video File to Play"), 0);
		} else {
			if (mRestart) {
				//When resume from the background, the OpenGL surface needs to be recreated,
				// The resume call in the VideoSurfaceView object does this.
				mVideoView.onResume();
				mRestart = false;
			}
			mVideoPlayer.start(mCurrentVideo);
		}

		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		// Stop the playback and save the current path.
		mVideoPlayer.stop();
		//mPrefEditor = mPrefs.edit();
		//mPrefEditor.putString(VIDEO_PATH, mCurrentVideo);
		//mPrefEditor.commit();
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//This is need for the GestureDetector to work
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//This is the callback from the media file chooser
		if (requestCode == 0) {
		    if(resultCode == RESULT_OK) {      
		        Uri sourceUri = data.getData();
		        String source = getPath(sourceUri);
		        mCurrentVideo = source;
		         
		        mRestart = true;
		    }
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// This gesture is not used
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float speedX = velocityX/5000;
		Log.i(TAG, "onFling: VelocityX = " + speedX);
		//mVideoView.setAngleAccel(0, speedX);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// This gesture is not used
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.i(TAG, "onScroll: Distance = " + (e2.getX() - e1.getX())/300);
		//mVideoView.setAngleAccel((e2.getX() - e1.getX())/350, 0);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// This gesture is not used
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// This gesture is not used
		return false;
	}

	public String getPath(Uri uri) {
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			
		if (cursor == null) {
			return uri.getPath();
		} else { 
		    cursor.moveToFirst(); 
		        
		    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		    
		    return cursor.getString(idx); 
		}
	}
}

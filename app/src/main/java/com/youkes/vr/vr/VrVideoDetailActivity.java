/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.vr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.youkes.vr.R;
import com.youkes.vr.ui.OverflowAdapter;
import com.youkes.vr.ui.OverflowHelper;
import com.youkes.vr.ui.view.TopBarView;
import com.youkes.vr.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A test activity that renders a 360 video using {@link VrVideoView}.
 * It loads the video in the assets by default. User can use it to load any video files using the
 * command:
 *   adb shell am start -a android.intent.action.VIEW \
 *     -n com.google.vr.sdk.samples.simplevideowidget/.SimpleVrVideoActivity
 *     -d file://sdcard/FILENAME.MP4
 */
public class VrVideoDetailActivity extends Activity implements View.OnClickListener {
  private static final String TAG = VrVideoDetailActivity.class.getSimpleName();

  /**
   * Preserve the video's state when rotating the phone.
   */
  private static final String STATE_IS_PAUSED = "isPaused";
  private static final String STATE_PROGRESS_TIME = "progressTime";
  /**
   * The video duration doesn't need to be preserved, but it is saved in this example. This allows
   * the seekBar to be configured during {@link #onRestoreInstanceState(Bundle)} rather than waiting
   * for the video to be reloaded and analyzed. This avoid UI jank.
   */
  private static final String STATE_VIDEO_DURATION = "videoDuration";

  /**
   * Arbitrary constants and variable to track load status. In this example, this variable should
   * only be accessed on the UI thread. In a real app, this variable would be code that performs
   * some UI actions when the video is fully loaded.
   */
  public static final int LOAD_VIDEO_STATUS_UNKNOWN = 0;
  public static final int LOAD_VIDEO_STATUS_SUCCESS = 1;
  public static final int LOAD_VIDEO_STATUS_ERROR = 2;

  private int loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;

  public int getLoadVideoStatus() {
    return loadVideoStatus;
  }

  /** Tracks the file to be loaded across the lifetime of this app. **/
  private Uri fileUri;
  private VideoLoaderTask backgroundVideoLoaderTask;

  /**
   * The video view and its custom UI elements.
   */
  private VrVideoView videoWidgetView;

  /**
   * Seeking UI & progress indicator. The seekBar's progress value represents milliseconds in the
   * video.
   */
  private SeekBar seekBar;
  private TextView statusText;

  /**
   * By default, the video will start playing as soon as it is loaded. This can be changed by using
   * {@link VrVideoView#pauseVideo()} after loading the video.
   */
  private boolean isPaused = false;
    private String targetId="";
    TopBarView topBarView=null;
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.vr_video_layout);

      String title=getIntent().getStringExtra("title");
      TextView titleView=(TextView)findViewById(R.id.titleView);
      titleView.setText(title);

      targetId=getIntent().getStringExtra("id");
      String text=getIntent().getStringExtra("text");
      TextView contentTextView=(TextView)findViewById(R.id.contentTextView);
      contentTextView.setText(text);
      int backResId = R.drawable.topbar_back_bt;
      topBarView=(TopBarView)findViewById(R.id.topbar);

      topBarView.showSearch(false);
      topBarView.showLeftTextBtn(true);
      topBarView.setTitle(title);
      int menuResId = R.drawable.icon_topbar_add;
      topBarView.setTopBarToStatus(1, backResId,
              menuResId, title, this);
      topBarView.hideIcon();
      topBarView.showLeftTextBtn(false);
      if(StringUtils.isEmpty(targetId)) {
          topBarView.getRightButton().setVisibility(View.GONE);
      }else{
          if(getMenuList().size()==0){
              topBarView.getRightButton().setVisibility(View.GONE);
          }else {
              topBarView.getRightButton().setVisibility(View.VISIBLE);
          }

      }

      seekBar = (SeekBar) findViewById(R.id.seek_bar);
      seekBar.setOnSeekBarChangeListener(new SeekBarListener());
      statusText = (TextView) findViewById(R.id.status_text);


      initOverflowItems();

      // Bind input and output objects for the view.
      videoWidgetView = (VrVideoView) findViewById(R.id.video_view);
      videoWidgetView.setEventListener(new ActivityEventListener());
      videoWidgetView.setInfoButtonEnabled(false);
      videoWidgetView.setVrModeButtonEnabled(true);
      //videoWidgetView.
      //videoWidgetView.set
      //loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;

      // Initial launch of the app or an Activity recreation due to rotation.
      handleIntent(getIntent());
  }

    private OverflowAdapter.OverflowItem[] mItems = null;

    public ArrayList<String> getMenuList() {
        ArrayList<String> menus = new ArrayList<>();
        return menus;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                onBackPressed();
                break;

            case R.id.btn_right:
                hideSoftKeyboard();
                controlPlusSubMenu();
                break;




        }
    }

    private void controlPlusSubMenu() {
        if (mOverflowHelper == null) {
            return;
        }

        if (mOverflowHelper.isOverflowShowing()) {
            mOverflowHelper.dismiss();
            return;
        }

        mOverflowHelper.setOverflowItems(mItems);
        if (menuListener != null) {
            mOverflowHelper.setOnOverflowItemClickListener(menuListener);
            mOverflowHelper.showAsDropDown(findViewById(R.id.btn_right));
        }
    }

    private AdapterView.OnItemClickListener menuListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            controlPlusSubMenu();
            onMenuClick(position);
        }

    };

    protected void onMenuClick(int position) {

    }



    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = this.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    OverflowHelper mOverflowHelper=null;
    private void initOverflowItems() {
        mOverflowHelper = new OverflowHelper(this);
        ArrayList<String> menus = getMenuList();
        if (mItems == null) {
            int siz = menus.size();
            if (siz == 0) {
                return;
            }
            mItems = new OverflowAdapter.OverflowItem[siz];
        }
        for (int i = 0; i < mItems.length; i++) {
            mItems[i] = new OverflowAdapter.OverflowItem(menus.get(i));
        }
    }

    /**
   * Called when the Activity is already running and it's given a new intent.
   */
  @Override
  protected void onNewIntent(Intent intent) {
    Log.i(TAG, this.hashCode() + ".onNewIntent()");
    // Save the intent. This allows the getIntent() call in onCreate() to use this new Intent during
    // future invocations.
    setIntent(intent);
    // Load the new image.
    handleIntent(intent);
  }

  /**
   * Load custom videos based on the Intent or load the default video. See the Javadoc for this
   * class for information on generating a custom intent via adb.
   */
  private void handleIntent(Intent intent) {
    // Determine if the Intent contains a file to load.
    if (Intent.ACTION_VIEW.equals(intent.getAction())) {
      Log.i(TAG, "ACTION_VIEW Intent received");

      fileUri = intent.getData();
      if (fileUri == null) {
        Log.w(TAG, "No data uri specified. Use \"-d /path/filename\".");
      } else {
        Log.i(TAG, "Using file " + fileUri.toString());
      }
    } else {
      Log.i(TAG, "Intent is not ACTION_VIEW. Using the default video.");
      fileUri = null;
    }

      String videoUrl=getIntent().getStringExtra("video");
      if(StringUtils.isHttp(videoUrl)) {
          fileUri = Uri.parse(videoUrl);
          if (backgroundVideoLoaderTask != null) {
              backgroundVideoLoaderTask.cancel(true);
          }
          backgroundVideoLoaderTask = new VideoLoaderTask();
          backgroundVideoLoaderTask.execute(fileUri);
      }

  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putLong(STATE_PROGRESS_TIME, videoWidgetView.getCurrentPosition());
    savedInstanceState.putLong(STATE_VIDEO_DURATION, videoWidgetView.getDuration());
    savedInstanceState.putBoolean(STATE_IS_PAUSED, isPaused);
    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    long progressTime = savedInstanceState.getLong(STATE_PROGRESS_TIME);
    videoWidgetView.seekTo(progressTime);
    seekBar.setMax((int) savedInstanceState.getLong(STATE_VIDEO_DURATION));
    seekBar.setProgress((int) progressTime);

    isPaused = savedInstanceState.getBoolean(STATE_IS_PAUSED);
    if (isPaused) {
      videoWidgetView.pauseVideo();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Prevent the view from rendering continuously when in the background.
    videoWidgetView.pauseRendering();
    // If the video is playing when onPause() is called, the default behavior will be to pause
    // the video and keep it paused when onResume() is called.
    isPaused = true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Resume the 3D rendering.
    videoWidgetView.resumeRendering();
    // Update the text to account for the paused video in onPause().
    updateStatusText();
  }

  @Override
  protected void onDestroy() {
    // Destroy the widget and free memory.
    videoWidgetView.shutdown();
    super.onDestroy();
  }

  private void togglePause() {
    if (isPaused) {
      videoWidgetView.playVideo();
    } else {
      videoWidgetView.pauseVideo();
    }
    isPaused = !isPaused;
    updateStatusText();
  }

  private void updateStatusText() {
      //statusText.setText("");

    StringBuilder status = new StringBuilder();
    status.append(isPaused ? "暂停: " : "播放: ");
    status.append(String.format("%d", (int)(videoWidgetView.getCurrentPosition() / 1000f)));
    status.append(" / ");
    status.append(String.format("%d", (int)(videoWidgetView.getDuration() / 1000f)));
    status.append(" 秒");
    statusText.setText(status.toString());

  }

  /**
   * When the user manipulates the seek bar, update the video position.
   */
  private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      if (fromUser) {
        videoWidgetView.seekTo(progress);
        updateStatusText();
      } // else this was from the ActivityEventHandler.onNewFrame()'s seekBar.setProgress update.
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
  }

  /**
   * Listen to the important events from widget.
   */
  private class ActivityEventListener extends VrVideoEventListener  {
    /**
     * Called by video widget on the UI thread when it's done loading the video.
     */
    @Override
    public void onLoadSuccess() {
      Log.i(TAG, "Sucessfully loaded video " + videoWidgetView.getDuration());
      loadVideoStatus = LOAD_VIDEO_STATUS_SUCCESS;
      seekBar.setMax((int) videoWidgetView.getDuration());
      updateStatusText();
    }

    /**
     * Called by video widget on the UI thread on any asynchronous error.
     */
    @Override
    public void onLoadError(String errorMessage) {
      // An error here is normally due to being unable to decode the video format.
      loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
      Toast.makeText(
          VrVideoDetailActivity.this, "Error loading video: " + errorMessage, Toast.LENGTH_LONG)
          .show();
      Log.e(TAG, "Error loading video: " + errorMessage);
    }

    @Override
    public void onClick() {
      togglePause();
    }

    /**
     * Update the UI every frame.
     */
    @Override
    public void onNewFrame() {
      updateStatusText();
      seekBar.setProgress((int) videoWidgetView.getCurrentPosition());
    }

    /**
     * Make the video play in a loop. This method could also be used to move to the next video in
     * a playlist.
     */
    @Override
    public void onCompletion() {
      videoWidgetView.seekTo(0);
    }
  }

  /**
   * Helper class to manage threading.
   */
  class VideoLoaderTask extends AsyncTask<Uri, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Uri... uri) {
      try {
        if (uri == null || uri.length < 1 || uri[0] == null) {
          videoWidgetView.loadVideoFromAsset("congo.mp4");
        } else {
          videoWidgetView.loadVideo(uri[0]);
        }
      } catch (IOException e) {
        // An error here is normally due to being unable to locate the file.
        loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
        // Since this is a background thread, we need to switch to the main thread to show a toast.
        videoWidgetView.post(new Runnable() {
          @Override
          public void run() {
            Toast
                .makeText(VrVideoDetailActivity.this, "Error opening file. ", Toast.LENGTH_LONG)
                .show();
          }
        });
        Log.e(TAG, "Could not open video: " + e);
      }

      return true;
    }
  }
}

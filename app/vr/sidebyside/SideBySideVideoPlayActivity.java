
package com.youkes.vr.vr.sidebyside;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import com.youkes.vr.R;
import com.youkes.vr.utils.StringUtils;
import com.youkes.vr.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class SideBySideVideoPlayActivity extends Activity {

    private static final String TAG = SideBySideVideoPlayActivity.class.getSimpleName();
    private Uri mVideoUri;
    private android.widget.VideoView mAndroidVideoView;
    private VideoView mItecVideoView;
    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private MediaController mMediaController;
    private String mPath="";



    private boolean checkWifi() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // .getState();
        if (wifi.isConnected()) {
            return true;
        }
        ToastUtil.showMessage("wifi on:" + wifi.toString());
        return false;
    }


    String mTitle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_by_side);
        //Utils.setActionBarSubtitleEllipsizeMiddle(this);

        mAndroidVideoView = (android.widget.VideoView) findViewById(R.id.androidvv);
        mItecVideoView = (VideoView) findViewById(R.id.itecvv);

        mMediaPlayerControl = new MediaPlayerMultiControl(mAndroidVideoView, mItecVideoView);
        mMediaController = new MediaController(this);
        mMediaController.setAnchorView(findViewById(R.id.container));
        mMediaController.setMediaPlayer(mMediaPlayerControl);

        mPath = getIntent().getStringExtra("play");
        mTitle = getIntent().getStringExtra("play");
        if(StringUtils.isHttp(mPath)) {
            if (!checkWifi()) {
                ToastUtil.showMessage(getString(R.string.network_waste));
                wifiStateDlg();
                return;
            }
        }

        loadVideo();
    }

    private void loadVideo() {
        mVideoUri = Uri.parse(mPath);
        //getActionBar().setSubtitle(mTitle);

        // HACK: this needs to be deferred, else it fails when setting video on both players (it works when doing it just on one)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAndroidVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mAndroidVideoView.seekTo(0); // display first frame
                    }
                });
                mItecVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mItecVideoView.seekTo(0); // display first frame
                    }
                });

                mAndroidVideoView.setVideoURI(mVideoUri);
                mItecVideoView.setVideoURI(mVideoUri);
            }
        }, 1000);
    }


    private void wifiStateDlg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WIFI未打开").setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("是否继续播放视频?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadVideo();
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SideBySideVideoPlayActivity.this.finish();
            }
        }).setCancelable(true);

        builder.create().show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.side_by_side, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMediaController.show();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        mMediaController.hide();
        super.onStop();
    }

    private class MediaPlayerMultiControl implements MediaController.MediaPlayerControl {

        private List<MediaController.MediaPlayerControl> mControls;

        public MediaPlayerMultiControl(MediaController.MediaPlayerControl... controls) {
            mControls = new ArrayList<MediaController.MediaPlayerControl>();
            for(MediaController.MediaPlayerControl mpc : controls) {
                mControls.add(mpc);
            }
        }

        @Override
        public void start() {
            for(MediaController.MediaPlayerControl mpc : mControls) {
                mpc.start();
            }
        }

        @Override
        public void pause() {
            for(MediaController.MediaPlayerControl mpc : mControls) {
                mpc.pause();
            }
        }

        @Override
        public int getDuration() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).getDuration();
            }
            return 0;
        }

        @Override
        public int getCurrentPosition() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).getCurrentPosition();
            }
            return 0;
        }

        @Override
        public void seekTo(int pos) {
            for(MediaController.MediaPlayerControl mpc : mControls) {
                mpc.seekTo(pos);
            }
        }

        @Override
        public boolean isPlaying() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).isPlaying();
            }
            return false;
        }

        @Override
        public int getBufferPercentage() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).getBufferPercentage();
            }
            return 0;
        }

        @Override
        public boolean canPause() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).canPause();
            }
            return false;
        }

        @Override
        public boolean canSeekBackward() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).canSeekBackward();
            }
            return false;
        }

        @Override
        public boolean canSeekForward() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).canSeekForward();
            }
            return false;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public int getAudioSessionId() {
            if(!mControls.isEmpty()) {
                return mControls.get(0).getAudioSessionId();
            }
            return 0;
        }
    }
}

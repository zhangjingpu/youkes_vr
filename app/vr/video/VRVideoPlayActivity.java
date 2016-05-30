package com.youkes.vr.vr.video;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.utovr.player.UVEventListener;
import com.utovr.player.UVInfoListener;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;
import com.utovr.player.UVPlayerCallBack;
import com.utovr.player.UVReaderType;
import com.youkes.vr.R;
import com.youkes.vr.utils.StringUtils;

public class VRVideoPlayActivity extends Activity implements UVPlayerCallBack
{
    private UVMediaPlayer mMediaplayer = null;  // 媒体视频播放器
    private Handler handler = null;
    private ToggleButton playpauseBtn;          // 启动、暂停按钮
    protected SeekBar time_Seekbar;             // 播放进度条
    protected TextView time_TextView;           // 时间长度
    private String videoTimeString = null;      // 时间长度文本
    protected ToggleButton gyroBtn;             // 陀螺仪控制按钮
    protected ToggleButton screenBtn;           // 单双屏
    private PowerManager.WakeLock mWakeLock = null;
    private boolean bufferResume = true;
    private boolean needBufferAnim = false;
    private ImageView imgBuffer;                // 缓冲动画
    //private String Path = "/sdcard/wu.mp4";     // setSource UVMediaType.UVMEDIA_TYPE_MP4
    private String Path = ""; //setSource UVMediaType.UVMEDIA_TYPE_M3U8
    private String mediaType=MediaType_MP4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        handler = new Handler();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "mytag");
        mWakeLock.acquire();
        initView();
        //初始化播放器
        RelativeLayout rlPlayView = (RelativeLayout) findViewById(R.id.video_rlPlayView);
        mMediaplayer = new UVMediaPlayer(VRVideoPlayActivity.this, rlPlayView);
        //将工具条的显示或隐藏交个SDK管理，也可自己管理
        RelativeLayout rlToolbar = (RelativeLayout) findViewById(R.id.video_rlToolbar);
        mMediaplayer.setToolbar(rlToolbar, null, null);

        this.Path=getIntent().getStringExtra("play");
        this.mediaType=getIntent().getStringExtra("type");

        if(StringUtils.isEmpty(this.Path)){

        }
        //mMediaplayer.removeUtoVRLoadView();
    }

    public static final String MediaType_MP4="mp4";
    public static final String MediaType_M3U8="m3u8";


    @Override
    protected void onResume()
    {
        super.onResume();
        if (mMediaplayer != null)
        {
            mMediaplayer.onResume(VRVideoPlayActivity.this);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (mMediaplayer != null)
        {
            mMediaplayer.onPause();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mWakeLock.release();
        if (mMediaplayer != null) {
            mMediaplayer.release();
        }
    }

    /**
     * SDK已经将播放器环境已设置好，可以播放了
     */
    @Override
    public void createEnv(final Surface surface)
    {
        if (mMediaplayer != null && mMediaplayer.isInited())
        {
            mMediaplayer.setSurface(surface);
        }
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    // 创建媒体视频播放器
                    mMediaplayer.initPlayer();
                    mMediaplayer.setListener(mListener);
                    mMediaplayer.setSurface(surface);
                    mMediaplayer.setInfoListener(mInfoListener);//VR_360
                    try {
                        /********************  播放网络m3u8   *******************/
                        if(mediaType.equals(MediaType_MP4)) {
                            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
                        }else if(mediaType.equals(MediaType_M3U8)){
                            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_M3U8, Path);
                        }
                        needBufferAnim = true;



                    } catch (IllegalStateException t) {
                        Log.e("utovr", "media setSource failed");
                        t.printStackTrace();
                    }

                }
                catch (Exception e)
                {
                    Log.e("utovr", e.getMessage(), e);
                }
            }
        });
    }

    /**
     * @param CurPostion 播放进度
     */
    @Override
    public void updateProgress(long CurPostion)
    {
        Message msg = handleProgress.obtainMessage(0, (int)CurPostion, 0);
        handleProgress.sendMessage(msg);
    }

    private UVEventListener mListener = new UVEventListener()
    {
        @Override
        public void onRenderTypeChanged(UVReaderType uvReaderType)
        {

        }

        @Override
        public void onGyroCtrl(int i, String s)
        {
            switch (i)
            {
                case UVEventListener.GYRO_CTRL_PLAY:
                    playpauseBtn.setChecked(false);
                    break;
                case UVEventListener.GYRO_CTRL_PAUSE:
                    playpauseBtn.setChecked(true);
                    break;
            }
        }

        @Override
        public void onStateChanged(int playbackState)
        {
            Log.i("utovr", "+++++++ playbackState:" + playbackState);
            switch (playbackState)
            {
                case UVMediaPlayer.STATE_PREPARING:
                    break;
                case UVMediaPlayer.STATE_BUFFERING:
                    if (needBufferAnim && mMediaplayer != null && mMediaplayer.isPlaying()) {
                        bufferResume = true;
                        setBufferVisibility(true);
                    }
                    break;
                case UVMediaPlayer.STATE_READY:
                    // 设置时间和进度条
                    setInfo();

                    if (bufferResume)
                    {
                        bufferResume = false;
                        setBufferVisibility(false);
                    }
                    break;
                case UVMediaPlayer.STATE_ENDED:
                    //这里是循环播放，可根据需求更改
                    mMediaplayer.replay();
                    break;
                case UVMediaPlayer.TRACK_DISABLED:
                case UVMediaPlayer.TRACK_DEFAULT:
                    break;
            }
        }

        @Override
        public void onError(Exception e, int ErrType)
        {
            switch (ErrType)
            {
                case UVEventListener.ERR_TIMEOUT:
                    toast("网络超时");
                    break;
                case UVEventListener.ERR_INIT:
                case UVEventListener.ERR_RENDER_INIT:
                case UVEventListener.ERR_DECODE:
                    toast("检查代码setSource参数UVMediaType是否正确或不支持该视频格式");
                    break;
                case UVEventListener.ERR_WRITE:
                    toast("WriteError");
                    break;
                case UVEventListener.ERR_LOAD:
                    toast("获得数据失败");
                    break;
                default:
                    toast("onError");
                    break;
            }
        }

        @Override
        public void onVideoSizeChanged(int width, int height)
        {
        }
    };

    private UVInfoListener mInfoListener = new UVInfoListener()
    {
        @Override
        public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate)
        {
        }

        @Override
        public void onLoadStarted()
        {
        }

        @Override
        public void onLoadCompleted()
        {
            if (bufferResume)
            {
                bufferResume = false;
                setBufferVisibility(false);
            }
            /*
            * 缓冲进度
            * 这里比较偷懒的做法是利用m3u8分片原理更新进度，网络播放MP4这种做法肯定是不对的
            * 你完全可以创建一个定时器调用 mMediaplayer.getBufferedPosition()
            */
            time_Seekbar.setSecondaryProgress((int) mMediaplayer.getBufferedPosition());

        }
    };

    private void initView()
    {
        // 工具栏上的按钮
        gyroBtn = (ToggleButton) findViewById(R.id.video_toolbar_btn_gyro);// 陀螺仪
        screenBtn = (ToggleButton) findViewById(R.id.video_toolbar_btn_screen);// 单双屏
        playpauseBtn = (ToggleButton) findViewById(R.id.video_toolbar_btn_playpause);// 播放/暂停
        time_Seekbar = (SeekBar) findViewById(R.id.video_toolbar_time_seekbar);// 进度
        // 陀螺仪按钮事件
        gyroBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (mMediaplayer != null)
                {
                    mMediaplayer.setGyroEnabled(!mMediaplayer.isGyroEnabled());
                    gyroBtn.setChecked(mMediaplayer.isGyroEnabled());
                }
            }
        });
        // 单双屏按钮事件
        screenBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mMediaplayer != null)
                {
                    boolean isScreen = !mMediaplayer.isDualScreenEnabled();
                    mMediaplayer.setDualScreenEnabled(isScreen);
                    //mMediaplayer.set

                    if (isScreen)
                    {

                        gyroBtn.setChecked(true);
                        gyroBtn.setEnabled(false);
                    }
                    else
                    {
                        mMediaplayer.setGyroEnabled(false);
                        gyroBtn.setChecked(false);
                        gyroBtn.setEnabled(true);
                    }
                }
            }
        });
        // 播放/暂停按钮事件
        playpauseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    if (mMediaplayer != null && mMediaplayer.isInited())
                    {//暂停媒体视频
                        mMediaplayer.pause();
                    }
                } else {
                    if (mMediaplayer != null && mMediaplayer.isInited())
                    {//播放媒体视频
                        mMediaplayer.play();
                    }
                }
            }
        });
        // 进度条事件
        time_Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mMediaplayer != null && mMediaplayer.isInited())
                {
                    mMediaplayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }
        });
        imgBuffer = (ImageView) findViewById(R.id.video_imgBuffer);
        time_TextView = (TextView) findViewById(R.id.video_toolbar_time_tv);// 时间
    }

    /**
     * 设置时间和进度条初始信息
     */
    public void setInfo() {
        int duration = 0;
        if (mMediaplayer != null)
        {
            duration = (int)mMediaplayer.getDuration();
        }
        if (duration == time_Seekbar.getMax())
        {
            return;
        }
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
        time_Seekbar.setProgress(0);
        time_Seekbar.setMax(duration);
        videoTimeString = VRVideoUtils.getShowTime(duration);
        time_TextView.setText("00:00:00/" + videoTimeString);
    }

    private void toast(final String msg)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(VRVideoPlayActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //缓冲动画控制
    private void setBufferVisibility(boolean Visible)
    {
        if (Visible)
        {
            imgBuffer.setVisibility(View.VISIBLE);
            VRVideoUtils.startImageAnim(imgBuffer, R.anim.loading);
        }
        else
        {
            VRVideoUtils.stopImageAnim(imgBuffer);
            imgBuffer.setVisibility(View.GONE);
        }
    }

    /*******************************************************
     * 通过Handler来更新进度条
     ******************************************************/
    public Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = msg.arg1;
            if (position >= 0 && videoTimeString != null) {
                time_Seekbar.setProgress(position);
                // 设置播放时间
                String cur = VRVideoUtils.getShowTime(position);
                time_TextView.setText(cur + "/" + videoTimeString);
            }

        };
    };

}



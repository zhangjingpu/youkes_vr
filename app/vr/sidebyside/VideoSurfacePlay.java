package com.youkes.vr.vr.sidebyside;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.youkes.vr.R;

/**
 * Created by xuming on 2016/4/15.
 */
    public class VideoSurfacePlay extends Activity implements SurfaceHolder.Callback {

    private static final int MAGIC_TEXTURE_ID = 10;

        /** Called when the activity is first created. */
        MediaPlayer player;
        SurfaceView surface;
        SurfaceHolder surfaceHolder;
        Button play,pause,stop;

    public int previewWidth, previewHeight;
    public int screenWidth, screenHeight;
    private Rect gRect;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_play_2d_left_right);
            play=(Button)findViewById(R.id.button1);
            pause=(Button)findViewById(R.id.button2);
            stop=(Button)findViewById(R.id.button3);
            surface=(SurfaceView)findViewById(R.id.surface);

            //surface.getView
            surfaceHolder=surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
            surfaceHolder.addCallback(this);//因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
            surfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型

            play.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    player.start();
                }});
            pause.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    player.pause();
                }});
            stop.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    player.stop();
                }});

            gSurfaceTexture=new SurfaceTexture(MAGIC_TEXTURE_ID);
            this.screenWidth=200;
            this.screenHeight=200;
            gRect=new Rect(0,0,screenWidth,screenHeight);

        }


        public SurfaceTexture gSurfaceTexture;

        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder arg0) {
//必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
            player=new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //player.setSurface(surface);
            player.setDisplay(surfaceHolder);
            //设置显示视频显示在SurfaceView上
            try {
                player.setDataSource("http://youkesfile.oss-cn-qingdao.aliyuncs.com/upload/public/1bba65a0da30ec3090820b428da56fa237cced33");
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onDestroy() {
            // TODO Auto-generated method stub
            super.onDestroy();
            if(player.isPlaying()){
                player.stop();
            }
            player.release();
            //Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
        }
    }

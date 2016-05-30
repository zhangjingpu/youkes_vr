/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.vr.video;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xilin on 2015/12/14.
 */
public class VRVideoUtils
{
    public static String getShowTime(long milliseconds) {
        // 获取日历函数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat dateFormat = null;
        // 判断是否大于60分钟，如果大于就显示小时。设置日期格式
        if (milliseconds / 60000 > 60) {
            dateFormat = new SimpleDateFormat("hh:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("00:mm:ss");
        }
        return dateFormat.format(calendar.getTime());
    }

    public static void startImageAnim(ImageView Img, int anim)
    {
        Img.setVisibility(View.VISIBLE);
        try
        {
            Img.setImageResource(anim);
            AnimationDrawable animationDrawable = (AnimationDrawable) Img.getDrawable();
            animationDrawable.start();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
    }

    public static void stopImageAnim(ImageView Img)
    {
        try
        {
            AnimationDrawable animationDrawable = (AnimationDrawable) Img.getDrawable();
            animationDrawable.stop();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        Img.setVisibility(View.GONE);
    }
}

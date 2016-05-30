/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 重载接口增加设置主界面是否可以进行滑动
 * Created by Jorstin on 2015/3/18.
 */
public class CCPCustomViewPager extends ViewPager {

    /**
     * 控制页面是否可以左右滑动
     */
    private boolean mSlidenabled = true;
    /**
     * @param context
     */
    public CCPCustomViewPager(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CCPCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否可以滑动
     */
    public final void setSlideEnabled (boolean enabled) {
        mSlidenabled = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if(!mSlidenabled) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        if(!mSlidenabled) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

}


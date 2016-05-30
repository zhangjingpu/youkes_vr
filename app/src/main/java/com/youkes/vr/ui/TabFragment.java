/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui;

import android.view.KeyEvent;

/**
 * 自定义三个TabView 的Fragment ,将三个TabView共同属性方法
 * 统一处理
 * 三个TabView 滑动页面需要继承该基类，
 * Created by Jorstin on 2015/3/18.
 */
public abstract class TabFragment extends BaseFragment {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDestroy() {
        onReleaseTabUI();
        super.onDestroy();
    }

    /**
     * 当前TabFragment被点击
     */
    public abstract void onTabFragmentClick();

    /**
     * 当前TabFragment被释放
     */
    public abstract void onReleaseTabUI();

}


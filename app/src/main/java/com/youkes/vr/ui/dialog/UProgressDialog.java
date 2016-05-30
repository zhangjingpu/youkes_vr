/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;

import com.youkes.vr.R;


public class UProgressDialog extends Dialog {

    private TextView mTextView;
    //private View mImageView;
    AsyncTask mAsyncTask;

    private final OnCancelListener mCancelListener
            = new OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            if(mAsyncTask != null) {
                mAsyncTask.cancel(true);
            }
        }
        
    };

    /**
     * @param context
     */
    public UProgressDialog(Context context) {
        super(context , R.style.Theme_Light_CustomDialog_Blue);
        mAsyncTask = null;
        setCancelable(true);
        setContentView(R.layout.loading_box);
        mTextView = (TextView)findViewById(R.id.dialogText);
        mTextView.setText(R.string.loading_press);
        
        setOnCancelListener(mCancelListener);
    }

    /**
     * @param context
     * @param resid
     */
    public UProgressDialog(Context context, int resid) {
        this(context);
        mTextView.setText(resid);
    }

    public UProgressDialog(Context context, CharSequence text) {
        this(context);
        mTextView.setText(text);
        this.setCancelable(true);
    }

    public UProgressDialog(Context context, AsyncTask asyncTask) {
        this(context);
        mAsyncTask = asyncTask;
    }

    public UProgressDialog(Context context, CharSequence text, AsyncTask asyncTask) {
        this(context , text);
        mAsyncTask = asyncTask;
    }

    /**
     * 设置对话框显示文本
     * @param text
     */
    public final void setPressText(CharSequence text) {
        mTextView.setText(text);
    }

    public final void dismiss() {
        super.dismiss();
       
    }

    public final void show() {
        super.show();
        //Animation loadAnimation = AnimationUtils.loadAnimation(getContext() ,R.anim.loading);
      
    }
}

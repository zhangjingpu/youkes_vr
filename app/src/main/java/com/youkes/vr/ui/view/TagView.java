/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youkes.vr.MainApp;
import com.youkes.vr.R;


public class TagView extends LinearLayout {

	private Context mContext;


	/**
	 * @param context
	 */
	public TagView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}


	private TextView textView=null;
	private ImageView closeBtn=null;
	private void initView() {
		setOrientation(LinearLayout.HORIZONTAL);
		textView=new TextView(mContext);
		textView.setTextSize(16);
		textView.setPadding(4, 4, 4, 4);
		this.addView(textView);
		closeBtn=new ImageView(mContext);
		Drawable drawable = getResources().getDrawable(R.drawable.close5);
		closeBtn.setImageDrawable(drawable);
		//closeBtn.setBackgroundResource(R.drawable.select_button);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.gravity= Gravity.CENTER_VERTICAL;
		lp.setMargins(2,2,2,2);
		lp.width= MainApp.getInstance().fromDPToPix(24);
		lp.height= MainApp.getInstance().fromDPToPix(24);
		closeBtn.setLayoutParams(lp);
		closeBtn.setClickable(true);
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCloseClicked(v);
			}
		});


		this.addView(closeBtn);

		this.setBackgroundColor(getResources().getColor(R.color.white_lighter));

		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		lp1.setMargins(4, 4,4, 4);
		this.setLayoutParams(lp1);

	}

	interface OnClosedHandler{
		void onCloseClick(String tag,TagView v);
	}

	public void setCloseListener(OnClosedHandler closeListener) {
		this.closeListener = closeListener;
	}

	OnClosedHandler closeListener=null;
	private void onCloseClicked(View v) {
		if(closeListener!=null){
			closeListener.onCloseClick(tag,this);
		}
	}


	private String tag=null;
	public void setTagText(String tag){
		this.tag=tag;
		textView.setText(tag);
	}

}

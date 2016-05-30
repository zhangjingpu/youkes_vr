/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.youkes.vr.R;
import com.youkes.vr.utils.ToastUtil;

import java.util.ArrayList;


public class TagsView extends HorizontalScrollView {

	private Context mContext;

	public ArrayList<String> getTags() {
		return tags;
	}

	ArrayList<String> tags=new ArrayList<>();

	/**
	 * @param context
	 */
	public TagsView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TagsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}


	LinearLayout container=null;
	private void initView() {
		container=new LinearLayout(mContext);
		container.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(container);

	}


	public void addTagText(String tag){
		if(tags.contains(tag)){
			return;
		}

		if(tags.size()>=5){
			ToastUtil.showMessage(getContext().getString(R.string.tags_max_limit));
			return;
		}
		if(tag==null||tag.equals("")){
			return;
		}

		tags.add(tag);
		TagView t=new TagView(mContext);
		t.setTagText(tag);
		container.addView(t);
		t.setCloseListener(new TagView.OnClosedHandler() {
			@Override
			public void onCloseClick(String tag,TagView v) {
				if(tag==null||v==null){
					return;
				}
				container.removeView(v);
				tags.remove(tag);

			}
		});
	}

}

/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui;


import com.youkes.vr.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomMenuView extends LinearLayout {

	public BottomMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout root = (LinearLayout) inflater.inflate(
				R.layout.bottom_menu_layout, null);
		
		String[] menu = { "首页", "分类" };
		int len = menu.length;
		for (int i = 0; i < len; i++) {
			String m = menu[i];
			View view = inflater.inflate(R.layout.bottom_menu_item_highlight, null);
			TextView tv = (TextView) view.findViewById(R.id.bottom_menu_text);
			tv.setText(m);
			root.addView(view);
		}

		this.addView(root);
		
		this.requestLayout();
		this.invalidate();
		
	}

	public BottomMenuView(Context context) {
		this(context, null);
	}

}

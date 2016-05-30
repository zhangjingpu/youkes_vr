/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.youkes.vr.AppViewPagerActivity;
import com.youkes.vr.ChannelItem;
import com.youkes.vr.R;
import com.youkes.vr.pref.PreferenceUtils;


import java.util.ArrayList;

public class VideoChannelsListActivity extends AppViewPagerActivity {

	@Override
	public String getTitleString() {
		return getString(R.string.hobby_circle);
	}

	@Override
	public ArrayList<String> getMenuList() {
		ArrayList<String> menus = new ArrayList<>();
		if(!PreferenceUtils.isUserVisitor()) {

		}

		return menus;

	}

	@Override
	protected void onMenuClick(int pos) {
		if(!PreferenceUtils.isUserVisitor()) {

		}else{

		}

	}



	public void back_click(View v) {
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			topicId = getIntent().getStringExtra("id");
			topicName = getIntent().getStringExtra("name");

		}

	}

	protected ArrayList<ChannelItem> getUserChannelLists(){
		if(!PreferenceUtils.isUserVisitor()) {
			return VideoChannelNames.topicChannels;

		}
		return VideoChannelNames.topicChannels;
	}


	protected Fragment initFragment(String channelName) {


        if (channelName.equals("圈子")) {
            VideoChannelsAllListFragment fragment = new VideoChannelsAllListFragment();
            return fragment;
        }

		Fragment f=new Fragment();
        return f;

	}



	String topicId = "";
	String topicName = "";



}

/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.youkes.vr.AppViewPagerActivity;
import com.youkes.vr.ChannelItem;
import com.youkes.vr.R;

import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.video.grid.VideoGridFragment;

import java.util.ArrayList;

/**
 * 视频服务
 * @author xuming
 *
 */

public class VideoChannelDetailActivity extends AppViewPagerActivity {

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main_view_pager_base;
	}

	@Override
	public String getTitleString() {
		return getString(R.string.video_channel_videos);
	}

	@Override
	public ArrayList<String> getMenuList() {
		ArrayList<String> menus = new ArrayList<>();
		if(!PreferenceUtils.isUserVisitor()) {

			//menus.add(getString(R.string.video_movie_trailer));
			//menus.add(getString(R.string.video_upload));
			//menus.add(getString(R.string.video_my));
		}
		return menus;
	}


    private String channelId="";
    private String channelName="";
    private String ownerUserId="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        this.channelId = getIntent().getStringExtra("id");
        this.channelName = getIntent().getStringExtra("name");
        this.ownerUserId = getIntent().getStringExtra("userId");

		super.onCreate(savedInstanceState);
        getTopBarView().setTitle("视频频道:"+this.channelName);

	}

	protected ArrayList<ChannelItem> getUserChannelLists(){
		return VideoTypeNames.defaultVideoChannels;
	}


	protected Fragment initFragment(String tag) {

        this.channelId=getIntent().getStringExtra("id");
		VideoGridFragment f=new VideoGridFragment();
		f.setChannelId(this.channelId);
		return f;

	}





}

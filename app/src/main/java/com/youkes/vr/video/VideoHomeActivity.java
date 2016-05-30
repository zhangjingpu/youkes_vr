/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.youkes.vr.AppViewPagerActivity;
import com.youkes.vr.R;
import com.youkes.vr.ChannelItem;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.video.channels.VideoChannelsAllListFragment;
import com.youkes.vr.video.grid.VideoGridFragment;


import java.util.ArrayList;

/**
 * 视频服务
 * @author xuming
 *
 */

public class VideoHomeActivity extends AppViewPagerActivity {

    @Override
    public boolean hasBackBtn() {
        return false;
    }

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main_view_pager_base;
	}

	@Override
	public String getTitleString() {
		return getString(R.string.app_name);
	}

	@Override
	public ArrayList<String> getMenuList() {
		ArrayList<String> menus = new ArrayList<>();
		if(!PreferenceUtils.isUserVisitor()) {

			menus.add(getString(R.string.video_movie_trailer));
			//menus.add(getString(R.string.video_upload));
			//menus.add(getString(R.string.video_my));
		}
		return menus;
	}


	@Override
	protected void onMenuClick(int pos) {
		if(!PreferenceUtils.isUserVisitor()) {
			switch (pos) {
				case 0:
					//startUpload();
					movieTrailer();
					break;
				case 1:
					break;
			}
		}

	}

	private void movieTrailer() {
		Intent i = new Intent(this, VideoTrailerActivity.class);
		startActivity(i);
	}





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected ArrayList<ChannelItem> getUserChannelLists(){
		return VideoTypeNames.defaultMainChannels;
	}


	protected Fragment initFragment(String channelName) {

		if(channelName.equals("热门")||channelName.equals("全部")||channelName.equals("视频")) {
			VideoGridFragment f=new VideoGridFragment();
            f.setTypestr("全景");
			return f;
		}

        if(channelName.equals("频道")) {
            VideoChannelsAllListFragment f=new VideoChannelsAllListFragment();
            f.setTagName("全景");
            return f;
        }

        //最近类型是排序
		VideoGridFragment f=new VideoGridFragment();
		f.setTypestr(channelName);
		return f;

	}





}

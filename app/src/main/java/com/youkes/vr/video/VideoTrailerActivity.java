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

public class VideoTrailerActivity extends AppViewPagerActivity {

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main_view_pager_base;
	}

	@Override
	public String getTitleString() {
		return getString(R.string.video_movie_trailer);
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


	@Override
	protected void onMenuClick(int pos) {
		if(!PreferenceUtils.isUserVisitor()) {
			switch (pos) {
				case 0:
					//startUpload();
					movieTrainer();
					break;
				case 1:
					break;
			}
		}

	}

	private void movieTrainer() {

	}





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected ArrayList<ChannelItem> getUserChannelLists(){
		return VideoTypeNames.defaultTrailerChannels;
	}


	protected Fragment initFragment(String tag) {

		if(tag.equals("热门")) {
			VideoGridFragment f=new VideoGridFragment();
			f.setTypestr("预告片");
			return f;
		}

		VideoGridFragment f=new VideoGridFragment();
		f.setTypestr("预告片");
		f.setTagstr(tag);
		return f;

	}





}

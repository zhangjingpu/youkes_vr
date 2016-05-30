/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.grid;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youkes.vr.ChannelItem;
import com.youkes.vr.GridTabFragment;
import com.youkes.vr.MainApp;
import com.youkes.vr.R;
import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.utils.StringUtils;
import com.youkes.vr.video.VideoApi;
import com.youkes.vr.video.VideoItem;
import com.youkes.vr.video.VideoListJson;
import com.youkes.vr.video.VideoTypeNames;
import com.youkes.vr.widget.swipegridview.SwipeGridView;

import java.util.ArrayList;
import java.util.List;

public class VideoGridTabFragment extends GridTabFragment implements
		SwipeRefreshLayout.OnRefreshListener {




	protected VideoGridAdapter listAdapter = new VideoGridAdapter();


	private boolean isRefresh = false;
	private String tagstr = "";

	int resId = R.layout.grid_tab_list;

	public VideoGridTabFragment() {
		listAdapter.setUserId(userId);
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	boolean firstLoad = true;
	Dialog deleteDlg = null;
	TextView emptyView=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater,container,savedInstanceState);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
		mListView = (SwipeGridView) v.findViewById(R.id.listview);
		mListView.setNumColumns(2);

		mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);

		emptyView = (TextView)v.findViewById(R.id.empty_tv);
		emptyView.setVisibility(View.GONE);

		swipeLayout.setOnRefreshListener(this);

		mListView.setAdapter(listAdapter);



		if (currentPage == 0) {
			loadData(currentPage++);
		} else {
			mProgressBar.setVisibility(View.GONE);
			mListView.onBottomComplete();
		}


		mListView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!hasMore) {
					return;
				}
				if (dataLoading) {
					return;
				}

				loadData(currentPage++);

			}
		});

		mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				hideAllInputControls();
				return false;
			}
		});

		initLoadingDlg();
		return v;
	}


	public ArrayList<ChannelItem> getChannelList1(String s){
		if(StringUtils.isEquals(s,"预告片")){
			 return VideoTypeNames.defaultTrailerChannels;
		}

		return new ArrayList<ChannelItem>();

	}



	public ArrayList<ChannelItem> getChannelList(){
		return VideoTypeNames.defaultMainChannels;
	}

	int currentPage = 0;


	@Override
	protected void onMenuTab0Selected(int i) {


		String typeName=getChannelList().get(i).getName();
		if("全部".equals(typeName)||"热门".equals(typeName)) {
			setTypeName("");
		}else {
			//ToastUtil.showMessage(typeName);
			setTypeName(typeName);

		}
		setTagstr("");

		initTabCol1();

		runRefresh();

	}


	protected void onMenuTab1Selected(int i) {
		if(i<=0){
			return;
		}
		ArrayList<ChannelItem> list1=getChannelList1(typeName);
		if(list1!=null&&list1.size()>i){
			String tagName=list1.get(i).getName();
			if("全部".equals(tagName)) {
				setTagstr("");
			}else {
				setTagstr(tagName);
			}

		}

		runRefresh();

	}

	private void initLoadingDlg() {
		//loadingDlg = DialogUtil.createLoadingDialog(getActivity());
		//loadingDlg.setCancelable(true);
		//loadingDlg.show();

	}

	protected void hideAllInputControls() {
		if (deleteDlg != null) {
			deleteDlg.dismiss();
		}

	}

	boolean dataLoading = false;

	private void loadData(int index) {
		dataLoading = true;
		if (MainApp.getInstance().isNetworkAvailable()) {
			loadItemList(index);
		} else {
			mListView.onBottomComplete();
			mProgressBar.setVisibility(View.GONE);
			dataLoading = false;
		}
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				runRefresh();
			}
		}, 500);
	}

	boolean hasMore = true;

	protected void runRefresh() {
		hasMore = true;
		isRefresh = true;
		currentPage = 0;
		listAdapter.clear();
		// listsModles.clear();
		loadData(currentPage++);

	}

	protected void onItemLongClick(int position) {
		// T.showLong(getActivity(), "long press");
		// DialogUtil.getAlertDlg(getActivity());
	}

	String circleId = "";

	public void setUserId(String userId) {
		this.userId = userId;
		listAdapter.setUserId(userId);
	}

	String userId="";



	private String typeName ="";

	public void setTypeName(String typestr) {
		this.typeName = typestr;
	}

    private String channelId="";
	void loadItemList(int index) {

		VideoApi.getInstance().query(channelId,userId, typeName, tagstr, index,
				new OnTaskCompleted() {

					@Override
					public void onTaskCompleted(String result) {
						// T.showLong(getActivity(), result);
						if (!StringUtils.isEmpty(result)) {
							getResult(result);
						} else {
							swipeLayout.setRefreshing(false);
						}

					}
				});

	}

	public void getResult(String result) {
		if(getActivity()==null){
			return;
		}
		if (isRefresh) {
			isRefresh = false;
			listAdapter.clear();

		}

		// T.showLong(getActivity(), result);
		mProgressBar.setVisibility(View.GONE);
		swipeLayout.setRefreshing(false);
		List<VideoItem> list = VideoListJson.readItems(result);

		dataLoading = false;
		if (list == null || list.size() == 0) {

			if(list==null){
				//ToastUtil.showMessage(getString(R.string.connect_server_error));
				emptyView.setText(getString(R.string.error_request_pull_refresh));
				if(listAdapter!=null&&listAdapter.getCount()==0) {emptyView.setVisibility(View.VISIBLE);}

			}else{
				//ToastUtil.showMessage(getString(R.string.connect_server_error));
				emptyView.setText(getString(R.string.main_empty_result));
				if(listAdapter!=null&&listAdapter.getCount()==0) {emptyView.setVisibility(View.VISIBLE);}
			}

			hasMore = false;
			swipeLayout.setRefreshing(false);
			mListView.setHasMore(hasMore);

			mListView.onBottomComplete();
			
			return;
		} else {
			hasMore = true;
			mListView.setHasMore(hasMore);
			emptyView.setVisibility(View.GONE);
		}

		listAdapter.appendList(list);
		// listsModles.addAll(list);
		mListView.onBottomComplete();
		

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public String getTagstr() {
		return tagstr;
	}
	int type = 0;

	public void setType(int t) {
		type = t;
	}

	public void setTagstr(String tagstr) {
		this.tagstr = tagstr;
	}

	protected void refresh() {

		runRefresh();

	}

	public void setCircleId(String id) {
		this.circleId = id;

	}

	@Override
	protected int getLayoutId() {
		return resId;
	}


}

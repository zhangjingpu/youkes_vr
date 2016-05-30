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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youkes.vr.MainApp;
import com.youkes.vr.R;
import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.ui.BaseFragment;
import com.youkes.vr.utils.DialogUtil;
import com.youkes.vr.utils.StringUtils;
import com.youkes.vr.video.VideoApi;

import com.youkes.vr.video.VideoItem;
import com.youkes.vr.video.VideoListJson;
import com.youkes.vr.widget.swipegridview.SwipeGridView;


import java.util.List;

public class VideoGridFragment extends BaseFragment implements
		SwipeRefreshLayout.OnRefreshListener {

	protected RelativeLayout inputBar;

	protected SwipeRefreshLayout swipeLayout;
	protected SwipeGridView mListView;
	protected ProgressBar mProgressBar;
	protected VideoGridAdapter listAdapter = new VideoGridAdapter();


	private boolean isRefresh = false;
	private String tagstr = "";

	int resId = R.layout.grid_list;
    private String channelId="";

    public VideoGridFragment() {
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

		View v = inflater.inflate(this.resId, container, false);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
		mListView = (SwipeGridView) v.findViewById(R.id.listview);
		mListView.setNumColumns(2);

		mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		inputBar = (RelativeLayout) v.findViewById(R.id.inputBar);
		emptyView = (TextView)v.findViewById(R.id.empty_tv);
		emptyView.setVisibility(View.GONE);

		swipeLayout.setOnRefreshListener(this);
		initDocDeleteDlg();
		mListView.setAdapter(listAdapter);

		/*
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long pos) {
				ToastUtil.showMessage("pos:" + pos);
			}
		});
		*/

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

	int currentPage = 0;
	//Dialog loadingDlg = null;

	private void initLoadingDlg() {
		//loadingDlg = DialogUtil.createLoadingDialog(getActivity());
		//loadingDlg.setCancelable(true);
		//loadingDlg.show();

	}

	protected void hideAllInputControls() {
		if (deleteDlg != null) {
			deleteDlg.dismiss();
		}

		if (inputBar != null) {
			inputBar.setVisibility(View.GONE);
		}

	}


	private void initDocDeleteDlg() {

		View v = LayoutInflater.from(getActivity()).inflate(
				R.layout.share_delete_layout, null);
		deleteDlg = DialogUtil.getMenuDialog(getActivity(), v);

		View cv = v.findViewById(R.id.cancel_click);
		cv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deleteDlg.dismiss();
			}
		});

		View confirmV = v.findViewById(R.id.confirm_click);
		confirmV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onDeleteDoc(v);
				// currentPost
				// deleteDlg.dismiss();
			}
		});

	}

	protected void onDeleteDoc(View v) {
		doDeleteDoc(currentDeletedDoc);
		deleteDlg.dismiss();
	}

	private void doDeleteDoc(final VideoItem doc) {
		if (doc == null) {
			return;
		}

		String id = doc.getId();


	}

	VideoItem currentDeletedDoc = null;

	protected void docDeleteClicked(final VideoItem doc) {

		if (deleteDlg == null) {
			return;
		}
		currentDeletedDoc = doc;
		deleteDlg.show();

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



	private String typestr="";

	public void setTypestr(String typestr) {
		this.typestr = typestr;
	}

	void loadItemList(int index) {

		VideoApi.getInstance().query(channelId,userId, typestr, tagstr, index,
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


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}

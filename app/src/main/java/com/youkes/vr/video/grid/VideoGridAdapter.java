/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.grid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.youkes.vr.video.VideoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoGridAdapter extends BaseAdapter {

	public List<VideoItem> lists = new ArrayList<VideoItem>();
	private String tag;

	public void appendList(List<VideoItem> list) {
		if (!lists.containsAll(list) && list != null && list.size() > 0) {
			lists.addAll(list);
		}
		notifyDataSetChanged();

	}

	// Context context;

	VideoGridItemActionListener actionListener = null;

	public void setActionListener(VideoGridItemActionListener listener) {
		this.actionListener = listener;
	}

	public void clear() {
		lists.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	HashMap<String, VideoGridItemView> viewMap = new HashMap<String, VideoGridItemView>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		VideoGridItemView newItemView;

		if (convertView == null) {
			newItemView = new VideoGridItemView(parent.getContext(), type);

		} else {
			newItemView = (VideoGridItemView) convertView;
		}

		initView(newItemView, position);
		newItemView.setNewsItemListener(new VideoGridItemView.NewItemListener() {
			@Override
			public void OnDeleteClick(VideoItem item) {
				VideoGridAdapter.this.lists.remove(item);
				VideoGridAdapter.this.notifyDataSetChanged();
			}

			@Override
			public void OnNewsClick(VideoItem item) {

			}

			@Override
			public void OnNewsTagClick(VideoItem item, String tag) {

			}
		});
		return newItemView;
	}

	VideoGridItemView initView(VideoGridItemView newItemView, int position) {
		final VideoItem doc = lists.get(position);
		int type = doc.getType();
		newItemView.setSelectedTag(tag);
		newItemView.setSelectedUserId(userId);

		if (doc != null) {
			doc.getTags().remove(this.tag);
		}

		newItemView.setDoc(doc);
		String docId = doc.getId();
		viewMap.put(docId, newItemView);
		String photo = doc.getUserAvatar();
		String userTitle = doc.getUserId();
		String userNick = doc.getUserNick();
		String userName = doc.getUserName();

		if (!userNick.equals("")) {
			userTitle = userNick;
		}

		if (!userName.equals("")) {
			userTitle = userName;
		}

		String datestr = doc.getDateReadable();
		String title = doc.getTitle();
		String text = doc.getText();
		ArrayList<String> imgs = doc.getImgs();

		String img=doc.getImg();
		String userPhoto = doc.getUserPhoto();
		//String video=doc.ge
		newItemView.setLink(title, text, img, userPhoto, userTitle, datestr);

		return newItemView;
	}

	protected void onDeleteDoc(final VideoItem doc) {

		if (this.actionListener != null) {
			this.actionListener.onDeleteDoc(doc);
		}
		// doDeleteDoc(doc);

	}

	public void onDeleteCompleted(String result, final VideoItem doc) {
		// T.showLong(MainApp.getApp().getApplicationContext(), result);
		lists.remove(doc);
		notifyDataSetChanged();
	}

	public VideoItem getDocById(String docId) {
		for (VideoItem doc : lists) {
			String dic = doc.getId();
			if (docId.equals(dic)) {
				return doc;
			}
		}
		return null;
	}


	private int type = 0;

	public void setType(int type) {
		this.type = type;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private String userId = "";

	public void setUserId(String userId) {
		this.userId = userId;
	}
}


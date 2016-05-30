/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoChannelsListAdapter extends BaseAdapter {

	public List<VideoChannelsItem> lists = new ArrayList<VideoChannelsItem>();
	private String tag;

	public void appendList(List<VideoChannelsItem> list) {
		if (!lists.containsAll(list) && list != null && list.size() > 0) {
			lists.addAll(list);
		}
		notifyDataSetChanged();
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

	HashMap<String, VideoChannelsListItemView> viewMap = new HashMap<String, VideoChannelsListItemView>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		VideoChannelsListItemView newItemView;

		if (convertView == null) {
			newItemView = new VideoChannelsListItemView(parent.getContext());

		} else {
			newItemView = (VideoChannelsListItemView) convertView;
		}

		newItemView.setSelectedUserId(userId);

		initView(newItemView, position);

		newItemView.setItemListener(defListener);


		return newItemView;
	}

	VideoChannelsListItemView.ListItemListener defListener=new VideoChannelsListItemView.ListItemListener() {
		@Override
		public void OnDeleteClick(VideoChannelsItem item) {
			if (VideoChannelsListAdapter.this.listItemActionListener != null) {
				VideoChannelsListAdapter.this.listItemActionListener.OnDeleteClick(item);
			}
			VideoChannelsListAdapter.this.lists.remove(item);
			VideoChannelsListAdapter.this.notifyDataSetChanged();
		}

		@Override
		public void OnItemClick(VideoChannelsItem item) {
			if (VideoChannelsListAdapter.this.listItemActionListener != null) {
				VideoChannelsListAdapter.this.listItemActionListener.OnItemClick(item);
			}
		}

		@Override
		public void OnItemTagClick(VideoChannelsItem item, String tag) {
			if (VideoChannelsListAdapter.this.listItemActionListener != null) {
				VideoChannelsListAdapter.this.listItemActionListener.OnItemTagClick(item,tag);
			}
		}

		@Override
		public void OnCheck(VideoChannelsItem item) {

			if (VideoChannelsListAdapter.this.listItemActionListener != null) {
				VideoChannelsListAdapter.this.listItemActionListener.OnCheck(item);
			}
		}

		@Override
		public void OnFocusCompleted(int status,VideoChannelsItem item) {
			if (VideoChannelsListAdapter.this.listItemActionListener != null) {
				VideoChannelsListAdapter.this.listItemActionListener.OnFocusCompleted(status,item);
			}
		}
	};

	public void setListItemActionListener(VideoChannelsListItemView.ListItemListener listItemActionListener) {
		this.listItemActionListener = listItemActionListener;
	}


	VideoChannelsListItemView.ListItemListener listItemActionListener=null;

	VideoChannelsListItemView initView(VideoChannelsListItemView newItemView, int position) {
		final VideoChannelsItem doc = lists.get(position);

		newItemView.setSelectedTag(tag);
		newItemView.setSelectedUserId(userId);

		if (doc != null) {
			doc.getTags().remove(this.tag);
		}

		newItemView.setDoc(doc);
		String docId = doc.getId();
		viewMap.put(docId, newItemView);


		String userTitle=doc.getUserId();
		String userName=doc.getUserName();
		if (!userName.equals("")) {
			userTitle = userName;
		}

		String datestr = doc.getDateReadable();
		String title = doc.getName();
		String text = doc.getDesc();

		ArrayList<String> tags=doc.getTags();
		String tagstr="";
		for(String t:tags){
			tagstr+=t+" ";
		}
		ArrayList<String> img = doc.getImgs();
		String userPhoto = doc.getUserPhoto();

		newItemView.setLink(title, tagstr, img, userPhoto, userTitle, datestr);

		return newItemView;
	}



	public VideoChannelsItem getDocById(String docId) {
		for (VideoChannelsItem doc : lists) {
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


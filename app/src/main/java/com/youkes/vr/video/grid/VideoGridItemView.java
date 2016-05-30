/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.grid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youkes.vr.R;

import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.utils.DialogUtil;
import com.youkes.vr.utils.GlideUtil;
import com.youkes.vr.utils.StringUtils;
import com.youkes.vr.video.VideoChannelDetailActivity;
import com.youkes.vr.video.VideoDetailActivity;
import com.youkes.vr.video.VideoItem;
import com.youkes.vr.video.VideoTagsActivity;


import java.util.ArrayList;


public class VideoGridItemView extends LinearLayout {

	protected ImageView bigImageView;
	protected TextView itemTitleView;
	protected TextView dateView;
	protected TextView tag0View;
	protected TextView tag1View;
	protected TextView tag2View;
	private String selectedUserId="";

	public VideoGridItemView(Context context, int type) {
		super(context);

		initViewItem(context);
	}

	public VideoGridItemView(Context context) {
		
		super(context);

		initViewItem(context);

	}

    TextView userNameView=null;
    TextView channelNameView=null;
	private void initViewItem(Context context) {

		View layout = LayoutInflater.from(context).inflate(
				R.layout.video_grid_item_view, this, true);

		View itemView= findViewById(R.id.video_layout);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDocItemClick();
			}
		});
		View v = findViewById(R.id.item_layout);

         this.userNameView=(TextView)findViewById(R.id.user_name);
         this.channelNameView=(TextView)findViewById(R.id.channel_name);

        this.userNameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameViewClick(v);
            }
        });

        this.channelNameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                channelNameViewClick(v);
            }
        });

		//itemTextView = (TextView) findViewById(R.id.topic_init_text);

		bigImageView = (ImageView) findViewById(R.id.topic_init_img);


		itemTitleView = (TextView) findViewById(R.id.topic_init_title);

		itemTitleView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDocItemClick();
			}
		});
		/*itemTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDocItemClick();
			}
		});
		*/

		//userImageView = (ImageView) findViewById(R.id.user_img);
		//userNameView = (TextView) findViewById(R.id.user_name);
		dateView = (TextView) findViewById(R.id.date_text);


		tag0View = (TextView) findViewById(R.id.tag0);
		tag1View = (TextView) findViewById(R.id.tag1);
		tag2View = (TextView) findViewById(R.id.tag2);


		tag0View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagsClick(0);
			}
		});

		tag1View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagsClick(1);
			}
		});

		tag2View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagsClick(2);
			}
		});



		bigImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDocItemClick();
			}
		});





		initDocDeleteDlg();


	}

    private void channelNameViewClick(View v) {


        if(this.doc==null){
            return;
        }
        String channelId = this.doc.getVideoChannelId();
        String channelName = this.doc.getVideoChannelName();
        Intent intent = new Intent(getContext(), VideoChannelDetailActivity.class);
        intent.putExtra("id", channelId);
        intent.putExtra("name", channelName);
        intent.putExtra("img", "");
        intent.putExtra("userId", "");
        getContext().startActivity(intent);
    }


    private void userNameViewClick(View v) {
        String userId = this.doc.getUserId();
        String userName = this.doc.getUserName();



    }


    protected void onDocEditClick() {

		if(this.doc==null){
			return;
		}

	}




	private void tagsClick(int i) {
		if(this.doc==null){
			return;
		}

		ArrayList<String> tags=doc.getTags();
		if(tags.size()>i&&i>=0){

			String t=tags.get(i);
			Intent intent = new Intent(getContext(), VideoTagsActivity.class);
			intent.putExtra("tag",t);
			getContext().startActivity(intent);
			return;

		}
	}

	private void onUserNameClick() {
		if(this.doc==null){
			return;
		}




	}



	Dialog deleteDlg=null;
	private void initDocDeleteDlg() {

		View v = LayoutInflater.from(getContext()).inflate(
				R.layout.share_delete_layout, null);

		deleteDlg = DialogUtil.getMenuDialog(getContext(), v);

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
			}
		});
	}


	protected void onNewsDeleteClick() {
		if (deleteDlg == null) {
			return;
		}
		deleteDlg.show();

	}

	private void onDeleteDoc(View v) {
		if(this.doc==null){
			return;
		}
		//ToastUtil.showMessage(this.doc.getName());
		if (deleteDlg == null) {
			return;
		}

		deleteDlg.dismiss();


	}

	protected void onDocItemClick() {

		if(this.doc==null){
			return;
		}

		String docId=this.doc.getId();
		if(docId!=null&&!docId.equals("")){
			Intent intent = new Intent(getContext(), VideoDetailActivity.class);
			String title = this.doc.getTitle();
            String text = this.doc.getText();
            String img=this.doc.getImg();
            intent.putExtra("img", img);
            intent.putExtra("text", text);
			intent.putExtra("title",title);
			intent.putExtra("id", docId);
			getContext().startActivity(intent);
			return;
		}


	}

	protected void linkClick(View v) {
		if (this.doc == null) {
			return;
		}
		onDocItemClick();

	}

	String selectedTag="";
	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
		if(selectedTag!=null) {
			if(this.doc!=null) {
				this.doc.getTags().remove(selectedTag);
			}
		}
	}

	public void setSelectedUserId(String userId) {
		this.selectedUserId = userId;
	}

	public interface NewItemListener{
		void OnDeleteClick(VideoItem item);
		void OnNewsClick(VideoItem item);
		void OnNewsTagClick(VideoItem item, String tag);
	}

	public void setNewsItemListener(NewItemListener newsItemListener) {
		this.newsItemListener = newsItemListener;

	}

	NewItemListener newsItemListener=null;
	private VideoItem doc = null;

	public void setDoc(VideoItem doc) {
		this.doc = doc;

		if(this.doc.getUserId().equals(PreferenceUtils.getUserId())){

		}else {

		}


		tag0View.setVisibility(View.GONE);
		tag1View.setVisibility(View.GONE);
		tag2View.setVisibility(View.GONE);

		if(selectedTag!=null) {
			doc.getTags().remove(selectedTag);
		}
		ArrayList<String> tags=doc.getTags();
		int tagsize=tags.size();
		if(tagsize>=3){
			tag2View.setText(tags.get(2));
			tag2View.setVisibility(View.VISIBLE);
		}
		if(tagsize>=2){
			tag1View.setText(tags.get(1));
			tag1View.setVisibility(View.VISIBLE);
		}
		if(tagsize>=1){
			tag0View.setText(tags.get(0));
			tag0View.setVisibility(View.VISIBLE);
		}

        String userName=this.doc.getUserName();
        if (!StringUtils.isEmpty(userName)) {
            userNameView.setText(userName);
            userNameView.setVisibility(View.GONE);
        } else {
            userNameView.setVisibility(View.GONE);
        }

        String videoChannelName=this.doc.getVideoChannelName();
        if (!StringUtils.isEmpty(videoChannelName)) {
            channelNameView.setText(videoChannelName);
            channelNameView.setVisibility(View.VISIBLE);
        } else {
            channelNameView.setVisibility(View.GONE);
        }


    }


	public void setLink(String title, String text, String img,String userPhoto,String userName,String date) {

		String imgUrl = img;
		//bigImageView.setVisibility(View.GONE);

		if (!"".equals(imgUrl) && imgUrl.indexOf("http") == 0) {
			bigImageView.setVisibility(View.VISIBLE);
			GlideUtil.displayImageBitmap(imgUrl, bigImageView);
		} else {
            bigImageView.setImageResource(R.drawable.pictures_no);
			bigImageView.setVisibility(View.VISIBLE);
		}



		if (!"".equals(userPhoto) && userPhoto.indexOf("http") == 0) {

			//GlideUtil.displayImage(userPhoto, userImageView);
		} else {
			//userImageView.setVisibility(View.GONE);
		}


		if (title != null && title.length() > 1) {
			itemTitleView.setText(title);
			itemTitleView.setVisibility(View.VISIBLE);
		} else {
			itemTitleView.setVisibility(View.GONE);
		}

		if (text != null && text.length() > 1) {
			//itemTextView.setText(StringUtils.simplify(text));
			//itemTextView.setVisibility(View.VISIBLE);
		} else {
			//itemTextView.setVisibility(View.GONE);
		}




		if (doc.getUserId().equals(selectedUserId)) {
			//userNameView.setVisibility(View.GONE);
		}

		//userNameView.setVisibility(View.GONE);

		if (date != null && date.length() > 1) {
			dateView.setText(date);
			dateView.setVisibility(View.VISIBLE);
		} else {
			dateView.setVisibility(View.GONE);
		}


	}

}

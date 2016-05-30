/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youkes.vr.R;
import com.youkes.vr.api.JSONCallRet;
import com.youkes.vr.api.JSONResult;

import com.youkes.vr.config.StatusCode;

import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.utils.DialogUtil;
import com.youkes.vr.utils.GlideUtil;
import com.youkes.vr.utils.ToastUtil;
import com.youkes.vr.video.VideoChannelDetailActivity;

import java.util.ArrayList;


public class VideoChannelsListItemView extends LinearLayout {

	protected TextView topicTextView;
	protected ImageView bigImageView;
	protected TextView topicTitleView;

	
	protected ImageView userImageView;
	protected TextView userNameView;
	protected TextView dateView;

	protected TextView tag0View;
	protected TextView tag1View;
	protected TextView tag2View;
	private String selectedUserId="";



	public VideoChannelsListItemView(Context context) {
		
		super(context);

		initViewItem(context);


		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClicked();
			}
		});

	}


	Button focusBtn=null;
	private void initViewItem(Context context) {

		View layout = LayoutInflater.from(context).inflate(
				R.layout.topic_item_list_item_view, this, true);

		View layoutV =findViewById(R.id.item_layout);
		layoutV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClicked();
			}
		});

		focusBtn =(Button)findViewById(R.id.focus_button);
		focusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				focusCircle(v);
			}
		});

		topicTextView = (TextView) findViewById(R.id.topic_init_text);

		bigImageView = (ImageView) findViewById(R.id.topic_init_img);


		topicTitleView = (TextView) findViewById(R.id.topic_init_title);



		userImageView = (ImageView) findViewById(R.id.user_img);
		userNameView = (TextView) findViewById(R.id.user_name);
		dateView = (TextView) findViewById(R.id.date_text);
		gridV = findViewById(R.id.grid_img_layout);

		tag0View = (TextView) findViewById(R.id.tag0);
		tag1View = (TextView) findViewById(R.id.tag1);
		tag2View = (TextView) findViewById(R.id.tag2);


		tag0View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		tag1View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		tag2View.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		imageView0 = (ImageView) findViewById(R.id.img_0);
		imageView1 = (ImageView) findViewById(R.id.img_1);
		imageView2 = (ImageView) findViewById(R.id.img_2);


		deleteBtn = findViewById(R.id.btn_delete);
		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemDeleteClick();
			}
		});

		editBtn = findViewById(R.id.btn_edit);
		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		initDocDeleteDlg();

		userNameView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		if(PreferenceUtils.isUserVisitor()){
			focusBtn.setVisibility(View.GONE);
		}else{
			focusBtn.setVisibility(View.VISIBLE);
		}

	}

	private void focusCircle(View v) {
		String name=this.doc.getName();
		String id=this.doc.getId();

		if(this.doc.getUserId().equals(PreferenceUtils.getUserId())){
			//onItemDeleteClick();
			//return;
		}


        int focusState=this.doc.getFocusState();
        if(focusState==1){

            VideoChannelsApi.getInstance().focusDel(this.doc.getFocusId(), new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String result) {
                    onFocusDelCompleted(result);
                }
            });
            
            return;
        }

		if(focusState==0) {
            VideoChannelsApi.getInstance().focusTopicCircle(id, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String result) {
                    onFocusCompleted(result);
                }
            });
        }

	}


    private void onFocusDelCompleted(String result) {
        JSONCallRet ret= JSONResult.parseRet(result);
        if(ret!=null) {
            ToastUtil.showMessage(ret.message);
            if (ret.status == StatusCode.Api_OK) {
                this.doc.setFocusState(0);
                if (focusBtn != null) {
                    focusBtn.setText(getContext().getString(R.string.focus));
                }
            }
        }else{
            ToastUtil.showMessage(getContext().getString(R.string.errormsg_server));
        }

    }

    private void onDeleteCompleted(String result) {

        JSONCallRet ret= JSONResult.parseRet(result);
        if(ret!=null) {

            ToastUtil.showMessage(ret.message);
            if(docItemListener!=null){
                docItemListener.OnDeleteClick(this.doc);
            }
            if(ret.status== StatusCode.Api_OK){
                this.doc.setFocusState(0);
                if(focusBtn!=null) {
                    focusBtn.setText(getContext().getString(R.string.focus));
                }
            }

        }else{
            ToastUtil.showMessage(getContext().getString(R.string.errormsg_server));
        }


	}




	private void onFocusCompleted(String result) {
		JSONCallRet ret= JSONResult.parseRet(result);
		if(ret!=null) {

			ToastUtil.showMessage(ret.message);
			if(docItemListener!=null){
				docItemListener.OnFocusCompleted(ret.status,this.doc);
			}
			if(ret.status== StatusCode.Api_OK){
                this.doc.setFocusState(1);
                if(focusBtn!=null) {
                    focusBtn.setText(getContext().getString(R.string.focus_cancel));
                }
			}

		}else{
			ToastUtil.showMessage(getContext().getString(R.string.errormsg_server));
		}

	}






	View editBtn=null;
	View deleteBtn=null;
	View gridV=null;
	ImageView imageView0 =null;
	ImageView imageView1 =null;
	ImageView imageView2 =null;

	Dialog deleteDlg=null;
	private void initDocDeleteDlg() {

		View v = LayoutInflater.from(getContext()).inflate(
				R.layout.share_delete_layout, null);
		TextView descView=(TextView)v.findViewById(R.id.del_desc);
		descView.setText(getContext().getString(R.string.del_site_confirm));

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


	protected void onItemDeleteClick() {
		if (deleteDlg == null) {
			return;
		}
		deleteDlg.show();

	}

	public interface ListItemListener {
		void OnDeleteClick(VideoChannelsItem item);
		void OnItemClick(VideoChannelsItem item);
		void OnItemTagClick(VideoChannelsItem item, String tag);
		void OnCheck(VideoChannelsItem item);
		void OnFocusCompleted(int status, VideoChannelsItem item);

	}

	public void setItemListener(ListItemListener itemListener) {
		this.docItemListener = itemListener;

	}

	ListItemListener docItemListener =null;

	private void onDeleteDoc(View v) {
		if(this.doc==null||deleteDlg == null){
			return;
		}

		String id=this.doc.getId();
		VideoChannelsApi.getInstance().deleteTopicCircle(id, new OnTaskCompleted() {
			@Override
			public void onTaskCompleted(String result) {
				onDeleteCompleted(result);

			}
		});

		deleteDlg.dismiss();


	}

	protected void onItemClicked() {
		VideoChannelsItem item = this.doc;
		String t=item.getType();


		Intent intent=new Intent(getContext(), VideoChannelDetailActivity.class);
		intent.putExtra("id",doc.getId());
		intent.putExtra("name",doc.getName());
        intent.putExtra("img",doc.getImg());
		intent.putExtra("userId",doc.getUserId());

		getContext().startActivity(intent);



	}




	protected void linkClick(View v) {
		if (this.doc == null) {
			return;
		}
		onItemClicked();

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


	private VideoChannelsItem doc = null;

	public void setDoc(VideoChannelsItem doc) {
		this.doc = doc;

		if(this.doc.getUserId().equals(PreferenceUtils.getUserId())){
			deleteBtn.setVisibility(View.VISIBLE);
			editBtn.setVisibility(View.VISIBLE);
		}else {
			deleteBtn.setVisibility(View.GONE);
			editBtn.setVisibility(View.GONE);
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

		/*
		if(this.doc.getUserId().equals(PreferenceUtils.getUserId())) {
			focusBtn.setText(getContext().getString(R.string.delete));
			focusBtn.setBackgroundResource(R.drawable.btn_style_red);
			focusBtn.setTextColor(Color.rgb(255, 255, 255));
		} else {
			focusBtn.setText(getContext().getString(R.string.focus));
			focusBtn.setBackgroundResource(R.drawable.btn_style_green);
			focusBtn.setTextColor(Color.rgb(255, 255, 255));
		}
		*/


        int focusState=doc.getFocusState();
        if(focusState==0) {
            focusBtn.setText(getContext().getString(R.string.focus));
        }else{
            focusBtn.setText(getContext().getString(R.string.focus_cancel));
        }
		focusBtn.setBackgroundResource(R.drawable.btn_style_green);
		focusBtn.setTextColor(Color.rgb(255, 255, 255));

	}

	public void setLink(String title, String text, ArrayList<String> imgs,String userPhoto,String userName,String date) {
		String imgUrl="";
		if(imgs!=null&&imgs.size()>0) {
			imgUrl = imgs.get(0);
		}
		bigImageView.setVisibility(View.GONE);
		gridV.setVisibility(View.GONE);
		imageView2.setVisibility(View.GONE);
		imageView1.setVisibility(View.GONE);
		imageView0.setVisibility(View.GONE);


		if(imgs!=null&&imgs.size()==1) {
			if (!"".equals(imgUrl) && imgUrl.indexOf("http") == 0) {
				bigImageView.setVisibility(View.VISIBLE);
				GlideUtil.displayImage(imgUrl, bigImageView);
			} else {
				bigImageView.setVisibility(View.GONE);
			}
		}else if(imgs!=null&&imgs.size()>1){
			bigImageView.setVisibility(View.GONE);
			gridV.setVisibility(View.VISIBLE);


			if(imgs.size()>=3){
				GlideUtil.displayImage(imgs.get(2), imageView2);
				imageView2.setVisibility(View.VISIBLE);
			}

			if(imgs.size()>=2){
				GlideUtil.displayImage(imgs.get(1), imageView1);
				imageView1.setVisibility(View.VISIBLE);
			}
			GlideUtil.displayImage(imgs.get(0), imageView0);
			imageView0.setVisibility(View.VISIBLE);


		}
		
		if (!"".equals(userPhoto)&&userPhoto.indexOf("http")==0) {

			GlideUtil.displayImage(userPhoto, userImageView);
		} else {
			userImageView.setVisibility(View.GONE);
		}
		
		

		if (title != null && title.length() > 1) {
			topicTitleView.setText(title);
			topicTitleView.setVisibility(View.VISIBLE);
		} else {
			topicTitleView.setVisibility(View.GONE);
		}

		if (text != null && text.length() > 1) {
			topicTextView.setText(text);
			topicTextView.setVisibility(View.VISIBLE);
		} else {
			topicTextView.setVisibility(View.GONE);
		}

		
		if (userName != null && userName.length() > 1) {
			userNameView.setText(userName);
			userNameView.setVisibility(View.VISIBLE);
		} else {
			userNameView.setVisibility(View.GONE);
		}

		if(doc.getUserId().equals(selectedUserId)){
			userNameView.setVisibility(View.GONE);
		}

		if (date != null && date.length() > 1) {
			dateView.setText(date);
			dateView.setVisibility(View.VISIBLE);
		} else {
			dateView.setVisibility(View.GONE);
		}
		
		
		
	}

}

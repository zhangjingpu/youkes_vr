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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.youkes.vr.R;
import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.utils.GlideUtil;
import com.youkes.vr.utils.StringUtils;
import com.youkes.vr.vr.VrVideoDetailActivity;


public class VideoDetailFragment extends Fragment {

	TextView detailText = null;
	TextView titleText = null;
	TextView videoTypeText = null;
	ImageView imageView = null;
	String imageSrc = null;
	String textStr = null;
	ImageButton playText = null;
	String itemId = "";
	VideoDetailItem detailItem = null;

	public VideoDetailFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.item_detail_video, container, false);

        titleText = (TextView) v.findViewById(R.id.title_text);
        imageView = (ImageView) v.findViewById(R.id.detail_img_view);
        detailText = (TextView) v.findViewById(R.id.detail_text);
        videoTypeText = (TextView) v.findViewById(R.id.video_type);
        playText = (ImageButton) v.findViewById(R.id.play_link);


        playText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                playClick(v);
            }
        });

        Intent intent = this.getActivity().getIntent();
        String docTitle = intent.getStringExtra("title");
        String docText = intent.getStringExtra("text");
        String docImg = intent.getStringExtra("img");

        if (StringUtils.isHttp(docImg)) {
            GlideUtil.displayImage(docImg, this.imageView);
        }
        docText = docText.trim();
        docText = Html.fromHtml(docText).toString();
        setDetailText(docText);
        if (this.titleText != null) {
            this.titleText.setText(docTitle);
        }


        return v;

    }

	protected void playClick(View v) {
		if (detailItem == null) {
			return;
		}

		String vclass=this.detailItem.getVclass();

		if(!StringUtils.isEmpty(vclass)&&VideoClass.VR_360_MP4.equals(vclass) ){


			Intent intent = new Intent(this.getActivity(),
                    VrVideoDetailActivity.class);
			intent.putExtra("video",detailItem.getPlay());
            intent.putExtra("id",detailItem.getId());
			intent.putExtra("title",detailItem.getTitle());
            intent.putExtra("text",detailItem.getText());
			startActivity(intent);


            return;
		}


		Intent intent = new Intent(this.getActivity(),
				VideoPlayerActivity.class);
		intent.putExtra("play", detailItem.getPlay());
		intent.putExtra("title", detailItem.getTitle());
		startActivity(intent);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	public void setDetailText(String text) {
		this.textStr = text;
		if (this.detailText != null) {
			this.detailText.setText(text);
		}
	}

	public void loadItem(String id) {
		itemId = id;

		VideoApi.getInstance().getDetail(id, new OnTaskCompleted() {

			@Override
			public void onTaskCompleted(String result) {
				onLoadItemParse(result);
			}
		});

	}

	protected void onLoadItemParse(String result) {
		// T.showLong(getActivity(), bookId+":"+result);
		detailItem = VideoDetailParser.parse(result);
		loadItem(detailItem);

	}

	public void loadItem(VideoDetailItem item) {

		if (item == null) {
			return;
		}

		textStr = item.getText();
		imageSrc = item.getImg();

		if (this.imageSrc != null) {
			GlideUtil.displayImage(imageSrc, this.imageView);
		}

		textStr = textStr.trim();
		textStr = Html.fromHtml(textStr).toString();

		String props = item.getPropText();
		if (props != null && props.length() > 4) {

		} else {
			// propsText.setHeight(0);
		}

		setDetailText(textStr);

		if (this.titleText != null) {
			this.titleText.setText(item.getTitle());
		}

		if (this.playText != null) {
			//this.playText.setText(getString(R.string.play_video));
		}

		String vclass=this.detailItem.getVclass();

		if(!StringUtils.isEmpty(vclass)&&VideoClass.VR_360_MP4.equals(vclass) ){
			videoTypeText.setText("VR视频:可以旋转场景");
		}else {
			videoTypeText.setText("视频");
		}

	}

	public VideoDetailItem getDoc() {

		return detailItem;
	}

    public void setInfo(String docTitle, String docText, String docImg) {


    }
}

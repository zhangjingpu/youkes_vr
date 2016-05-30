/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.youkes.vr.AppMenuActivity;


import com.youkes.vr.R;
import com.youkes.vr.config.ServerConfig;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.ui.dialog.UListDialog;

import java.util.ArrayList;


public class VideoDetailActivity extends AppMenuActivity {

	private String docId = "";
	private String docTitle = "";
    private String docText = "";
    private String docImg = "";

	VideoDetailFragment detailFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			detailFragment = new VideoDetailFragment();
			Intent intent = this.getIntent();
			docId = intent.getStringExtra("id");
			docTitle = intent.getStringExtra("title");
            docText = intent.getStringExtra("text");
            docImg = intent.getStringExtra("img");

			this.setTitle(docTitle);
			this.getTopBarView().setTitle(docTitle);

            detailFragment.setInfo(docTitle,docText,docImg);
			detailFragment.loadItem(docId);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, detailFragment).commit();

		}

	}


	@Override
	public ArrayList<String> getMenuList() {
		ArrayList<String> menus = new ArrayList<>();
		if(!PreferenceUtils.isUserVisitor()) {

		}else{
		}

		return menus;

	}


	@Override
	protected void onMenuClick(int pos) {
		if(!PreferenceUtils.isUserVisitor()) {
			switch (pos) {
				case 0:

					break;
				case 1:
					shareSocial();
					break;


			}
		}
	}



    UListDialog shareDlgSocial =null;
	private UListDialog getImageMenuDialogSocial() {
		if(shareDlgSocial !=null){
			return shareDlgSocial;
		}

		shareDlgSocial = new UListDialog(this,
				R.array.webview_image_actions_social);
		shareDlgSocial.setTitle(getString(R.string.share));
		shareDlgSocial.setOnDialogItemClickListener(new UListDialog.OnDialogItemClickListener() {
			@Override
			public void onDialogItemClick(Dialog d, int position) {
				onMenuSocialClicked(d, position);
			}
		});

		return shareDlgSocial;

	}


	private void onMenuSocialClicked(Dialog d, int position) {
		if(detailFragment==null){
			return;
		}
		VideoDetailItem doc=detailFragment.getDoc();


		String img=doc.getImg();
		ArrayList<String> imgs=new ArrayList<String>();
		imgs.add(img);
		String  title=doc.getTitle();
		String url= ServerConfig.getVideoUrl(doc.getId());
        String videoUrl=doc.getPlay();
        String text=doc.getText();
		switch (position) {
			case 0:

				break;
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;

            case 4:

                break;
		}
	}




    private void shareSocial() {
		UListDialog dialog = getImageMenuDialogSocial();
		dialog.show();
	}




}

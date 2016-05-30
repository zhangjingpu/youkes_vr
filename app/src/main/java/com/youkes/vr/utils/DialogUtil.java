/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.youkes.vr.MainApp;
import com.youkes.vr.R;

public class DialogUtil {

	
	public static Dialog createLoadingDialog(Context context) {

		if (context == null) {
			return null;
		}

		LayoutInflater inflater = LayoutInflater.from(context);
		// View v = inflater.inflate(R.layout.progress_bar, null);
		View v = inflater.inflate(R.layout.loading_box, null);
		View layout = v.findViewById(R.id.layout);
		Dialog loadingDialog = new Dialog(context, R.style.Theme_Light_CustomDialog_Blue);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(loadingDialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		loadingDialog.getWindow().setAttributes(lp);

		return loadingDialog;

	}

	
	public static Dialog getGenderSelectDlg(Activity context) {

		View v = LayoutInflater.from(context).inflate(
				R.layout.dlg_gender_select, null);
		Dialog dlg = DialogUtil.getMenuDialog(context, v);

		View maleV = v.findViewById(R.id.male_select);
		View femaleV = v.findViewById(R.id.female_select);

		final CheckBox maleCheck = (CheckBox) v.findViewById(R.id.cb_male);
		final CheckBox femaleCheck = (CheckBox) v.findViewById(R.id.cb_female);

		maleV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				maleCheck.setChecked(true);
				femaleCheck.setChecked(false);
			}
		});

		femaleV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				maleCheck.setChecked(false);
				femaleCheck.setChecked(true);
			}
		});
		return dlg;
	}

	public static Dialog getMenuDialog(Context context, View view) {

		final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle);
		dialog.setContentView(view);

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		float screenW = MainApp.getInstance().getScreenWidth();
		// int screenH = getScreenHeight(context);
		lp.width = (int) (0.8 * screenW);
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画

		dialog.setCanceledOnTouchOutside(true);
		return dialog;

	}

	




	

	public static AlertDialog getAlertDlg(Context context) {
		AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.setTitle("操作");
		dlg.show();
		return dlg;
	}

	
	public static void messageBox(Context context, String title, String text) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
		dlgAlert.setTitle(title);
		dlgAlert.setMessage(text);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// finish();
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

}

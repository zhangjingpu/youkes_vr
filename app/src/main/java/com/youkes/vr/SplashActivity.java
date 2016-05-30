/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.youkes.vr.config.ServerConfig;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.ui.dialog.UAlertDialog;
import com.youkes.vr.update.ApkUpdateChecker;
import com.youkes.vr.update.ApkUpdateListener;
import com.youkes.vr.video.VideoHomeActivity;

public class SplashActivity extends FragmentActivity {

	ApkUpdateChecker update = null;
	private Handler mHandler= new Handler();

	View progressBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		progressBar = findViewById(R.id.progressbar);

		startConnect();
	}

	private void startConnect() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			checkServerAvailability();
		} else {
			showReConnectBoxDelay();
			//showConnectionNADialog();
		}

	}

	private void checkServerAvailability() {

		checkApkUpdate();
	}

	private void checkApkUpdate() {
		update = new ApkUpdateChecker(SplashActivity.this);
		update.setOnCancelListener(new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				startLogin();
			}

		});

		update.setUpdateListener(new ApkUpdateListener() {

			@Override
			public void onAlreadyNewestVersion() {
				startLogin();
			}

			@Override
			public void onError() {

                //showReConnectBoxDelay();
				startLoginActivity();

			}
		});

		update.setCheckUrl(ServerConfig.getUpdateUrl());
		update.checkForUpdates();

	}

	public void showConnectionNADialogDelay() {

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				showConnectionNADialog();
			}
		}, 100);
	}

	public void showConnectionNADialog() {

		String text = "网络不可用,是否打开WIFI设置?";

		final UAlertDialog buildAlert = UAlertDialog.buildAlert(this, text,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						SplashActivity.this.finish();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						onWifiOpen();
					}
				});

		buildAlert.setTitle(R.string.app_tip);
		buildAlert.setCancelable(false);
		buildAlert.show();

	}

	protected void showReConnectBoxDelay() {

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				showReConnectBox();
			}
		}, 100);

	}

	private void showReConnectBox() {
		progressBar.setVisibility(View.VISIBLE);

		UAlertDialog buildAlert = UAlertDialog.buildAlert(this,
				getString(R.string.reconnect),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						startLoginActivity();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ToastUtil.showMessage("pos");
						startConnect();
					}
				});
		buildAlert.setCancelable(false);
		buildAlert.setTitle(R.string.app_tip);
		buildAlert.show();
	}


	private void startLoginActivity() {
		MainApp.getInstance().logout();
		mHandler.postDelayed(goMainActivity, 100);
		SplashActivity.this.finish();
	}

	protected void onWifiOpen() {

		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		this.startActivity(intent);

		showReConnectBoxDelay();
	}

	boolean loginLoading = false;

	private void startLogin() {

		final String userId = PreferenceUtils.getUserId();
		final String accessKey = PreferenceUtils.getAccessKey();
		if (loginLoading) {
			return;
		}

		loginLoading = true;

	}



	Runnable goMainActivity = new Runnable() {
		@Override
		public void run() {
			startActivity(new Intent(SplashActivity.this, VideoHomeActivity.class));
			finish();
		}

	};


	private void doLauncherAction() {
		mHandler.postDelayed(goMainActivity, 100);
	}




}

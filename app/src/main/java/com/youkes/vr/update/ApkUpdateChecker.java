/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.update;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class ApkUpdateChecker {

	private static final String TAG = "UpdateChecker";
	private Context mContext;
	// 检查版本信息的线程
	private Thread mThread;
	// 版本对比地址
	private String mCheckUrl;
	private ApkVersion mApkVersion;
	// 下载apk的对话框
	private ProgressDialog mProgressDialog;

	private File apkFile;

	public void setCheckUrl(String url) {
		mCheckUrl = url;
	}

	DialogInterface.OnClickListener cancelListener = null;

	public void setOnCancelListener(DialogInterface.OnClickListener listener) {
		this.cancelListener = listener;
	}

	public ApkUpdateChecker(Context context) {
		mContext = context;
		// instantiate it within the onCreate method
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("正在下载");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);

	}

	ApkUpdateListener updateListener = null;

	public void setUpdateListener(ApkUpdateListener listener) {
		this.updateListener = listener;
	}

	public void checkForUpdates() {
		if (mCheckUrl == null) {
			// throw new Exception("checkUrl can not be null");
			return;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					mApkVersion = (ApkVersion) msg.obj;
					try {
						int versionCode = mContext.getPackageManager()
								.getPackageInfo(mContext.getPackageName(), 0).versionCode;
						System.out.println("version-->" + versionCode
								+ "  mApkVersion.getApkCode()-->>"
								+ mApkVersion.getApkCode());
						if (mApkVersion.getApkCode() > versionCode) {
							showUpdateDialog();
						} else {
							Toast.makeText(mContext, "已经是最新版本",
									Toast.LENGTH_SHORT).show();
							if (updateListener != null) {
								updateListener.onAlreadyNewestVersion();
							}
						}
					} catch (PackageManager.NameNotFoundException ignored) {
						//
					}
				}
			}
		};

		mThread = new Thread() {
			@Override
			public void run() {
				// if (isNetworkAvailable(mContext)) {
				Message msg = new Message();
				String json = sendPost();
				Log.i("jianghejie", "json = " + json);
				if (json != null) {
					ApkVersion version = ApkVersion.parseJson(json);
					msg.what = 1;
					msg.obj = version;
					handler.sendMessage(msg);
				} else {
					Log.e(TAG, "can't get app update json");
				}
			}
		};
		mThread.start();
	}

	protected String sendPost() {
		HttpURLConnection uRLConnection = null;
		InputStream is = null;
		BufferedReader buffer = null;
		String result = null;
		try {
			URL url = new URL(mCheckUrl);
			uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setConnectTimeout(10 * 1000);
			uRLConnection.setReadTimeout(10 * 1000);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("Connection", "Keep-Alive");
			uRLConnection.setRequestProperty("Charset", "UTF-8");
			uRLConnection
					.setRequestProperty("Accept-Encoding", "gzip, deflate");
			uRLConnection
					.setRequestProperty("Content-Type", "application/json");
			uRLConnection.connect();
			is = uRLConnection.getInputStream();
			String content_encode = uRLConnection.getContentEncoding();
			if (null != content_encode && !"".equals(content_encode)
					&& content_encode.equals("gzip")) {
				is = new GZIPInputStream(is);
			}
			buffer = new BufferedReader(new InputStreamReader(is));
			StringBuilder strBuilder = new StringBuilder();
			String line;
			while ((line = buffer.readLine()) != null) {
				strBuilder.append(line);
			}
			result = strBuilder.toString();
		} catch (Exception e) {
			//ToastUtil.showMessage("服务器维护中...");
			
			if (updateListener != null) {
				updateListener.onError();
			}
			Log.e(TAG, "http post error", e);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (uRLConnection != null) {
				uRLConnection.disconnect();
			}
		}
		return result;
	}

	public void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		// builder.setIcon(R.drawable.icon);
		builder.setTitle("有新版本");
		builder.setMessage(mApkVersion.getUpdateMessage());
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				downLoadApk();
			}
		});

		if (cancelListener != null) {
			builder.setNegativeButton("忽略", cancelListener);
		}
		builder.setCancelable(false);
		builder.show();
	}

	public void downLoadApk() {
		String apkUrl = mApkVersion.getApkUrl();
		System.out.println("apkUrl-->>" + apkUrl);
		String dir = mContext.getExternalFilesDir("apk").getAbsolutePath();
		File folder = Environment.getExternalStoragePublicDirectory(dir);
		if (folder.exists() && folder.isDirectory()) {
			// do nothing
		} else {
			folder.mkdirs();
		}

		String filename = apkUrl.substring(apkUrl.lastIndexOf("/"),
				apkUrl.length());
		String destinationFilePath = dir + "/" + filename;
		apkFile = new File(destinationFilePath);
		mProgressDialog.show();
		Intent intent = new Intent(mContext, ApkDownService.class);
		intent.putExtra("url", apkUrl);
		intent.putExtra("dest", destinationFilePath);
		intent.putExtra("receiver", new DownloadReceiver(new Handler()));
		mContext.startService(intent);
	}

	private class DownloadReceiver extends ResultReceiver {
		public DownloadReceiver(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			if (resultCode == ApkDownService.UPDATE_PROGRESS) {
				int progress = resultData.getInt("progress");
				mProgressDialog.setProgress(progress);
				if (progress == 100) {
					mProgressDialog.dismiss();
					// 如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
					String[] command = { "chmod", "777", apkFile.toString() };
					try {
						ProcessBuilder builder = new ProcessBuilder(command);
						builder.start();
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(apkFile),
								"application/vnd.android.package-archive");
						mContext.startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
			}
		}
	}
}
/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.update;

import org.json.JSONException;
import org.json.JSONObject;

public class ApkVersion {
	
	private String updateMessage;
    private String apkUrl;
    private int apkCode;
    public static final String APK_DOWNLOAD_URL = "url";
    public static final String APK_UPDATE_CONTENT = "updateMessage";
    public static final String APK_VERSION_CODE = "versionCode";

    public static ApkVersion parseJson(String json) {
		ApkVersion ver = new ApkVersion();
		try {
			JSONObject obj = new JSONObject(json);
			String updateMessage = obj.getString(ApkVersion.APK_UPDATE_CONTENT);
			String apkUrl = obj.getString(ApkVersion.APK_DOWNLOAD_URL);
			int apkCode = obj.getInt(ApkVersion.APK_VERSION_CODE);
			System.out.println("updateMessage-->" + updateMessage
					+ " apkUrl-->>" + apkUrl + "  apkCode-->>" + apkCode);
			ver.setApkCode(apkCode);
			ver.setApkUrl(apkUrl);
			ver.setUpdateMessage(updateMessage);
		} catch (JSONException e) {
			//Log.e(TAG, "parse json error", e);
		}
		return ver;
	}
    
    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public int getApkCode() {
        return apkCode;
    }

    public void setApkCode(int apkCode) {
        this.apkCode = apkCode;
    }

}

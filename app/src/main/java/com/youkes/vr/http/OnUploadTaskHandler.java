/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.http;

public interface OnUploadTaskHandler {
	
	void onTaskCompleted(String result);
	void onUploadeBytes(int count, int total);
	void onUploadError();
	
}

/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.config;


public class ServerConfig {

	public static String API_HOST = "api.youkes.com";
	public static int API_PORT = 8081;

	public static void init() {

	}


	public static String getUserAvatar(String userId) {
		return "http://" + API_HOST + ":" + API_PORT + "/user/avatar/" + userId;// xuming";
	}

	
	public static String getUserAvatarUnknown() {
		return "http://file.youkes.com/public/user/avatar.png";
	}

	public static String getUserAvatarDefault() {
		return "http://file.youkes.com/public/user/avatar.png";
	}


	public static String getUpdateUrl() {
		return "http://" + API_HOST + ":" + API_PORT
				+ "/api/apk/vr/version/latest";
	}

	
	public static String getUploadFileUrl22(String sha1) {
		
		String url="http://file.youkes.com/upload/public/"+sha1;
		return url;
		
	}



	public static String getVideoUrl(String id) {
		return "http://youkes.com/video/detail/" + id;// xuming";
	}



}

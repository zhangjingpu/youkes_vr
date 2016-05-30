/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import java.util.ArrayList;
import java.util.List;


import com.youkes.vr.config.ServerConfig;
import com.youkes.vr.http.HttpGetTask;
import com.youkes.vr.http.HttpPostTask;
import com.youkes.vr.http.NameValuePair;
import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.utils.StringUtils;


public class VideoApi {
	private static VideoApi mInstance = null;
	static String API_HOST = ServerConfig.API_HOST;
	static int API_PORT = ServerConfig.API_PORT;
	public static String URL_Query="http://" + API_HOST + ":" + API_PORT+"/api/video/query";
	public static String URL_Search="http://" + API_HOST + ":" + API_PORT+"/api/video/search";
	public static String URL_Get_Detail="http://" + API_HOST + ":" + API_PORT+"/api/video/detail";
	public static String URL_Video_Web_Share="http://" + API_HOST + ":" + API_PORT+"/api/video/web/share";

	protected VideoApi() {

	}

	public static VideoApi getInstance() {
		if (mInstance == null) {
			mInstance = new VideoApi();
		}
		return mInstance;
	}

	
	

	public void query(int page,OnTaskCompleted listener) {

		String url = URL_Query + "?";


		ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new NameValuePair("p", String.valueOf(page)));
		HttpGetTask.execute(listener, params, url);

	}



	public void query(String channelId,String targetUserId,String type, String tag, int pageIdx,
					  OnTaskCompleted listener) {

		List<NameValuePair> h = new ArrayList<NameValuePair>();
		h.add(new NameValuePair("targetUserId", targetUserId));
		h.add(new NameValuePair("type", type));
		h.add(new NameValuePair("tag", tag));
        h.add(new NameValuePair("channelId", channelId));
		h.add(new NameValuePair("p", String.valueOf(pageIdx)));
		HttpPostTask.execute(listener, h, URL_Query);

	}


	public void getDetail(String id, OnTaskCompleted listener){
		
		
		String url = URL_Get_Detail;
		ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new NameValuePair("id",id));
		HttpGetTask.execute(listener,params,url);

	}


	public void shareWebVideo(String title, String text, String img, ArrayList<String> tags, String videoUrl, String pageUrl, OnTaskCompleted listener) {

		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("title", title));
		list.add(new NameValuePair("text", text));

		list.add(new NameValuePair("img", img));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		String tagstr= StringUtils.toArrayStr(tags);
		list.add(new NameValuePair("tags", tagstr));
		list.add(new NameValuePair("videoUrl", videoUrl));
		list.add(new NameValuePair("pageUrl", pageUrl));

		HttpPostTask.execute(listener, list, URL_Video_Web_Share);


	}
}

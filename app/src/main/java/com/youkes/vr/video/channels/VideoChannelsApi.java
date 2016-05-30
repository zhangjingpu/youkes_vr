/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import com.youkes.vr.config.ServerConfig;
import com.youkes.vr.http.HttpGetTask;
import com.youkes.vr.http.HttpPostTask;
import com.youkes.vr.http.NameValuePair;
import com.youkes.vr.http.OnTaskCompleted;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.utils.GeoUtil;
import com.youkes.vr.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoChannelsApi {

	private static VideoChannelsApi instance = null;
	static String API_HOST = ServerConfig.API_HOST;
	static int API_PORT = ServerConfig.API_PORT;

	public static String URL_Topic_Circle_Add = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/add";


	public static String URL_Topic_Circle_Focus = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/focus";

	public static String URL_Topic_Circle_Focus_Del = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/focus/del";

	public static String URL_Topic_Circle_Del = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/del";


	public static String URL_Topic_Circle_Add_Web_Img = "http://" + API_HOST
			+ ":" + API_PORT + "/api/video/channel/add/web/img";

	public static String URL_Topic_Circle_Query = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/query";

    public static String URL_Topic_Circle_Near_Query = "http://" + API_HOST + ":"
            + API_PORT + "/api/video/channel/near";

	public static String URL_Topic_Circle_Focus_Query = "http://" + API_HOST + ":"
			+ API_PORT + "/api/video/channel/focus/query";




    static String URL_Circle_Geofilter_Query_Base="/api/video/channel/geofilter";
    static String URL_Circle_Geofilter_Query = "http://" + API_HOST + ":" + API_PORT + URL_Circle_Geofilter_Query_Base;

	protected VideoChannelsApi() {

	}

	public static VideoChannelsApi getInstance() {
		if (instance == null) {
			instance = new VideoChannelsApi();
		}
		return instance;
	}


	public void addTopicCircle(String name,String desc, String img,ArrayList<String> tags,double lat,double lng, OnTaskCompleted listener) {
		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("name", name));
		list.add(new NameValuePair("desc", desc));
		list.add(new NameValuePair("img", img));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		String tagstr= StringUtils.toArrayStr(tags);
		list.add(new NameValuePair("tags", tagstr));

        if(lat!=0&&lng!=0) {
            list.add(new NameValuePair("lat", String.valueOf(lat)));
            list.add(new NameValuePair("lng", String.valueOf(lng)));
            list.add(new NameValuePair("pos", GeoUtil.getLatLngString(lat, lng)));
        }

		 HttpPostTask.execute(listener, list, URL_Topic_Circle_Add);

	}


	public void focusTopicCircle(String id, OnTaskCompleted listener) {
		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("id", id));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Focus);

	}



	public void focusDel(String id, OnTaskCompleted listener) {
		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("id", id));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Focus_Del);

	}


	public void deleteTopicCircle(String id, OnTaskCompleted listener) {
		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("id", id));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Del);

	}





	public void addTopicCircleWebImg(String name, String img, String url,
			OnTaskCompleted listener) {
		String userId = PreferenceUtils.getUserId();
		String accessKey = PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("name", name));
		list.add(new NameValuePair("img", img));
		list.add(new NameValuePair("url", url));
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		 HttpPostTask.execute(listener, list, URL_Topic_Circle_Add_Web_Img);

	}

	public void topicCircleQuery(String targetUserId,int idx,String tag, OnTaskCompleted listener) {

		String userId=PreferenceUtils.getUserId();
		String accessKey=PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		list.add(new NameValuePair("targetUserId", targetUserId));
		list.add(new NameValuePair("p", String.valueOf(idx)));
		list.add(new NameValuePair("tag", tag));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Query);
	}


    public void topicCircleNear(String targetUserId,int idx,String tag,double lat,double lng, OnTaskCompleted listener) {

        String userId=PreferenceUtils.getUserId();
        String accessKey=PreferenceUtils.getAccessKey();

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new NameValuePair("userId", userId));
        list.add(new NameValuePair("accessKey", accessKey));
        list.add(new NameValuePair("targetUserId", targetUserId));
        list.add(new NameValuePair("p", String.valueOf(idx)));
        list.add(new NameValuePair("tag", tag));
        list.add(new NameValuePair("lat", String.valueOf(lat)));
        list.add(new NameValuePair("lng", String.valueOf(lng)));
        HttpPostTask.execute(listener, list, URL_Topic_Circle_Near_Query);
    }

	public void topicCircleFocusQuery(String targetUserId,int idx,String tag, OnTaskCompleted listener) {

		String userId=PreferenceUtils.getUserId();
		String accessKey=PreferenceUtils.getAccessKey();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("userId", userId));
		list.add(new NameValuePair("accessKey", accessKey));
		list.add(new NameValuePair("targetUserId", targetUserId));
		list.add(new NameValuePair("p", String.valueOf(idx)));
		list.add(new NameValuePair("tag", tag));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Focus_Query);
	}

	public void myTopicCircleQuery(int idx, OnTaskCompleted listener) {
		String userId=PreferenceUtils.getUserId();
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("p", String.valueOf(idx)));
		list.add(new NameValuePair("userId", userId));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Query);
	}

	public void userTopicCircleQuery(String userId,int idx, OnTaskCompleted listener) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair("p", String.valueOf(idx)));
		list.add(new NameValuePair("userId", userId));
		HttpPostTask.execute(listener, list, URL_Topic_Circle_Query);
	}







    public void geofilter(double latMin, double lngMin, double latMax, double lngMax, OnTaskCompleted listener) {


        String userId = PreferenceUtils.getUserId();
        String accessKey = PreferenceUtils.getAccessKey();

        ArrayList<NameValuePair> h = new ArrayList<NameValuePair>();
        h.add(new NameValuePair("userId", userId));
        h.add(new NameValuePair("accessKey", accessKey));
        h.add(new NameValuePair("latMin", String.valueOf(latMin)));
        h.add(new NameValuePair("lngMin", String.valueOf(lngMin)));
        h.add(new NameValuePair("latMax", String.valueOf(latMax)));
        h.add(new NameValuePair("lngMax", String.valueOf(lngMax)));
        h.add(new NameValuePair("p", String.valueOf(0)));
        HttpGetTask.execute(listener, h, URL_Circle_Geofilter_Query);
    }

    public void geofilter(String tag,double latMin, double lngMin, double latMax, double lngMax,int p, OnTaskCompleted listener) {


        String userId = PreferenceUtils.getUserId();
        String accessKey = PreferenceUtils.getAccessKey();

        ArrayList<NameValuePair> h = new ArrayList<NameValuePair>();
        h.add(new NameValuePair("userId", userId));
        h.add(new NameValuePair("accessKey", accessKey));
        h.add(new NameValuePair("tag", tag));
        h.add(new NameValuePair("latMin", String.valueOf(latMin)));
        h.add(new NameValuePair("lngMin", String.valueOf(lngMin)));
        h.add(new NameValuePair("latMax", String.valueOf(latMax)));
        h.add(new NameValuePair("lngMax", String.valueOf(lngMax)));
        h.add(new NameValuePair("p", String.valueOf(p)));
        HttpGetTask.execute(listener, h, URL_Circle_Geofilter_Query);
    }
}

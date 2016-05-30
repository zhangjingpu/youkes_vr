/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class VideoListJson {


    public static ArrayList<VideoItem> readItems(String str) {

    	ArrayList<VideoItem> list=new ArrayList<VideoItem>();
	    try{
	        JSONObject jsonResult=new JSONObject(str);
			if(jsonResult.has("api")){
				if("/error/network".equals(jsonResult.getString("api"))){
					return null;
				}
			}

	        if(!jsonResult.has("content")){
				return null;
        	}


	        JSONArray docs=jsonResult.getJSONArray("content");
	        int lenDocs=docs.length();
	        for(int i=0;i<lenDocs;i++){
	        	JSONObject docObj=docs.getJSONObject(i);
	        	VideoItem info = new VideoItem(docObj);
	        	list.add(info);
	        	
	        }

	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
    }


}

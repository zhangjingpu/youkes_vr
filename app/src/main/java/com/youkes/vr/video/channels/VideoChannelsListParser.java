/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoChannelsListParser {
	
	public static ArrayList<VideoChannelsItem> parse(String jsonstr)
	{

		ArrayList<VideoChannelsItem> list=new ArrayList<VideoChannelsItem>();
	    try{
	        JSONObject jsonResult=new JSONObject(jsonstr);
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
				VideoChannelsItem info=new VideoChannelsItem(docObj);
	        	list.add(info);
	        	
	        }
	        
	     
	    }catch (Exception e) {
	        e.printStackTrace();
			return null;
	    }
	    return list;
	}


	
	
}

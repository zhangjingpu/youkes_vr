/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import org.json.JSONObject;

public class VideoDetailParser {

	public static VideoDetailItem parse(String jsonstr) {

		try {
			JSONObject jsonResult = new JSONObject(jsonstr);
			if (!jsonResult.has("content")) {
				return new VideoDetailItem();
			}

			JSONObject docObj = jsonResult.getJSONObject("content");

			VideoDetailItem info = new VideoDetailItem(docObj);

			return info;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new VideoDetailItem();

	}

}

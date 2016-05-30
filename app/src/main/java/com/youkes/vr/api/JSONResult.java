/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.api;

import com.youkes.vr.utils.JSONUtil;

import org.json.JSONObject;

public class JSONResult {

	public static int parse(String str) {

		try {
			JSONObject jsonResult = new JSONObject(str);
			if (!jsonResult.has("status")) {
				return -1;
			}

			int code = jsonResult.getInt("status");
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public static JSONCallRet parseRet(String str) {

		try {
			JSONObject jsonResult = new JSONObject(str);
			if (!jsonResult.has("api")) {
				return null;
			}
			if (!jsonResult.has("status")) {
				return null;
			}

			String api = JSONUtil.getString(jsonResult, "api");
			int status = JSONUtil.getInt(jsonResult, "status");
			String message = JSONUtil.getString(jsonResult, "message");
			String accessKey = JSONUtil.getString(jsonResult, "accessKey");

			String content=JSONUtil.getString(jsonResult,"content");
			JSONCallRet r = new JSONCallRet(api, status, message, accessKey,content);

			return r;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}

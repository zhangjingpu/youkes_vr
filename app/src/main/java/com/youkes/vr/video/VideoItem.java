/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import com.youkes.vr.utils.JSONUtil;
import com.youkes.vr.utils.DateUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class VideoItem {


	private int commentIndex = -1;

	private String userId = "";
	private String userNick = "";
	private String userName = "";
	private String userPhoto = "";

	private String circleId = "";
	private String circleName = "";
	
	private String text = "";
	private String title = "";
	private String img = "";
	private Date date = new Date();
	private int width = 0;
	private int height = 0;
	private int type;
	private String targetId = "";
	private double lat = 0.0;
	private double lng = 0.0;
	private JSONObject jsonObj = null;
	private String topicId="";
    private String videoChannelId="";
    private String videoChannelName="";

    public String getVideoChannelId() {
        return videoChannelId;
    }

    public String getVideoChannelName() {
        return videoChannelName;
    }


    public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	private String docId="";
	
	public VideoItem(JSONObject docObj) {
		this.setJsonObj(docObj);
		this.setId(JSONUtil.getString(docObj, "_id"));
		this.userId = JSONUtil.getString(docObj, "userId");
		this.userNick = JSONUtil.getString(docObj, "userNick");
		this.circleId = JSONUtil.getString(docObj, "circleId");
		this.circleName = JSONUtil.getString(docObj, "circleName");
		this.userName = JSONUtil.getString(docObj, "userName");
		this.userPhoto = JSONUtil.getString(docObj, "userPhoto");
		this.docId = JSONUtil.getString(docObj, "docId");
        this.videoChannelId = JSONUtil.getString(docObj, "videoChannelId");
        this.videoChannelName = JSONUtil.getString(docObj, "videoChannelName");
		this.text = JSONUtil.getString(docObj, "text");
		this.setWidth(JSONUtil.getInt(docObj, "width"));
		this.setHeight(JSONUtil.getInt(docObj, "height"));
		this.setTopicId(JSONUtil.getString(docObj, "topicId"));
		this.lat = JSONUtil.getDouble(docObj, "lat");
		this.lng = JSONUtil.getDouble(docObj, "lng");

		this.setTargetId(JSONUtil.getString(docObj, "targetId"));
		this.img = JSONUtil.getString(docObj, "img");
		this.setUrl(JSONUtil.getString(docObj, "url"));
		this.type = JSONUtil.getInt(docObj, "type");
		this.setTitle(JSONUtil.getString(docObj, "title"));
		this.setImgs(JSONUtil.getArrayStrList(docObj, "imgs"));
		this.setDate(JSONUtil.getLongDate(docObj, "date"));

		this.tags=JSONUtil.getArrayStrList(docObj, "tags");



	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	private ArrayList<String> imgs = null;
	private ArrayList<String> tags = null;
	private String url;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String id = "";



	public String getImageSrc() {
		return img;
	}

	public ArrayList<String> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<String> imgs) {
		this.imgs = imgs;
	}

	public String getUserAvatar() {
		return this.userPhoto;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateReadable() {
		return DateUtil.toHumanReadable(this.date);
	}

	public String getImgsrc() {
		return img;
	}

	public String getTitle() {
		return this.title;

	}

	public int getImgCount() {
		if (imgs == null) {
			return 0;
		}
		return imgs.size();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}









	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public ArrayList<String> getTags() {
		return tags;
	}
}

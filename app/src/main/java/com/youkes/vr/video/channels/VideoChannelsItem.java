/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import com.youkes.vr.utils.DateUtil;
import com.youkes.vr.utils.JSONUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xuming on 2016/2/19.
 */
public class VideoChannelsItem {

    private String id = "";

    private String img = "";
    private ArrayList<String> tags=new ArrayList<String>();

    private ArrayList<String> imgs=new ArrayList<String>();
    private String userPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }






    private Date date=null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userId="";
    private String userName="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String name="";
    private String desc="";
    private String type="";

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    private String focusId="";

    public int getFocusState() {
        return focusState;
    }

    public void setFocusState(int focusState) {
        this.focusState = focusState;
    }

    private int focusState=0;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lat = 0.0;
    private double lng = 0.0;
    private double dist=0.0;

    public VideoChannelsItem(JSONObject obj) {

        this.id = JSONUtil.getString(obj, "_id");
        this.name = JSONUtil.getString(obj, "name");
        this.desc = JSONUtil.getString(obj, "desc");
        this.img = JSONUtil.getString(obj, "img");
        this.userId = JSONUtil.getString(obj, "userId");
        this.type = JSONUtil.getString(obj, "type");
        this.userName = JSONUtil.getString(obj, "userName");
        this.date = JSONUtil.getLongDate(obj, "date");
        this.userPhoto=JSONUtil.getString(obj, "userPhoto");
        this.tags=JSONUtil.getArrayStrList(obj,"tags");
        this.imgs.add(this.img);
        this.focusState=JSONUtil.getInt(obj,"focusState");
        this.focusId=JSONUtil.getString(obj,"focusId");

        this.lat = JSONUtil.getDouble(obj, "lat");
        this.lng = JSONUtil.getDouble(obj, "lng");

    }


    public ArrayList<String> getTags() {
        return tags;
    }

    public String getDateReadable() {
        return DateUtil.toHumanReadable(this.date);
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}

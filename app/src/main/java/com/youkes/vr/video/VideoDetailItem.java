/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import java.util.Date;

import org.json.JSONObject;

import com.youkes.vr.utils.JSONUtil;
import com.youkes.vr.config.ServerConfig;


public class VideoDetailItem {

	private String id = "";
	private String userId = "";
	private String targetId = "";

	private String img = "";// img http addr

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getWidth() {
		if (width == 0) {
			return 400;
		}
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {

		if (height == 0) {
			return 400;
		}
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getT0() {
		return t0;
	}

	public void setT0(String t0) {
		this.t0 = t0;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String[] getAuthor() {
		return author;
	}

	public void setAuthor(String[] author) {
		this.author = author;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String[] getProps() {
		return props;
	}

	public void setProps(String[] props) {
		this.props = props;
	}

	private String title = "";
	private String text = "";
	private Date date;
	private String parentId = "";
	private int seq = 0;
	private int type = 0;
	String groupName = "";
	String groupId = "";

	int width = 0;
	int height = 0;

	String t0 = "";
	String t1 = "";
	String isbn = "";
	String publisher = "";
	double price = 0;
	String[] author = {};
	String[] tags = null;
	String[] props = null;
	private String play = "";

	public VideoDetailItem() {

	}

	public String getVclass() {
		return vclass;
	}

	private String vclass="";

	public VideoDetailItem(JSONObject obj) {

		this.id = JSONUtil.getString(obj, "_id");
		this.userId = JSONUtil.getString(obj, "userId");
		this.targetId = JSONUtil.getString(obj, "targetId");
		this.play = JSONUtil.getString(obj, "play");
		this.type = JSONUtil.getInt(obj, "type");
		this.seq = JSONUtil.getInt(obj, "seq");
		this.title = JSONUtil.getString(obj, "title");
		this.groupId = JSONUtil.getString(obj, "groupId");
		this.groupName = JSONUtil.getString(obj, "groupName");
		this.parentId = JSONUtil.getString(obj, "parentId");
		this.vclass = JSONUtil.getString(obj, "vclass");

		this.text = JSONUtil.getString(obj, "text");
		this.img = JSONUtil.getString(obj, "img");
		this.width = JSONUtil.getInt(obj, "width");
		this.height = JSONUtil.getInt(obj, "height");

		
		this.tags = JSONUtil.getArrayString(obj, "tags");
		this.props = JSONUtil.getArrayString(obj, "props");
		this.date = JSONUtil.getLongDate(obj, "date");

		publisher = JSONUtil.getString(obj, "publisher");
		t0 = JSONUtil.getString(obj, "t0");
		t1 = JSONUtil.getString(obj, "t1");
		isbn = JSONUtil.getString(obj, "isbn");

		price = JSONUtil.getDouble(obj, "price");// (obj, "publisher");
		author = JSONUtil.getArrayString(obj, "author");

	}

	public String getTagText() {
		String out = "";
		if (price > 0) {
			out += "价格:" + price + " ";

		}

		return out;

	}

	public String getPropText() {
		String out = "";
		if (price > 0) {
			out += "价格:" + price + " ";
			out += "\n";
		}

		if (t0 != null && !t0.equals("")) {
			out += "类型:" + t0 + " ";
			if (t1 != null && !t1.equals("")) {
				out += t1 + " ";
			}
			out += "\n";
		}

		if (isbn != null && !isbn.equals("")) {
			out += "ISBN:" + isbn + " ";
			out += "\n";
		}

		if (author != null && author.length > 0) {
			out += "作者:";
			for (int i = 0; i < author.length; i++) {
				out += author[i] + " ";
			}
			out += "\n";
		}

		if (publisher != null && !publisher.equals("")) {
			out += "出版社:" + publisher + " ";
			out += "\n";
		}

		return out;

	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	
	public String getDetailUrl() {
		// TODO Auto-generated method stub
		return ServerConfig.getVideoUrl(id);
	}

}

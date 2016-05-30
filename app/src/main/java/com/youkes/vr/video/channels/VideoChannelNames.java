/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.channels;

import com.youkes.vr.ChannelItem;

import java.util.ArrayList;

/**
 * Created by xuming on 2015/11/17.
 */
public class VideoChannelNames {

    public static ArrayList<ChannelItem> circleNearChannels= new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> topicChannels= new ArrayList<ChannelItem>();

    public static ArrayList<ChannelItem> defaultUserChannels= new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> channelsMain= new ArrayList<ChannelItem>();

    public static ArrayList<ChannelItem> channelsNear= new ArrayList<ChannelItem>();


    public static ArrayList<ChannelItem> circleDetailChannels=new ArrayList<ChannelItem>();

    public static ArrayList<ChannelItem> visitorUserChannels= new ArrayList<ChannelItem>();

    public static ArrayList<ChannelItem> selectChannels= new ArrayList<ChannelItem>();

    static {

        circleNearChannels.add(new ChannelItem("","1", "圈子", 1, 1));


        topicChannels.add(new ChannelItem("","1", "话题", 1, 1));
        topicChannels.add(new ChannelItem("","1", "圈子", 1, 1));


        defaultUserChannels.add(new ChannelItem("","1", "关注", 1, 1));
        //defaultUserChannels.add(new ChannelItem("","1", "回复", 1, 1));
        //defaultUserChannels.add(new ChannelItem("","1", "评论", 1, 1));

        defaultUserChannels.add(new ChannelItem("","2", "热门", 2, 1));
        //defaultUserChannels.add(new ChannelItem("","2", "圈子", 2, 1));
        defaultUserChannels.add(new ChannelItem("","2", "明星", 2, 1));

        defaultUserChannels.add(new ChannelItem("","3", "电影", 3, 0));
        defaultUserChannels.add(new ChannelItem("","4", "电视剧", 4, 1));
        defaultUserChannels.add(new ChannelItem("","8", "动漫", 8, 0));
        defaultUserChannels.add(new ChannelItem("","5", "综艺", 5, 1));
        defaultUserChannels.add(new ChannelItem("","6", "体育", 6, 0));
        defaultUserChannels.add(new ChannelItem("","7", "小说", 7, 0));

        defaultUserChannels.add(new ChannelItem("","20", "美女", 20, 0));
        defaultUserChannels.add(new ChannelItem("","21", "帅哥", 21, 0));

        defaultUserChannels.add(new ChannelItem("","9", "旅游", 9, 0));
        defaultUserChannels.add(new ChannelItem("","10", "生活", 10, 0));
        defaultUserChannels.add(new ChannelItem("","11", "趣味", 11, 0));
        defaultUserChannels.add(new ChannelItem("","12", "高校", 12, 0));
        defaultUserChannels.add(new ChannelItem("","13", "城市", 13, 0));
        defaultUserChannels.add(new ChannelItem("","14", "艺术", 14, 0));
        defaultUserChannels.add(new ChannelItem("","15", "收藏", 15, 0));
        defaultUserChannels.add(new ChannelItem("","16", "科学", 16, 0));
        defaultUserChannels.add(new ChannelItem("","17", "历史", 17, 0));
        defaultUserChannels.add(new ChannelItem("","18", "程序", 18, 0));
        defaultUserChannels.add(new ChannelItem("","19", "更多", 19, 0));


        channelsNear.add(new ChannelItem("","1", "附近", 2, 1));
        channelsNear.add(new ChannelItem("","2", "校园", 2, 1));
        channelsNear.add(new ChannelItem("","3", "景区", 2, 1));
        channelsNear.add(new ChannelItem("","4", "商圈", 2, 1));//商户



        channelsMain.add(new ChannelItem("","2", "全景", 2, 1));


        circleDetailChannels.add(new ChannelItem("","1", "全部", 1, 1));
        //circleDetailChannels.add(new ChannelItem("","2", "热门", 2, 1));

        //circleDetailChannels.add(new ChannelItem("","3", "成员", 3, 1));
        //circleDetailChannels.add(new ChannelItem("","4", "图片", 4, 1));
        //circleDetailChannels.add(new ChannelItem("","5", "视频", 5, 1));


        selectChannels.add(new ChannelItem("","1", "关注", 1, 1));

        selectChannels.add(new ChannelItem("","2", "全部", 2, 1));
        //defaultUserChannels.add(new ChannelItem("","2", "圈子", 2, 1));
        selectChannels.add(new ChannelItem("","2", "明星", 2, 1));

        selectChannels.add(new ChannelItem("","3", "电影", 3, 0));
        selectChannels.add(new ChannelItem("","4", "电视剧", 4, 1));
        selectChannels.add(new ChannelItem("","8", "动漫", 8, 0));
        selectChannels.add(new ChannelItem("","5", "综艺", 5, 1));
        selectChannels.add(new ChannelItem("","6", "体育", 6, 0));
        selectChannels.add(new ChannelItem("","7", "小说", 7, 0));

        selectChannels.add(new ChannelItem("","20", "美女", 20, 0));
        selectChannels.add(new ChannelItem("","21", "帅哥", 21, 0));
        selectChannels.add(new ChannelItem("","9", "旅游", 9, 0));
        selectChannels.add(new ChannelItem("","10", "生活", 10, 0));
        selectChannels.add(new ChannelItem("","11", "趣味", 11, 0));
        selectChannels.add(new ChannelItem("","12", "高校", 12, 0));
        selectChannels.add(new ChannelItem("","13", "城市", 13, 0));
        selectChannels.add(new ChannelItem("","14", "艺术", 14, 0));
        selectChannels.add(new ChannelItem("","15", "收藏", 15, 0));
        selectChannels.add(new ChannelItem("","16", "科学", 16, 0));
        selectChannels.add(new ChannelItem("","17", "历史", 17, 0));
        selectChannels.add(new ChannelItem("","18", "程序", 18, 0));





        visitorUserChannels.add(new ChannelItem("","1", "全部", 2, 1));
        visitorUserChannels.add(new ChannelItem("","2", "明星", 2, 1));

        visitorUserChannels.add(new ChannelItem("","3", "电影", 3, 0));
        defaultUserChannels.add(new ChannelItem("","4", "电视剧", 4, 1));
        visitorUserChannels.add(new ChannelItem("","8", "动漫", 8, 0));
        visitorUserChannels.add(new ChannelItem("","5", "综艺", 5, 1));
        visitorUserChannels.add(new ChannelItem("","6", "体育", 6, 0));
        visitorUserChannels.add(new ChannelItem("","7", "小说", 7, 0));

        visitorUserChannels.add(new ChannelItem("","20", "美女", 20, 0));
        visitorUserChannels.add(new ChannelItem("","21", "帅哥", 21, 0));

        visitorUserChannels.add(new ChannelItem("","9", "旅游", 9, 0));
        visitorUserChannels.add(new ChannelItem("","10", "生活", 10, 0));
        visitorUserChannels.add(new ChannelItem("","11", "趣味", 11, 0));
        visitorUserChannels.add(new ChannelItem("","12", "高校", 12, 0));
        visitorUserChannels.add(new ChannelItem("","13", "城市", 13, 0));
        visitorUserChannels.add(new ChannelItem("","14", "艺术", 14, 0));
        visitorUserChannels.add(new ChannelItem("","15", "收藏", 15, 0));
        visitorUserChannels.add(new ChannelItem("","16", "科学", 16, 0));
        visitorUserChannels.add(new ChannelItem("","17", "历史", 17, 0));
        visitorUserChannels.add(new ChannelItem("","18", "程序", 18, 0));



    }

}

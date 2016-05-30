/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video;

import com.youkes.vr.ChannelItem;

import java.util.ArrayList;

public class VideoTypeNames {


    public static ArrayList<ChannelItem> defaultMainChannels = new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> defaultTrailerChannels= new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> defaultTagChannels = new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> defaultVideoChannels = new ArrayList<ChannelItem>();
    public static ArrayList<ChannelItem> userVideoChannels = new ArrayList<ChannelItem>();


    static {

        userVideoChannels.add(new ChannelItem("", "1", "用户", 1, 1));
        defaultVideoChannels.add(new ChannelItem("", "1", "全部", 1, 1));
        defaultTagChannels.add(new ChannelItem("", "1", "全部", 1, 1));
        defaultMainChannels.add(new ChannelItem("", "1", "视频", 1, 1));
        defaultMainChannels.add(new ChannelItem("", "1", "频道", 1, 1));

        //defaultMainChannels.add(new ChannelItem("", "2", "电视剧", 1, 1));

        defaultTrailerChannels.add(new ChannelItem("", "1", "全部", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "剧情", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "喜剧", 1, 1));

        defaultTrailerChannels.add(new ChannelItem("", "1", "爱情", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "动作", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "奇幻", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "科幻", 1, 1));

        defaultTrailerChannels.add(new ChannelItem("", "1", "动画", 1, 1));

        defaultTrailerChannels.add(new ChannelItem("", "1", "武侠", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "惊悚", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "恐怖", 1, 1));
        defaultTrailerChannels.add(new ChannelItem("", "1", "战争", 1, 1));





    }


}

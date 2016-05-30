/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

/**
 * Created by xuming on 2016/2/9.
 */
public class MIMEUtil {
    public static boolean isVideo(String mime){
        if(mime==null){
            return false;
        }
        if(mime.indexOf("video")>=0){
            return true;
        }
        return false;
    }

    public static boolean isImage(String mime){
        if(mime==null){
            return false;
        }

        if(mime.indexOf("image")>=0){
            return true;
        }
        return false;
    }

    public static String getExtFromMime(String mime){
        return "";
    }

}

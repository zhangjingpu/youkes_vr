/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import com.youkes.vr.http.NameValuePair;

import java.util.List;

/**
 * Created by xuming on 2016/2/20.
 */
public class HttpReqUtil {
    public static void addNameValueIfNotExist(List<NameValuePair> params,String key, String val) {
        if(params==null){
            return;
        }

        for(NameValuePair p:params){
            if(p.getName().equals(key)){
                return;
            }
        }

        params.add(new NameValuePair(key,val));
    }
}

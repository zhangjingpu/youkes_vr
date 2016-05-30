/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;


public class GeoUtil {


    public static String getLatLngString(double lat, double lng) {

        String latlng="{";
        latlng+="'lat':'"+lat+"',";
        latlng+="'lng':'"+lng+"',";
        latlng+="}";

        return latlng;
    }



}

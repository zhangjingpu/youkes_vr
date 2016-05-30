/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by xuming on 2015/9/28.
 */
public class DpiUtils {
    public static int getPixel(Context context,int dp) {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return height;
    }
}

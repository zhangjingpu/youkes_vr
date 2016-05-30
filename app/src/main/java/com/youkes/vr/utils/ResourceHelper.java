/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import com.youkes.vr.MainApp;


/**
 * 资源文件帮助类
 * Created by Jorstin on 2015/3/18.
 */
public class ResourceHelper {

    static {
        density = -1.0F;
    }

    private static final String TAG = LogUtil.getLogUtilsTag(ResourceHelper.class);

    private static float density;

    /**
     *
     * @param context
     * @param ratio
     * @return
     */
    public static int fromDPToPix(Context context, int ratio) {
        return Math.round(getDensity(context) * ratio);
    }

    /**
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        if (context == null) {
            context = MainApp.getContext();
        }
        if (density < 0.0F){
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }
    /**
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableById(Context context , int resId) {

        if(context == null) {
            LogUtil.e(TAG, "get drawable, resId " + resId + ", but context is null");
            return null;
        }

        return context.getResources().getDrawable(resId);
    }

    /**
     *
     * @param bitmap
     * @param density
     */
    public static Bitmap getDegreeBitmap(Bitmap bitmap , float density) {
        if (density % 360.0F == 0.0F) {
            return bitmap;
        }

        boolean filter = true;
        if(bitmap == null) {
            filter = false;
        }

        Matrix localMatrix = new Matrix();
        localMatrix.reset();
        localMatrix.setRotate(density, bitmap.getWidth() / 2,
                bitmap.getHeight() / 2);
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), localMatrix, filter);
        LogUtil.d(TAG, "  degree:" + density + " , filter" + filter);
        if(resultBitmap != null && resultBitmap != bitmap) {
            bitmap.recycle();
        }
        return resultBitmap;
    }

    /**
     *
     * @param context
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(Context context , int resId) {
        if(context == null) {
            LogUtil.e(TAG, "get drawable, resId " + resId + ", but context is null");
            return null;
        }
        return context.getResources().getColorStateList(resId);
    }
}

/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.youkes.vr.R;

/**

 */
public class NotificationUtil {
	
	public static final String TAG = LogUtil.getLogUtilsTag(Notification.class);

	@TargetApi(VERSION_CODES.HONEYCOMB)
	public static Notification buildNotification(Context context, int icon,
			int defaults, boolean onlyVibrate, String tickerText,
			String contentTitle, String contentText, Bitmap largeIcon,
			PendingIntent intent) {
		int color=context.getResources().getColor(R.color.white);
		if(Build.VERSION.SDK_INT > VERSION_CODES.HONEYCOMB) {
			Notification.Builder builder = new Notification.Builder(context);

			builder.setLights(color, 300, 1000)
			.setSmallIcon(icon)
			.setTicker(tickerText)
			.setContentTitle(contentTitle)
			.setContentText(contentText)
			.setContentIntent(intent);
			
			if(onlyVibrate) {
				defaults &= Notification.DEFAULT_VIBRATE;
			} 
			
			LogUtil.d(TAG, "defaults flag " + defaults);
			builder.setDefaults(defaults);
			if(largeIcon != null) {
				builder.setLargeIcon(largeIcon);
			}
			return builder.getNotification();
		}


		Notification notification = new Notification();
		notification.ledARGB =color;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags = (Notification.FLAG_SHOW_LIGHTS | notification.flags);
		notification.icon = icon;
		notification.tickerText = tickerText;
		LogUtil.d(TAG, "defaults flag " + defaults);
		if(onlyVibrate) {
			defaults &= Notification.DEFAULT_VIBRATE;
		} 
		notification.defaults = defaults;
		//notification.setLatestEventInfo(context, contentTitle, contentText, intent);
	    return notification;
	}
	
}

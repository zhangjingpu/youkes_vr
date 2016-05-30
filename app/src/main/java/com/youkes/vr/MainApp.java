/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;


import com.umeng.analytics.MobclickAgent;
import com.youkes.vr.crash.CrashHandler;
import com.youkes.vr.pref.PreferenceUtils;
import com.youkes.vr.ui.emoji.EmoticonUtil;
import com.youkes.vr.http.NetWorkHelper;

import java.util.ArrayList;
import java.util.HashMap;
import android.support.multidex.MultiDexApplication;
import android.support.multidex.MultiDex;

public class MainApp extends MultiDexApplication {


        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
            MultiDex.install(this);
        }


    private static MainApp instance;
    public static Context getContext() {
        return context;
    }

    private static Context context;
    /**
     * 单例，返回一个实例
     * @return
     */
    public static MainApp getInstance() {
        if (instance == null) {

        }
        return instance;
    }


    public  boolean hasNetwork() {
        if(context==null){
            return  false;
        }
        android.net.ConnectivityManager cManager = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }





    public Location getLocation(Activity activity) {
        // 获取位置管理服务
        LocationManager locationManager

                = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, getString(R.string.please_open_gps), Toast.LENGTH_SHORT)
                    .show();
            //Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            //activity.startActivity(intent);
            return null;
        }

        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        return location;

        /*
        updateToNewLocation(location);
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        updateToNewLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
                */
    }



    /**
     * 获取应用程序版本名称
     * @return
     */
    public String getVersion() {
        String version = "0.0.0";
        if(context == null) {
            return version;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }



    /**
     * 获取应用版本号
     * @return 版本号
     */
    public int getVersionCode() {
        int code = 1;
        if(context == null) {
            return code;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            code = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }




    @Override
    public void onCreate() {
        super.onCreate();



        context = getApplicationContext();
        instance = this;

        setChattingContactId();
        initImageLoader();
        



        if(EmoticonUtil.getEmojiSize() == 0) {
            EmoticonUtil.initEmoji();
        }

        initEmoji();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());



    }
    ArrayList<String> faceList =new ArrayList<String>();

    HashMap<String,Bitmap> faceBitmapMap =new HashMap<String,Bitmap>();

    public Bitmap getFaceBitmap(String key){
        if(!faceBitmapMap.containsKey(key)){
            return null;
        }
        return faceBitmapMap.get(key);

    }
    private void initEmoji() {


    }



    public boolean isNetworkAvailable() {
		return NetWorkHelper.isNetworkAvailable(getBaseContext());
	}
    

    private void setChattingContactId() {

    }

    private void initImageLoader() {
    }





    public float getScreenHeight() {
		return getBaseContext().getResources().getDisplayMetrics().heightPixels;

	}

	public float getScreenWidth() {
		return getBaseContext().getResources().getDisplayMetrics().widthPixels;

	}
	
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    public int fromDPToPix( int dp) {
        return Math.round(getDensity() * dp);
    }

    float density=0;
    public float getDensity() {
        Context context= getContext();
        if (density <= 0){
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }





    public void logout() {
        PreferenceUtils.setAnonymous();
        release();
        MobclickAgent.onProfileSignOff();
    }


    public static void release() {

    }


}

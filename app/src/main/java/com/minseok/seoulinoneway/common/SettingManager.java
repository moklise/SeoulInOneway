package com.minseok.seoulinoneway.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by minseok on 2018. 8. 31..
 * SeoulInOneWay.
 */
public class SettingManager {

    Context mContext;
    SharedPreferences mSharedPref;
    SharedPreferences.Editor mSharedPrefEdit;

    Boolean isFirst;
    Float longitude;
    Float latitude;
    String regionName;

    public boolean isLoaded = false;

    public static final String KEY_IS_FIRST = "isfirst";            // 최초 시작
    public static final String KEY_LONGITUDE = "pos_longitude";     // 경도
    public static final String KEY_LATITUDE = "pos_ latitude";      // 위도
    public static final String KEY_REGION_NAME = "pos_latitude";    // 지역 이름 (중구, 중랑구)

    public SettingManager(Context mContext) {
        this.mContext = mContext;
        this.mSharedPref = mContext.getSharedPreferences("seoulinoneway_sharedprefernce", Activity.MODE_PRIVATE);
        this.mSharedPrefEdit = mSharedPref.edit();
    }

    public void loadSettingValue() {
        if (mContext != null && mSharedPref != null) {
            longitude = mSharedPref.getFloat(KEY_LONGITUDE, 0);
            latitude = mSharedPref.getFloat(KEY_LATITUDE, 0);
            regionName = mSharedPref.getString(KEY_REGION_NAME, "");
            isFirst = mSharedPref.getBoolean(KEY_IS_FIRST, true);

            isLoaded = true;
        }
    }

    public Float getLongitude() { return longitude; }
    public void setLongitude(Float longitude) {
        if (mSharedPrefEdit != null && isLoaded) {
            mSharedPrefEdit
                    .putFloat(KEY_LONGITUDE, longitude)
                    .commit();
        }

        this.longitude = longitude;
    }

    public Float getLatitude() { return latitude; }
    public void setLatitude(Float latitude) {
        if (mSharedPrefEdit != null && isLoaded) {
            mSharedPrefEdit
                    .putFloat(KEY_LATITUDE, latitude)
                    .commit();
        }

        this.latitude = latitude;
    }

    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) {
        if (mSharedPrefEdit != null && isLoaded) {
            mSharedPrefEdit
                    .putString(KEY_REGION_NAME, regionName)
                    .commit();
        }

        this.regionName = regionName;
    }

    public Boolean getFirst() { return isFirst; }
    public void setFirst(Boolean first) {
        if (mSharedPrefEdit != null && isLoaded) {
            mSharedPrefEdit.putBoolean(KEY_IS_FIRST, isFirst)
                    .commit();
        }

        isFirst = first;
    }
}

package com.minseok.seoulinoneway;

import android.app.Application;
import android.content.Context;

import com.minseok.seoulinoneway.common.SettingManager;

/**
 * Created by minseok on 2018. 8. 31..
 * SeoulInOneWay.
 */
public class SeoulInOneWayApplication extends Application {
    private static SettingManager mSettingManager = null;
    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
    }

    public static SettingManager getSettingManager() {
        if (mSettingManager == null) {
            mSettingManager = new SettingManager( mContext);
        }

        if (!mSettingManager.isLoaded) {
            mSettingManager.loadSettingValue();
        }

        return mSettingManager;
    }
}

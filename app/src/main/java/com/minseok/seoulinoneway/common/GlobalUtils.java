package com.minseok.seoulinoneway.common;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by minseok on 2018. 7. 10..
 * SeoulInOneWay.
 */
public class GlobalUtils {
    public static int dpTopx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}

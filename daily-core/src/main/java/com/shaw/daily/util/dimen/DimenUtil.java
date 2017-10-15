package com.shaw.daily.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.shaw.daily.app.Daily;

/**
 * Created by shaw on 2017/8/31.
 */

public class DimenUtil {
    private static final Resources resources = Daily.getApplicationContext().getResources();
    private static final DisplayMetrics dm = resources.getDisplayMetrics();

    public static int getScreenWidth() {
        return (int) (dm.widthPixels/getDensity());
    }

    public static int getScreenHeight() {
        return (int) (dm.heightPixels/getDensity());
    }

    private static float getDensity() {
        return dm.density;
    }

}

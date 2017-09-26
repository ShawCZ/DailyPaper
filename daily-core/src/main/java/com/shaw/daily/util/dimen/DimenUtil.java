package com.shaw.daily.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.shaw.daily.app.Daily;

/**
 * Created by shaw on 2017/8/31.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = Daily.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Daily.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}

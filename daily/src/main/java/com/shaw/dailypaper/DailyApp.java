package com.shaw.dailypaper;


import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.shaw.daily.app.Daily;
import com.shaw.daily.dp.icon.FontDPModule;

/**
 * Created by shaw
 */

public class DailyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Daily.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontDPModule())
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}

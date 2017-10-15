package com.shaw.dailypaper;


import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.shaw.daily.app.Daily;
import com.shaw.daily.dp.icon.FontDPModule;
import com.shaw.daily.net.interceptors.DebugInterceptor;
import com.shaw.dailypaper.event.ShareEvent;
import com.shaw.dailypaper.event.TestEvent;

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
                .withInterceptor(new DebugInterceptor("text",R.raw.latest))
                .withApiHost("http://news-at.zhihu.com/api/4/")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share", new ShareEvent())
                .configure();
    }
}

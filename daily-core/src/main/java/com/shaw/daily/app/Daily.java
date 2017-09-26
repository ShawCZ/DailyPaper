package com.shaw.daily.app;

import android.content.Context;

/**
 * Created by shaw
 */

public final class Daily {
    public static Configurator init(Context context){
        getConfigurator().getDailyConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());
         return Configurator.getInstance();
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    //获取全局Context
    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

}

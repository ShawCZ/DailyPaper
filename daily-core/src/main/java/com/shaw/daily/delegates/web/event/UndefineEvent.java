package com.shaw.daily.delegates.web.event;

import android.util.Log;

/**
 * Created by
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        Log.e("UndefineEvent", params);
        return null;
    }
}

package com.shaw.dailypaper;

import com.shaw.daily.activitys.ProxyActivity;
import com.shaw.daily.delegates.DailyDelegate;

public class DailyActivity extends ProxyActivity {

    @Override
    public DailyDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}

package com.shaw.dailypaper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.shaw.daily.activitys.ProxyActivity;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.main.index.IndexDelegate;

public class DailyActivity extends ProxyActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

    }

    @Override
    public DailyDelegate setRootDelegate() {
        return new IndexDelegate();
    }
}

package com.shaw.dailypaper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;

/**
 * Created by shaw
 */

public class ExampleDelegate extends DailyDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}

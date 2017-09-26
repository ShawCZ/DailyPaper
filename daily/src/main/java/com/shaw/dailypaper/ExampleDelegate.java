package com.shaw.dailypaper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;

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
        RestClient.builder()
                .url("http://www.baidu.com")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();

    }
}

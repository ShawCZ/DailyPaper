package com.shaw.daily.dp.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;

import butterknife.OnClick;

/**
 * Created by shaw on 2017/10/8.
 */

public class LoginDelegate extends DailyDelegate {

    @OnClick(R2.id.icon_login_back)
    void onClickLoginBack(){
        getProxyActivity().getSupportDelegate().pop();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}

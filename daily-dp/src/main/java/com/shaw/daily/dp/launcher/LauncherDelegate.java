package com.shaw.daily.dp.launcher;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.dp.main.index.IndexDelegate;
import com.shaw.daily.ui.launcher.ScrollLauncherTag;
import com.shaw.daily.util.storage.DailyPreference;
import com.shaw.daily.util.timer.BaseTimerTask;
import com.shaw.daily.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by shaw on 2017/9/26.
 */

public class LauncherDelegate extends DailyDelegate implements ITimerListener {

    private Timer mTimer = null;
    private int mCount = 5;

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTvTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    //判断是否显示滑动轮播图
    private void checkIsShowScroll() {
        if (!DailyPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().startWithPop(new LauncherScrollDelegate());
        } else {
            //检查用户是否登陆
            //进入主页
            getSupportDelegate().startWithPop(new IndexDelegate());
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTimer();
//        RestClient.builder()
//                .url("news/latest")
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        new IndexDataConverter().setJsonData(response).convert();
//                    }
//                })
//                .build()
//                .get();

    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format(" 跳过\n {0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}

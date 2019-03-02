package com.shaw.daily.dp.launcher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.main.index.IndexDelegate;
import com.shaw.daily.ui.launcher.LauncherHolderCreator;
import com.shaw.daily.ui.launcher.ScrollLauncherTag;
import com.shaw.daily.util.storage.DailyPreference;

import java.util.ArrayList;

/**
 * Created by shaw
 */

public class LauncherScrollDelegate extends DailyDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private CharSequence text = "text";

    @SuppressLint("NewApi")
    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_00);
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        mConvenientBanner.setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int poistion) {
        if (poistion == INTEGERS.size()-1){
            //设置app已经第一次打开了
            DailyPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //进入主页
            getSupportDelegate().startWithPop(new IndexDelegate());
        }
    }
}

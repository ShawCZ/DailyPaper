package com.shaw.daily.dp.main.navigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.dp.login.LoginDelegate;
import com.shaw.daily.dp.main.index.IndexItemClickListener;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.util.storage.DailyPreference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by shaw
 * @author hx
 */

public class NavigationDelegate extends DailyDelegate {
    private static final String TAG = "NavigationDelegate";

    @BindView(R2.id.drawer_user)
    LinearLayoutCompat mDrawerUser = null;
    @BindView(R2.id.drawer_user_avatar)
    AppCompatImageView mDrawerUserAvatar = null;
    @BindView(R2.id.drawer_user_name)
    AppCompatTextView mDrawerUserName = null;
    @BindView(R2.id.user_collection)
    LinearLayoutCompat mDrawerUserCollection = null;
    @BindView(R2.id.user_message)
    LinearLayoutCompat mDrawerUserMessage = null;
    @BindView(R2.id.daily_setting)
    LinearLayoutCompat mDrawerDailySetting = null;
    @BindView(R2.id.navigation_recycler_view)
    RecyclerView mRecyclerView = null;

    @OnClick(R2.id.drawer_user)
    void onClickLogin(){
        getProxyActivity().getSupportDelegate().start(new LoginDelegate());
    }

    private NavigationAdapter adapter = null;
    private View mHolderView = null;


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(this));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_navigation;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mDrawerUserName.setText(DailyPreference.getPhoneNumber(LoginDelegate.PHONENUMBER));
        initRecyclerView();
        RestClient.builder()
                .url("themes")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mHolderView = LayoutInflater.from(getContext()).inflate(R.layout.item_navigation_header,null,false);
                        adapter = new NavigationAdapter(new NavigationDataConverter().setJsonData(response).convert(),NavigationDelegate.this);
                        adapter.setHeaderView(mHolderView);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setAnimation(null);
                    }
                })
                .build()
                .get();
    }
}

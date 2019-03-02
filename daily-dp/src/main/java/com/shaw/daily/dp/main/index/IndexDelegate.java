package com.shaw.daily.dp.main.index;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.dp.main.Rotuer;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;
import com.shaw.daily.ui.recycler.RgbValue;
import com.shaw.daily.ui.refresh.RefreshHandler;
import com.shaw.daily.util.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by shaw
 * @author hx
 */

public class IndexDelegate extends DailyDelegate {
    private static final String TAG = "IndexDelegate";

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_menu)
    IconTextView mIconMenu = null;
    @BindView(R2.id.tv_index_title)
    AppCompatTextView mTitle = null;
    @BindView(R2.id.loyout_drawer)
    DrawerLayout mDrawerLayout = null;

    @OnClick(R2.id.icon_index_menu)
    void onClickNavigation(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private RefreshHandler mRefreshHandler = null;
    private LinearLayoutManager manager = new LinearLayoutManager(getContext());
    private MultipleRecyclerAdapter mAdapter = new MultipleRecyclerAdapter(null);

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(this));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RgbValue RGB_VALUE = RgbValue.create(0, 153, 204);
        StatusBarUtil.setStatusBarColor(getProxyActivity(), Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        mRefreshHandler = new RefreshHandler(mRefreshLayout, new IndexDataConverter(), mRecyclerView,mAdapter);
        Rotuer.getInstance().setIndexDelegate(this);
    }

	@Override
	public void onStart() {
		super.onStart();
		StatusBarUtil.setRootViewFitsSystemWindows(getProxyActivity(),true);
	}

	@Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage();
    }

    //双击退出程序事件设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)){
            mDrawerLayout.closeDrawer(Gravity.START);
        }else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish();
            } else {

                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}

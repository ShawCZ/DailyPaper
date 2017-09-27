package com.shaw.daily.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.shaw.daily.app.Daily;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.ui.recycler.DataConverter;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by shaw
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final RecyclerView RECYCLEVIEW;

    public RefreshHandler(SwipeRefreshLayout refreshLayout, DataConverter converter, RecyclerView recycleview) {
        REFRESH_LAYOUT = refreshLayout;
        CONVERTER = converter;
        RECYCLEVIEW = recycleview;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }
    
    public void firstPage(String url){
        RestClient.builder()
                .url("text")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        RECYCLEVIEW.setAdapter(mAdapter);
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Daily.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }
}

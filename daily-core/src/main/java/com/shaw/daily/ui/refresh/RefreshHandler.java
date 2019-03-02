package com.shaw.daily.ui.refresh;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shaw.daily.app.Daily;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.ui.recycler.DataConverter;
import com.shaw.daily.ui.recycler.FloatingItemDecoration;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;
import com.shaw.daily.util.date.CurrentDateUitl;

/**
 * Created by shaw
 */

public class RefreshHandler extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final MultipleRecyclerAdapter ADAPTER;
    private final DataConverter CONVERTER;
    private final RecyclerView RECYCLEVIEW;
    private final FloatingItemDecoration floatingItemDecoration;

    //第一次加载更多
    private boolean isFirstLoadMore = false;
    private int dateParams = 0;

    public RefreshHandler(SwipeRefreshLayout refreshLayout, DataConverter converter, RecyclerView recycleview, MultipleRecyclerAdapter adapter) {
        REFRESH_LAYOUT = refreshLayout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
        CONVERTER = converter;
        RECYCLEVIEW = recycleview;
        ADAPTER = adapter;
        RECYCLEVIEW.setAdapter(ADAPTER);
        RECYCLEVIEW.addOnScrollListener(this);
        ADAPTER.setOnLoadMoreListener(RefreshHandler.this, RECYCLEVIEW);

        floatingItemDecoration = new FloatingItemDecoration(Daily.getApplicationContext(), 0.1f, 0.1f);
        floatingItemDecoration.setTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, Daily.getApplicationContext().getResources().getDisplayMetrics()));
        RECYCLEVIEW.setHasFixedSize(true);

    }

    public void firstPage() {
        RestClient.builder()
                .url("news/latest")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        CONVERTER.clearData();

                        ADAPTER.addData(CONVERTER.setJsonData(response).convert());
                        floatingItemDecoration.setKeys(ADAPTER.getKeys());
                        RECYCLEVIEW.addItemDecoration(floatingItemDecoration);
                    }
                })
                .build()
                .get();
    }

    //SwipeRefreshLayout下拉刷新
    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);

        //进行一些网络请求
        RestClient.builder()
                .url("news/latest")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        CurrentDateUitl.restart();
                        isFirstLoadMore = false;
                        dateParams = 0;
                        CONVERTER.clearData();
                        ADAPTER.getData().clear();
                        ADAPTER.replaceData(CONVERTER.setJsonData(response).convert());
                    }
                })
                .build()
                .get();

        REFRESH_LAYOUT.setRefreshing(false);
    }

    //加载之前的数据
    @Override
    public void onLoadMoreRequested() {
        /**
         * 请求的URL为：news/before/+dateParams
         * */
        String date = CurrentDateUitl.getBackDate(dateParams);
        Log.d("数据条数", ADAPTER.getData().size() + "");
        Log.d("DailyPaper", date);


        RestClient.builder()
                .url("news/before/" + date)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //日期位置
                        int position = ADAPTER.getData().size();
                        //千万不要重复设置Adapter，不然刷新完会一直弹回到第一条消息
                        ADAPTER.setNewData(CONVERTER.setJsonData(response).convert());
                        ADAPTER.loadMoreComplete();
                        ADAPTER.getKeys().put(position, ADAPTER.getData().get(position).getField(MultipleFields.DATE).toString());
                        floatingItemDecoration.setKeys(ADAPTER.getKeys());
                    }
                })
                .build()
                .get();

        if (!isFirstLoadMore) {
            dateParams--;
            isFirstLoadMore = true;
        }

    }
}

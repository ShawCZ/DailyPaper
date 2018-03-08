package com.shaw.daily.dp.main.theme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.dp.main.detial.DetailDelegate;
import com.shaw.daily.dp.main.index.IndexItemClickListener;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;

import butterknife.BindView;

/**
 *
 * @author hx
 * @date 2018/2/4
 */

public class ThemeListDelegate extends DailyDelegate {
	@BindView(R2.id.rv_theme_list)
	RecyclerView mRecyclerView = null;

	private LinearLayoutManager manager = new LinearLayoutManager(getContext());
	private MultipleRecyclerAdapter adapter = new MultipleRecyclerAdapter(null);

	public static ThemeListDelegate create(int storyId) {
		final Bundle args = new Bundle();
		args.putInt("THEME_ID", storyId);
		final ThemeListDelegate delegate = new ThemeListDelegate();
		delegate.setArguments(args);
		return delegate;
	}

	@Override
	public Object setLayout() {
		return R.layout.delegate_theme_list;
	}

	@Override
	public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
		mRecyclerView.setAdapter(adapter);
		int themeId = getArguments().getInt("THEME_ID");
		RestClient.builder()
				.url("theme/"+themeId)
				.success(new ISuccess() {
					@Override
					public void onSuccess(String response) {
						Log.d("theme", "response = "+response);
					}
				})
				.build()
				.get();
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		initRecyclerView();
	}

	private void initRecyclerView() {
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(this));
	}
}

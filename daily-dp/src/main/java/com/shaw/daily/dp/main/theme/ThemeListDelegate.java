package com.shaw.daily.dp.main.theme;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.dp.main.index.IndexItemClickListener;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;

import butterknife.BindView;

/**
 * @author hx
 * @date 2018/2/4
 */

public class ThemeListDelegate extends DailyDelegate {
	@BindView(R2.id.rv_theme_list)
	RecyclerView mRecyclerView = null;

	private LinearLayoutManager manager;
	private MultipleRecyclerAdapter adapter;

	public static ThemeListDelegate create(String storyId) {
		Bundle args = new Bundle();
		args.putString("THEME_ID", storyId);
		ThemeListDelegate delegate = new ThemeListDelegate();
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
		String themeId = getArguments().getString("THEME_ID");
		RestClient.builder()
				.url("theme/" + themeId)
				.success(new ISuccess() {
					@Override
					public void onSuccess(String response) {
						Log.d("theme", "response = " + response);
						adapter = new ThemeListAdapter(new ThemeListDataConverter().setJsonData(response).convert(),ThemeListDelegate.this);
						mRecyclerView.setAdapter(adapter);
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
		manager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(this));
	}
}

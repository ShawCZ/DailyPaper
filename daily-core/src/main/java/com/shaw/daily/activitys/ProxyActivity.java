package com.shaw.daily.activitys;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;

import com.shaw.daily.R;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.util.SystemBarHelper;
import com.shaw.daily.util.statusbar.StatusBarUtil;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by shaw
 */

public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

	private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

	public abstract DailyDelegate setRootDelegate();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DELEGATE.onCreate(savedInstanceState);
		initContainer(savedInstanceState);
	}

	private void initContainer(@Nullable Bundle savedInstanceState) {
		final ContentFrameLayout container = new ContentFrameLayout(this);
		container.setId(R.id.delegate_container);
		setContentView(container);

		//当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
		StatusBarUtil.setRootViewFitsSystemWindows(this, true);
		//设置状态栏透明
		StatusBarUtil.setTranslucentStatus(this);
		//一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
		//所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
		if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
			//如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
			//这样半透明+白=灰, 状态栏的文字能看得清
			StatusBarUtil.setStatusBarColor(this, 0x55000000);
		}
		setStatusBar();
		if (savedInstanceState == null) {
			DELEGATE.loadRootFragment(R.id.delegate_container, setRootDelegate());
		}
	}

	private void setStatusBar() {
		Window window = getWindow();
		ViewGroup container = (ViewGroup) window.getDecorView();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			View statusBarView = container.findViewById(R.id.statusbar_view);
			if (statusBarView == null) {
				statusBarView = new View(container.getContext());
				statusBarView.setId(R.id.statusbar_view);
				ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(container.getContext()));
				container.addView(statusBarView, 0, lp);
			}

			statusBarView.setBackgroundColor(android.R.color.holo_blue_dark);
			statusBarView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onDestroy() {
		DELEGATE.onDestroy();
		super.onDestroy();
		System.gc();
		System.runFinalization();
	}

	@Override
	public SupportActivityDelegate getSupportDelegate() {
		return DELEGATE;
	}

	@Override
	public ExtraTransaction extraTransaction() {
		return DELEGATE.extraTransaction();
	}

	@Override
	public FragmentAnimator getFragmentAnimator() {
		return DELEGATE.getFragmentAnimator();
	}

	@Override
	public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
		DELEGATE.setFragmentAnimator(fragmentAnimator);
	}

	@Override
	public FragmentAnimator onCreateFragmentAnimator() {
		return DELEGATE.onCreateFragmentAnimator();
	}

	@Override
	public void onBackPressedSupport() {
		DELEGATE.onBackPressedSupport();
	}

	@Override
	public void onBackPressed() {
		DELEGATE.onBackPressed();
	}

	@Override
	public void post(Runnable runnable) {
		DELEGATE.post(runnable);
	}
}

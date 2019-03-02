package com.shaw.daily.activitys;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.shaw.daily.R;
import com.shaw.daily.delegates.BaseNormalDelegate;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.util.statusbar.StatusBarUtil;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by shaw
 */

public abstract class BaseActivity extends AppCompatActivity implements ISupportActivity {

	private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

	public abstract BaseNormalDelegate setRootDelegate();

	public abstract int setContainerId();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DELEGATE.onCreate(savedInstanceState);
		initContainer(savedInstanceState);
	}

	private void initContainer(@Nullable Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			DELEGATE.loadRootFragment(setContainerId(), setRootDelegate());
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

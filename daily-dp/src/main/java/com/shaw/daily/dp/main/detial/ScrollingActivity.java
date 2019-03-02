package com.shaw.daily.dp.main.detial;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shaw.daily.activitys.BaseActivity;
import com.shaw.daily.delegates.BaseNormalDelegate;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;


public class ScrollingActivity extends BaseActivity {

	@Override
	public BaseNormalDelegate setRootDelegate() {
		return new DetailDelegate();
	}

	@Override
	public int setContainerId() {
		return R.id.container;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
			}
		});

		AppCompatImageView imageView = findViewById(R.id.img);
		imageView.setImageResource(R.drawable.login_bg);
	}
}


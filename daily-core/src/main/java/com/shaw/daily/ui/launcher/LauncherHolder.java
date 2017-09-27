package com.shaw.daily.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by shaw on 2017/9/26.
 */

public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int i, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}

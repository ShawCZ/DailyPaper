package com.shaw.daily.dp.main.index;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.main.detial.DetailDelegate;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by shaw
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final DailyDelegate DELEGATE;
    private DetailDelegate delegate = null;

    private IndexItemClickListener(DailyDelegate delegate) {
        DELEGATE = delegate;
    }

    public static SimpleClickListener create(DailyDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        switch (entity.getItemType()) {
            case 1:
                break;
            case 2:
                @SuppressWarnings("unchecked")
                final ConvenientBanner<String> banner = (ConvenientBanner<String>) baseQuickAdapter.getViewByPosition(0, R.id.banner_recycler_item);
                final ArrayList<Integer> storyBannerIds = entity.getField(MultipleFields.ID);
                final int storyBannerId;
                if (banner != null) {
                    storyBannerId = storyBannerIds.get(banner.getCurrentItem());
                    delegate = DetailDelegate.create(storyBannerId);
                }
                DELEGATE.getSupportDelegate().start(delegate);
                break;
            case 3:
                final int storyId = entity.getField(MultipleFields.ID);
                delegate = DetailDelegate.create(storyId);
                DELEGATE.getSupportDelegate().start(delegate);
                break;
            case 4:
                final int themeId = entity.getField(MultipleFields.ID);

                break;
            default:
                break;
        }

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }
}

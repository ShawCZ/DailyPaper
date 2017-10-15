package com.shaw.daily.dp.main.navigation;

import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.ui.recycler.ItemType;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleItemEntity;
import com.shaw.daily.ui.recycler.MultipleRecyclerAdapter;
import com.shaw.daily.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by shaw
 */

public class NavigationAdapter extends MultipleRecyclerAdapter {
    private final DailyDelegate DELEGATE;
    private View mHeaderView;

    public NavigationAdapter(List<MultipleItemEntity> data, DailyDelegate delegate) {
        super(data);
        DELEGATE = delegate;
        addItemType(ItemType.THEMES, R.layout.item_navigation_menu);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.THEMES:
                final String name  = entity.getField(MultipleFields.NAME);
                final boolean isItemClick = entity.getField(MultipleFields.TAG);
                holder.setText(R.id.item_sotry_theme,name);
                break;
            default:
                break;
        }
    }

    //设置RecyclerView头部布局
    @Override
    public int setHeaderView(View header) {
        return super.setHeaderView(header);
    }

}

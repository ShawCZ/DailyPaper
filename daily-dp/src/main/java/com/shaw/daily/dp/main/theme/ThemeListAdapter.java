package com.shaw.daily.dp.main.theme;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

public class ThemeListAdapter extends MultipleRecyclerAdapter {
    private final DailyDelegate DELEGATE;
    private View mHeaderView;

    private String title;
    private String imageUrl;

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public ThemeListAdapter(List<MultipleItemEntity> data, DailyDelegate delegate) {
        super(data);
        DELEGATE = delegate;
        addItemType(ItemType.THEMES, R.layout.item_multiple_story_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
//        switch (holder.getItemViewType()){
//            case ItemType.THEMES:
                title = entity.getField(MultipleFields.TITLE);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_multiple, title);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
//
//                break;
//            default:
//                break;
//        }
    }

    //设置RecyclerView头部布局
    @Override
    public int setHeaderView(View header) {
        return super.setHeaderView(header);
    }

}

package com.shaw.daily.ui.recycler;

import androidx.annotation.Nullable;
import androidx.transition.Visibility;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.shaw.daily.R;
import com.shaw.daily.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaw
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder> implements OnItemClickListener, ViewPager.OnPageChangeListener {


    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;

    private ConvenientBanner<String> convenientBanner;

    private String title;
    private String imageUrl;
    private ArrayList<String> bannerImages;
    private ArrayList<String> bannerTitles;
    private String date;

    //存放日期
    private Map<Integer, String> keys = new HashMap<>();

    public Map<Integer, String> getKeys() {
        return keys;
    }

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();

        keys.put(0,"今日新闻");

    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter dataConverter) {
        return new MultipleRecyclerAdapter(dataConverter.convert());
    }


    private void init() {
        //设置不同item布局
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        addItemType(ItemType.STORY_LIST, R.layout.item_multiple_story_list);
        addItemType(ItemType.DATE, R.layout.item_multiple_date);

        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    private MultipleViewHolder mHolder;

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        switch (holder.getItemViewType()) {
            case ItemType.DATE:
//                date = entity.getField(MultipleFields.DATE);
//                holder.setText(R.id.tv_date, date);
               break;
            case ItemType.STORY_LIST:
                title = entity.getField(MultipleFields.TITLE);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_multiple, title);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));

                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    bannerTitles = entity.getField(MultipleFields.TITLE);
                    convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    holder.setText(R.id.banner_title, bannerTitles.get(0));
                    convenientBanner.setOnPageChangeListener(this);
                    convenientBanner.setOnItemClickListener(this);
                    mHolder = holder;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    //动态设置banner标题
    @Override
    public void onPageSelected(int position) {
        mHolder.setText(R.id.banner_title, bannerTitles.get(convenientBanner.getCurrentItem()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void setNewData(@Nullable List<MultipleItemEntity> data) {
        if (data == null) {
            this.mData = (List) new ArrayList();
        } else {
            this.mData = (List) data;
        }
        notifyItemRangeChanged(mData.size() - data.size(), getItemCount());
    }


}

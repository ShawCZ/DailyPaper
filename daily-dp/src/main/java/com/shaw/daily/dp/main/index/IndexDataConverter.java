package com.shaw.daily.dp.main.index;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaw.daily.app.Daily;
import com.shaw.daily.ui.recycler.DataConverter;
import com.shaw.daily.ui.recycler.ItemType;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleItemEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by shaw
 */

public class IndexDataConverter extends DataConverter {

//    @SuppressLint("SimpleDateFormat")
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//    String date = sdf.format(new java.util.Date());

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final String storyDate = JSON.parseObject(getJsonData()).getString("date");
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setItemType(ItemType.DATE)
                .setField(MultipleFields.DATE, storyDate)
                .build();
        ENTITYS.add(entity);

        //取出当天的新闻轮播图
        //storyDate.equals(date)
        if (true) {
            ENTITYS.remove(0);
            Log.d("今天的日报", storyDate);
            final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("top_stories");
            final int size = dataArray.size();

            final ArrayList<Integer> bannerIds = new ArrayList<>();
            final ArrayList<String> bannerImages = new ArrayList<>();
            final ArrayList<String> bannerTitles = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                final JSONObject data = dataArray.getJSONObject(i);

                final int id = data.getInteger("id");
                final String imageUrl = data.getString("image");
                final String title = data.getString("title");

                bannerIds.add(id);
                bannerTitles.add(title);
                bannerImages.add(imageUrl);
            }
            int type = ItemType.BANNER;
            final MultipleItemEntity entityBanner = MultipleItemEntity.builder()
                    .setItemType(type)
                    .setField(MultipleFields.ID, bannerIds)
                    .setField(MultipleFields.TITLE, bannerTitles)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .build();

            ENTITYS.add(entityBanner);
        }

        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("stories");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final int id = data.getInteger("id");
            final String title = data.getString("title");

            final JSONArray images = data.getJSONArray("images");
            final String imageUrl = images.getString(0);

            int type = ItemType.STORY_LIST;
            final MultipleItemEntity entityStoryList = MultipleItemEntity.builder()
                    .setItemType(type)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TITLE, title)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .build();

            ENTITYS.add(entityStoryList);
        }

        return ENTITYS;
    }
}

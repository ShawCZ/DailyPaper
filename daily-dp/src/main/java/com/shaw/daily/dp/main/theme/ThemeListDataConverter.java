package com.shaw.daily.dp.main.theme;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaw.daily.ui.recycler.DataConverter;
import com.shaw.daily.ui.recycler.ItemType;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by shaw
 */

public class ThemeListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("stories");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final int id = data.getInteger("id");
            final String title = data.getString("title");
            final JSONArray images = data.getJSONArray("images");
            String imageUrl = "";
            if (images != null){
                 imageUrl = images.getString(0);
            }


            int type = ItemType.THEMES;
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

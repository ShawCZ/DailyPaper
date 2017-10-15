package com.shaw.daily.dp.main.navigation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaw.daily.ui.recycler.DataConverter;
import com.shaw.daily.ui.recycler.ItemType;
import com.shaw.daily.ui.recycler.MultipleFields;
import com.shaw.daily.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by shaw on 2017/10/8.
 */

public class NavigationDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("others");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String thumbnail = data.getString("thumbnail");
            final String description = data.getString("description");
            final String color = data.getString("color");
            final boolean isItemClick = false;

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(ItemType.THEMES)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.IMAGE_URL, thumbnail)
                    .setField(MultipleFields.TEXT, description)
                    .setField(MultipleFields.COLOR, color)
                    .setField(MultipleFields.TAG,isItemClick)
                    .build();

            ENTITYS.add(entity);

        }
        return ENTITYS;
    }
}

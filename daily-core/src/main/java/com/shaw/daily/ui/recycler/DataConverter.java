package com.shaw.daily.ui.recycler;

import java.util.ArrayList;

/**
 * Created by shaw
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITYS = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL");
        }
        return mJsonData;
    }

    public ArrayList<MultipleItemEntity> getEntities(){
        return ENTITYS;
    }

    public void clearData(){
        ENTITYS.clear();
    }

}

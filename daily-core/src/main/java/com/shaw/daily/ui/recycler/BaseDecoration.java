package com.shaw.daily.ui.recycler;

import androidx.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by shaw on 2017/9/28.
 */

public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(@ColorInt int color,int size){
        setDividerLookup(new DividerLookImpl(color,size) );
    }

    public static BaseDecoration create(@ColorInt int color,int size){
        return new BaseDecoration(color,size);
    }
}

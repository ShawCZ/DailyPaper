package com.shaw.daily.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;

/**
 * Created by shaw
 */

public class LatteWebInterface {

    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate) {
        return new LatteWebInterface(delegate);
    }

    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }

}

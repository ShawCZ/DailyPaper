package com.shaw.daily.dp.main.detial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.delegates.web.WebDelegateImpl;
import com.shaw.daily.dp.R;
import com.shaw.daily.net.RestClient;
import com.shaw.daily.net.callback.ISuccess;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Logger;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by shaw on 2017/9/30.
 */

public class DetialDelegate extends DailyDelegate {

    private static final String ARG_STORY_ID = "ARG_STORY_ID";
    private int mStoryId = -1;
    private String htmlString;
    private String titleImage;
    private Document document;

    public static DetialDelegate create(int storyId) {
        final Bundle args = new Bundle();
        args.putInt("ARG_STORY_ID", storyId);
        final DetialDelegate delegate = new DetialDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mStoryId = args.getInt("ARG_STORY_ID");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_detial;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("http://news-at.zhihu.com/api/4/news/" + mStoryId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject data = JSON.parseObject(response);
                        htmlString = data.getString("body");
                        titleImage = data.getString("images").trim();
                        String url = titleImage.substring(1, titleImage.length() - 1);

                        document = Jsoup.parseBodyFragment(htmlString);

                        //加入图片标签
                        Element img = document.select("div.img-place-holder").first();
                        img.html("<img class=\"night img\" src=" + url + "  width=\"100%\" height=\"100%\" display=\"block\" max-width=\" 100%\"/>");

                        htmlString = document.toString();
                        htmlString = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + htmlString;

                        final WebDelegateImpl delegate = WebDelegateImpl.create(htmlString);
                        getSupportDelegate().loadRootFragment(R.id.web_discovery_container, delegate);

                    }
                })
                .build()
                .get();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}

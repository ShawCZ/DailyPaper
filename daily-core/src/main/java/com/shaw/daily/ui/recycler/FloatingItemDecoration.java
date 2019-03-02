package com.shaw.daily.ui.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shaw
 */

public class FloatingItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "FloatingItemDecoration";

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int dividerHeight;
    private int dividerWidth;
    private Map<Integer, String> keys = new HashMap<>();
    private int mTitleHeight;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private float mTextHeight;
    private float mTextBaselineOffset;
    private Context mContext;

    //滚动列表的时候是否一直悬浮头部
    private boolean showFloatingHeaderOnScrolling = true;

    private final RgbValue RGB_VALUE = RgbValue.create(0, 153, 204);

    public FloatingItemDecoration(Context context) {
        final TypedArray array = context.obtainStyledAttributes(ATTRS);
        mDivider = array.getDrawable(0);
        array.recycle();
        this.dividerHeight = mDivider.getIntrinsicHeight();
        this.dividerWidth = mDivider.getIntrinsicWidth();
        init(context);
    }

    //自定义分割线
    public FloatingItemDecoration(Context context, @DrawableRes int drawableId) {
        mDivider = ContextCompat.getDrawable(context, drawableId);
        this.dividerHeight = mDivider.getIntrinsicHeight();
        this.dividerWidth = mDivider.getIntrinsicWidth();
        init(context);
    }

    //自定义分割线
    public FloatingItemDecoration(Context context, @Dimension float dividerWidth, @Dimension float dividerHeigth) {
        mDivider = new ColorDrawable(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        this.dividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerHeigth, context.getResources().getDisplayMetrics());
        this.dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, context.getResources().getDisplayMetrics());
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, mContext.getResources().getDisplayMetrics()));
        mTextPaint.setColor(Color.WHITE);
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.bottom - fm.top;
        mTextBaselineOffset = fm.bottom;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.d(TAG,"--onDraw ");

        drawVertical(c, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = 0;
        int bottom = 0;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (!keys.containsKey(params.getViewLayoutPosition())) {
                //画普通分割线
                mDivider.setBounds(0, 0, 0, 0);
                mDivider.draw(canvas);
            } else {
                //第一条数据 画头部
                top = child.getTop() - params.topMargin - mTitleHeight;
                bottom = top + mTitleHeight;
                canvas.drawRect(left, top, right, bottom, mBackgroundPaint);
                float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, mContext.getResources().getDisplayMetrics());
                //计算文字的baseLine
                float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;
                canvas.drawText(keys.get(params.getViewLayoutPosition()), x, y, mTextPaint);
            }
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.d(TAG,"--onDrawOver ");

        if (!showFloatingHeaderOnScrolling) {
            return;
        }
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        int firstVisiblePos = manager.findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {
            return;
        }
        String title = getTitle(firstVisiblePos);
        if (TextUtils.isEmpty(title)) {
            return;
        }

        boolean flag = false;
        if (getTitle(firstVisiblePos + 1) != null && !title.equals(getTitle(firstVisiblePos + 1))) {
            //当前组最后一个元素，但不一定碰撞了
            View child = parent.findViewHolderForAdapterPosition(firstVisiblePos).itemView;
            if (child.getTop() + child.getMeasuredHeight() < mTitleHeight) {
                //进一步检查碰撞
                //保持画布当前状态
                c.save();
                flag = true;
                //负的代表向上
                c.translate(0, child.getTop() + child.getMeasuredHeight() - mTitleHeight);
            }
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + mTitleHeight;
        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, mContext.getResources().getDisplayMetrics());
        //计算文字的baseLine
        float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;
        c.drawText(title, x, y, mTextPaint);

        if (flag) {
            //还原画布为初始状态
            c.restore();
        }
    }

    //循环查找标题，找到说明位置属于该分组
    private String getTitle(int position) {
        while (position >= 0) {
            if (keys.containsKey(position)) {
                return keys.get(position);
            }
            position--;
        }
        return null;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG,"--getItemOffsets ");
        int pos = parent.getChildViewHolder(view).getAdapterPosition();
        if (keys.containsKey(pos)) {
            //留出头部偏移
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            outRect.set(0, dividerHeight, 0, 0);
        }
    }

    public void setShowFloatingHeaderOnScrolling(boolean showFloatingHeaderOnScrolling) {
        this.showFloatingHeaderOnScrolling = showFloatingHeaderOnScrolling;
    }

    public void setKeys(Map<Integer, String> keys) {
        this.keys.clear();
        this.keys.putAll(keys);
    }

    public void setTitleHeight(int titleHeight) {
        this.mTitleHeight = titleHeight;
    }
}

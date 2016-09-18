package com.example.hiphonezhu.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

/**
 * 粘性布局
 * @author hiphonezhu@gmail.com
 * @version [NestedScrolling, 16/9/13 13:41]
 */
public class StickyLayout extends LinearLayout implements NestedScrollingParent {
    OverScroller mScroller;
    int maxScrollY; // 最大滚动距离
    public StickyLayout(Context context) {
        super(context);
        init();
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    View vAlways;
    int statusBarHeight;
    public void setAlways(View vAlways, int statusBarHeight)
    {
        this.vAlways = vAlways;
        this.statusBarHeight = statusBarHeight;

        maxScrollY = mTopViewHeight - statusBarHeight;
    }

    void init()
    {
        mScroller = new OverScroller(getContext());
    }

    ImageView iv;
    TextView tvSticky;
    RecyclerView recyclerView;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv = (ImageView)findViewById(R.id.iv);
        tvSticky = (TextView)findViewById(R.id.tv_sticky);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
    }

    int mTopViewHeight;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = iv.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 动态设置RecyclerView的高度
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        // StickyNavLayout高度 - Sticky View高度 - ImageView可见高度
        params.height = getMeasuredHeight() - tvSticky.getMeasuredHeight() - (mTopViewHeight - iv.getScrollY());
        recyclerView.setLayoutParams(params);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
    {
        return true;
    }
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        // dy > 0表示子View向上滑动;

        // 子View向上滑动且父View的偏移量<ImageView高度
        boolean hiddenTop = dy > 0 && getScrollY() < maxScrollY;

        // 子View向下滑动(说明此时父View已经往上偏移了)且父View还在屏幕外面, 另外内部View不能在垂直方向往下移动了
        /**
         * ViewCompat.canScrollVertically(view, int)
         * 负数: 顶部是否可以往下滚动(官方描述: 能否往上滚动, 不太准确吧~)
         * 正数: 底部是否可以往上滚动
         */
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
        if (velocityY > 0 && getScrollY() < maxScrollY) // 向上滑动, 且当前View还没滑到最顶部
        {
            fling((int) velocityY, maxScrollY);
            return true;
        }
        else if (velocityY < 0 && getScrollY() > 0) // 向下滑动, 且当前View部分在屏幕外
        {
            fling((int) velocityY, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public void onStopNestedScroll(View child) {
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    public void fling(int velocityY, int maxY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, maxY);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0) // 不允许向下滑动
        {
            y = 0;
        }
        if (y > maxScrollY) // 防止向上滑动距离大于最大滑动距离
        {
            y = maxScrollY;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
        else
        {
            int[] location = new int[2];
            tvSticky.getLocationOnScreen(location);
            // Y轴位置 <= 状态栏高度, "假"的自己可见
            if (location[1] <= statusBarHeight)
            {
                vAlways.setVisibility(View.VISIBLE);
            }
            else
            {
                vAlways.setVisibility(View.INVISIBLE);
            }
        }
    }
}

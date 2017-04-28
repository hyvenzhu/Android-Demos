package android.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * @author hiphonezhu@gmail.com
 * @version [RefreshLayout, 17/4/10 22:19]
 */

public class RefreshLayout extends LinearLayout implements NestedScrollingParent {
    OverScroller mScroller;

    HeaderView headerView;
    View scrollView;
    FooterView footerView;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = (HeaderView) getChildAt(0);
        scrollView = getChildAt(1);
        footerView = (FooterView) getChildAt(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(scrollView, widthMeasureSpec, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        measureChild(footerView, widthMeasureSpec, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.AT_MOST));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollBy(0, headerView.getHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        // 垂直方向
        return child == scrollView && nestedScrollAxes == SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy < 0) { // 向下滚动
            if (!ViewCompat.canScrollVertically(target, -1)) { // target(NestedScrollView)不可以继续往下滚动，则滚动RefreshLayout
                scrollBy(0, dy);
                consumed[1] = dy;
            } else {
                if (getScrollY() > headerView.getHeight()) { // footerView 部分可见
                    scrollBy(0, dy);
                    consumed[1] = dy;
                }
            }
        } else { // 向上滚动
            if (getScrollY() < headerView.getHeight()) { // 向上滚动
                scrollBy(0, dy);
                consumed[1] = dy;
            } else if (!ViewCompat.canScrollVertically(target, 1)) { // target(NestedScrollView)不可以继续往上滚动，则滚动RefreshLayout
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        if (y < 0) { // 向下滚动最大值：headerView完全可见
            y = 0;
        } else if (y > headerView.getHeight() + footerView.getHeight()) { // 向上滚动最大值：footerView完全显示
            y = headerView.getHeight() + footerView.getHeight();
        }

        int scrollY = getScrollY();
        if (scrollY > headerView.getHeight() && scrollY < headerView.getHeight() + footerView.getRefreshViewHeight()) {
            footerView.updateState(State.PULL_TO_REFRESH);
        } else if (scrollY > headerView.getHeight() + footerView.getRefreshViewHeight()) {
            footerView.updateState(State.RELEASE_TO_REFRESH);
        } else if (scrollY > headerView.getHeight() - headerView.getRefreshViewHeight()) {
            headerView.updateState(State.PULL_TO_REFRESH);
        } else if (scrollY != 0 && scrollY < headerView.getHeight() - headerView.getRefreshViewHeight()) {
            headerView.updateState(State.RELEASE_TO_REFRESH);
        }

        super.scrollTo(x, y);
    }

    @Override
    public void onStopNestedScroll(View child) {
        if (headerView.getCurrentState() == State.RELEASE_TO_REFRESH) {
            scrollTo(0, headerView.getHeight() - headerView.getRefreshViewHeight());
            headerView.updateState(State.REFRESHING);
        } else if (footerView.getCurrentState() == State.RELEASE_TO_REFRESH) {
            scrollTo(0, headerView.getHeight() + footerView.getRefreshViewHeight());
            footerView.updateState(State.REFRESHING);
        } else {
            if (headerView.getCurrentState() == State.REFRESHING
                    && getScrollY() < headerView.getHeight() - headerView.getRefreshViewHeight()) { // headerView正在刷新且完全可见
                scrollTo(0, headerView.getHeight() - headerView.getRefreshViewHeight());
            } else if (footerView.getCurrentState() == State.REFRESHING
                    && getScrollY() > headerView.getHeight() + footerView.getRefreshViewHeight()) { // footerView正在刷新且完全可见
                scrollTo(0, headerView.getHeight() + footerView.getRefreshViewHeight());
            }
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public enum State {
        IDLE,
        PULL_TO_REFRESH,
        RELEASE_TO_REFRESH,
        REFRESHING,
    }
}

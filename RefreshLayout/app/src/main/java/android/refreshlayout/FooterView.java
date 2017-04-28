package android.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author hiphonezhu@gmail.com
 * @version [RefreshLayout, 17/4/20 22:53]
 */

public class FooterView extends LinearLayout {
    RefreshLayout.State mState = RefreshLayout.State.IDLE;
    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setGravity(Gravity.TOP);
    }

    TextView tvState;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvState = (TextView)getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View v = getChildAt(0);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 3 * v.getHeight());
    }

    public int getRefreshViewHeight() {
        return tvState.getHeight();
    }

    public void updateState(RefreshLayout.State state) {
        if (mState == RefreshLayout.State.REFRESHING) {
            return;
        }
        this.mState = state;
        if (state == RefreshLayout.State.PULL_TO_REFRESH) {
            tvState.setText("上拉加载");
        } else if (state == RefreshLayout.State.RELEASE_TO_REFRESH) {
            tvState.setText("松开刷新");
        } else if (state == RefreshLayout.State.REFRESHING) {
            tvState.setText("刷新中");
        }
    }

    public RefreshLayout.State getCurrentState() {
        return mState;
    }
}

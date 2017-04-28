package android.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.refreshlayout.RefreshLayout.State;

/**
 * @author hiphonezhu@gmail.com
 * @version [RefreshLayout, 17/4/12 20:57]
 */

public class HeaderView extends LinearLayout {
    State mState = State.IDLE;
    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setGravity(Gravity.BOTTOM);
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

    public void updateState(State state) {
        if (mState == State.REFRESHING) {
            return;
        }
        this.mState = state;
        if (state == State.PULL_TO_REFRESH) {
            tvState.setText("下拉刷新");
        } else if (state == State.RELEASE_TO_REFRESH) {
            tvState.setText("松开刷新");
        } else if (state == State.REFRESHING) {
            tvState.setText("刷新中");
        }
    }

    public State getCurrentState() {
        return mState;
    }
}

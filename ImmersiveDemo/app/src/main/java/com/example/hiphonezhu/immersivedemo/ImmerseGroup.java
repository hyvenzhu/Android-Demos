package com.example.hiphonezhu.immersivedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 重写 onMeasure 使得 paddingBottom = 0，解决 adjustResize 模式下的拉伸问题（paddingBottom 一个键盘的高度）。
 * 这样的做法就不会把 fitSystemWindows 局限在根布局的使用；相反，一般多作用在状态栏下面的第一个视图，例如：标题栏、或者图片。
 * 更多详见：https://stackoverflow.com/questions/28043202/android-appcompat-toolbar-stretches-when-searchview-gets-focus
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 17/6/12 11:12]
 */
public class ImmerseGroup extends FrameLayout {
    public ImmerseGroup(Context context) {
        super(context);
    }

    public ImmerseGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImmerseGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.wzhy.simplecustomviews.viewzlifecycle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017-11-24 0024.
 Creation
 |->Constructor
 |->onFinishInflate()

 Layout
 |->onMeasure()
 |->onLayout(int, int, int, int)
 |->onSizeChanged(int, int, int, int)

 Drawing
 |->onDraw(android.graphics.Canvas)

 Event processing
 |->onKeyDown(int, KeyEvent)
 |->onKeyUp(int, KeyEvent)
 |->onTrackballEvent(MotionEvent)
 |->onTouchEvent(MotionEvent)

 Focus
 |->onFocusChanged(boolean, int, android.graplics.Rect)
 |->onWindowFocusChanged(boolean)

 Attaching
 |->onAttachedToWindow()
 |->onDetachedFromWindow()
 |->onWindowVisibilityChanged(int)


 View的生命周期：

 >> xml中引入控件
 constructor with 2 parameter
 onFinishInflate
 onAttachedToWindow
 onWindowVisibilityChanged
 onMeasure
 onSizeChanged
 onLayout
 onMeasure
 onLayout
 onDraw

 >> 通过new的方式添加进布局
 constructor with 1 parameter
 onAttachedToWindow
 onWindowVisibilityChanged
 onMeasure
 onSizeChanged
 onLayout
 onMeasure
 onLayout
 onDraw

 * onMeasure的调用取决于控件的父容器以及View Tree的结构，不同的父容器有不同的测量逻辑。
 */

public class LifeCycleView extends View {

    private static final String TAG = LifeCycleView.class.getSimpleName();

    public LifeCycleView(Context context) {
        super(context);
        Log.e(TAG, "constructor with 1 parameter");
    }

    public LifeCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "constructor with 2 parameter");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.d(TAG, "onWindowVisibilityChanged");
    }

}

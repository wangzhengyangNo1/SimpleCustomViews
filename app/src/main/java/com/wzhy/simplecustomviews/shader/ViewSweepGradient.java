package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

/**
 * Created by Administrator on 2017-9-29 0029.
 */

public class ViewSweepGradient extends View {

    public static final int RECT_SIZE = 200;//矩形尺寸的一半
    private Paint mPaint; //画笔

    private int left, top, right, bottom; //矩形左上右下坐标
    private int centerX;
    private int centerY;
    private Paint mTextPaint;

    public ViewSweepGradient(Context context) {
        this(context, null);
    }

    public ViewSweepGradient(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewSweepGradient(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        //获取屏幕尺寸
        Size screenSize = DisplayUtil.getScreenSize();
        //获取屏幕中点坐标
        centerX = screenSize.getWidth() / 2;
        centerY = screenSize.getHeight() / 2;

        //计算矩形左上右下坐标值
        left = centerX - RECT_SIZE;
        top = centerY - RECT_SIZE;
        right = centerX + RECT_SIZE;
        bottom = centerY + RECT_SIZE;

        //设置着色器
        //mPaint.setShader(new SweepGradient(centerX, centerY, Color.BLUE, Color.YELLOW));
        mPaint.setShader(new SweepGradient(centerX, centerY, new int[]{Color.GREEN, Color.WHITE, Color.GREEN}, new float[]{0f, 0.5f, 1f}));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}

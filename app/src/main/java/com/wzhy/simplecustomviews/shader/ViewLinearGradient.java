package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

import static com.wzhy.simplecustomviews.shader.ViewBitmapShader.RECT_SIZE;

/**
 * Created by Administrator on 2017-9-29 0029.
 */

public class ViewLinearGradient extends View {

    public static final int RECT_SIZE = 200;//矩形尺寸的一半
    private Paint mPaint; //画笔

    private int left, top, right, bottom; //矩形左上右下坐标
    private int centerX;
    private int centerY;
    private Paint mTextPaint;

    public ViewLinearGradient(Context context) {
        this(context, null);
    }

    public ViewLinearGradient(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLinearGradient(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setShader(new LinearGradient(left, top, right, bottom, new int[]{Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.BLUE}, new float[]{0f, 0.1f, 0.2f, 0.7f, 0.8f}, Shader.TileMode.REPEAT));

        mTextPaint.setShader(new LinearGradient(0, 10, 10, 0, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, mPaint);

        mTextPaint.setTextSize(30);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        canvas.drawText("我是测试君！", centerX, centerY - (fontMetrics.ascent + fontMetrics.descent) / 2f, mTextPaint);
    }
}

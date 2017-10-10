package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.MyApp;
import com.wzhy.simplecustomviews.R;
import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

/**
 * Created by Administrator on 2017-9-29 0029.
 */

public class ViewBitmapShader extends View {

    public static final int RECT_SIZE = 200;//矩形尺寸的一半
    private Paint mPaint; //画笔

    private int left, top, right, bottom; //矩形左上右下坐标
    private int screenW;
    private int screenH;


    public ViewBitmapShader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewBitmapShader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        //获取屏幕尺寸
        Size screenSize = DisplayUtil.getScreenSize();
        screenW = screenSize.getWidth();
        screenH = screenSize.getHeight();
        //获取屏幕中点坐标
        int centerX = screenW / 2;
        int centerY = screenH / 2;

        //计算矩形左上右下坐标值
        left = centerX - RECT_SIZE;
        top = centerY - RECT_SIZE;
        right = centerX + RECT_SIZE;
        bottom = centerY + RECT_SIZE;

        //获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(MyApp.getAppContext().getResources(), R.drawable.pic_1);

        //实例化一个shader
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //实例化一个矩阵对象
        Matrix matrix = new Matrix();

        //设置矩阵变换
        matrix.setTranslate(left, top);

        //设置Shader的变换矩阵
        bitmapShader.setLocalMatrix(matrix);

        //设置着色器
        mPaint.setShader(bitmapShader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(left, top, right, bottom, mPaint);
        //canvas.drawRect(0, 0, screenW, screenH, mPaint);


    }
}

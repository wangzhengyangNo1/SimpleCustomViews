package com.wzhy.simplecustomviews.measure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * measure
 */

public class ImgView extends View {

    private Bitmap mBitmap;

    public ImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制位图
        canvas.drawBitmap(mBitmap, getPaddingLeft(), getPaddingTop(), null);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ImageView

        //声明一个临时变量来存储计算出的测量值
        int resultWidth = 0;

        //获取宽度测量规格中的mode
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        //获取宽度测量中的size
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);


        /*如果父容器心里有数（父容器能确定子控件的宽度）*/
        if (modeWidth == MeasureSpec.EXACTLY) {
            //子控件拿父容器给的宽度
            resultWidth = sizeWidth;

        } else { /*如果父容器心里没数（父容器不能确定子控件的宽度）*/
            //子控件要看自己需要多大
            resultWidth = mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();

            Log.i(TAG, "onMeasure: sizeWidth = " + sizeWidth);
            Log.i(TAG, "onMeasure: bmpWidth = " + mBitmap.getWidth());

            /*如果父容器给子控件一个限制值*/
            if (modeWidth == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, sizeWidth);
            }
        }


        int resultHeight = 0;
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = sizeHeight;
        } else {
            resultHeight = mBitmap.getHeight() + getPaddingTop() + getPaddingBottom();

            if (modeHeight == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, sizeHeight);
            }
        }
//        resultWidth += getPaddingLeft() + getPaddingRight();
//        resultHeight += getPaddingTop() + getPaddingBottom();
        //设置测量尺寸
        setMeasuredDimension(resultWidth, resultHeight);

    }
}

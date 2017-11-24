package com.wzhy.simplecustomviews.measure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.R;
import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

/**
 * Created by Administrator on 2017-11-17 0017.
 */

public class IconView extends View {

    private Bitmap mBitmap;//位图
    private TextPaint mTextPaint;//绘制文本的画笔
    private String mText;//绘制的文本

    private float mTextSize;//文字大小

    /**
     * 宽高枚举类
     */
    private enum Ratio {
        WIDTH, HEIGHT;
    }


    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //计算参数
        calArgs(context);

        //初始化
        init();
    }

    /**
     * 参数计算
     *
     * @param context 上下文
     */
    private void calArgs(Context context) {
        Size screenSize = DisplayUtil.getScreenSize();
        int screenWidth = screenSize.getWidth();

        //计算文本尺寸
        mTextSize = screenWidth / 10f;
    }

    /**
     * 初始化
     */
    private void init() {
        /*获取Bitmap*/
        if (null == mBitmap) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_1);
        }
        /*为文字赋值*/
        if (null == mText || mText.trim().length() == 0) {
            mText = "label";
        }

        /*初始化画笔并设置参数*/
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.LTGRAY);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, Ratio.WIDTH), getMeasureSize(widthMeasureSpec, Ratio.HEIGHT));
    }

//    /**
//     * 获取测量后的尺寸
//     *
//     * @param measureSpec 测量规格
//     * @param ratio       宽高标识
//     * @return 宽或高的测量值
//     */
//    private int getMeasureSize(int measureSpec, Ratio ratio) {
//
//        //声明临时变量保存测量值
//        int result = 0;
//
//        /*获取mode和size*/
//        int mode = MeasureSpec.getMode(measureSpec);
//        int size = MeasureSpec.getSize(measureSpec);
//
//        /*判断mode具体值*/
//        switch (mode) {
//            case MeasureSpec.EXACTLY://EXACTLY时，直接赋值
//                result = size;
//                break;
//            default://默认情况下，UNSPECIFIED和AT_MOST一并处理
//                if (ratio == Ratio.WIDTH) {
//                    float textWidth = mTextPaint.measureText(mText);
//                    result = (int) (textWidth > mBitmap.getWidth() ? textWidth : mBitmap.getWidth())
//                            + getPaddingLeft() + getPaddingRight();
//                } else if (ratio == Ratio.HEIGHT) {
//                    result = ((int) (mTextPaint.descent() - mTextPaint.ascent())) * 2 + mBitmap.getHeight()
//                            + getPaddingTop() + getPaddingBottom();
//                }
//                /*AT_MOST时，判断size和result的大小，取较小值*/
//                if (mode == MeasureSpec.AT_MOST) {
//                    result = Math.min(result, size);
//                }
//                break;
//        }
//
//        return result;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(0x80987865);
//        /*绘制
//         * 参数就不做单独处理了因为只会Draw一次不会频繁调用
//         * */
//        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2- mBitmap.getHeight() / 2, null);
//        canvas.drawText(mText, getWidth() / 2 , getHeight() /2 + mBitmap.getHeight()/2 - mTextPaint.ascent(), mTextPaint);
//    }

    /**
     * 获取测量后的尺寸
     *
     * @param measureSpec 测量规格
     * @param ratio       宽高标识
     * @return 宽或高的测量值
     */
    private int getMeasureSize(int measureSpec, Ratio ratio) {

        //声明临时变量保存测量值
        int result = 0;

        /*获取mode和size*/
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        /*判断mode具体值*/
        switch (mode) {
            case MeasureSpec.EXACTLY://EXACTLY时，直接赋值
                result = size;
                break;
            default://默认情况下，UNSPECIFIED和AT_MOST一并处理
                if (ratio == Ratio.WIDTH) {
                    float textWidth = mTextPaint.measureText(mText);
                    result = (int) (textWidth > mBitmap.getWidth() ? textWidth : mBitmap.getWidth())
                            + 20 + getPaddingLeft() + getPaddingRight();
                } else if (ratio == Ratio.HEIGHT) {
                    result = ((int) (mTextPaint.descent() - mTextPaint.ascent())) +  20 + mBitmap.getHeight()
                            + getPaddingTop() + getPaddingBottom();
                }
                /*AT_MOST时，判断size和result的大小，取较小值*/
                if (mode == MeasureSpec.AT_MOST) {
                    result = Math.min(result, size);
                }
                break;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x80987865);
        /*绘制
         * 参数就不做单独处理了因为只会Draw一次不会频繁调用
         * */
        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, 10 + getPaddingTop() /*getHeight() / 2 - mBitmap.getHeight() / 2*/, null);
        canvas.drawText(mText, getWidth() / 2, 20 + getPaddingTop() + mBitmap.getHeight() - mTextPaint.ascent(), mTextPaint);
    }
}

package com.wzhy.simplecustomviews.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 层和画布的保存和释放。
 * 来自爱哥的博客：http://blog.csdn.net/aigestudio/article/details/42677973
 */

public class LayerView extends View {

    private Paint mPaint;
    private int mViewWidth;
    private int mViewHeight;

    public LayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //实例化画笔对象并设置其标识值
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*获得控件宽高*/
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.rotate(30);

        //saveTest1(canvas);

        //saveTest2(canvas);

        saveTest3(canvas);

    }

    private void saveTest3(Canvas canvas) {
        System.out.println("LayerView: s--->count = " + canvas.getSaveCount());

        //保存并裁剪画布，填充黄色
        int saveId1 = canvas.save(Canvas.CLIP_SAVE_FLAG);
        System.out.println("LayerView: saveId1 = " + saveId1);
        System.out.println("LayerView: 1--->count = " + canvas.getSaveCount());
        canvas.clipRect(mViewWidth / 2f - 300, mViewHeight /2f - 300,
                mViewWidth / 2f + 300, mViewHeight /2f + 300);
        canvas.drawColor(Color.YELLOW);
        //canvas.restoreToCount(saveId1);

        //保存并裁剪画布，并填充绿色
        int saveId2 = canvas.save(Canvas.CLIP_SAVE_FLAG);
        System.out.println("LayerView: saveId2 = " + saveId2);
        System.out.println("LayerView: 2--->count = " + canvas.getSaveCount());
        canvas.clipRect(mViewWidth / 2f - 200, mViewHeight /2f - 200,
                mViewWidth / 2f + 200, mViewHeight /2f + 200);
        canvas.drawColor(Color.GREEN);
        //canvas.restoreToCount(saveId2);

        //保存画布并旋转，在绘制一个蓝色矩形
        int saveId3 = canvas.save(Canvas.MATRIX_SAVE_FLAG);
        System.out.println("LayerView: saveId3 = " + saveId3);
        System.out.println("LayerView: 3--->count = " + canvas.getSaveCount());
        canvas.rotate(5);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mViewWidth / 2f - 100, mViewHeight / 2f - 100,
                mViewWidth / 2f + 100, mViewHeight / 2f + 100, mPaint);

        //canvas.restoreToCount(saveId3);

        System.out.println("LayerView: e--->count = " + canvas.getSaveCount());

        mPaint.setColor(Color.CYAN);
        canvas.drawRect(mViewWidth / 2f , mViewWidth / 2f,
                mViewWidth / 2f + 400, mViewHeight / 2f + 400, mPaint);
    }


    private void saveTest2(Canvas canvas) {

        /*绘制一个红色矩形*/
        mPaint.setColor(Color.RED);
        canvas.drawRect(mViewWidth / 2f - 240, mViewHeight / 2f - 240,
                mViewWidth / 2f + 240, mViewHeight / 2f + 240, mPaint);

        int saveId1 = canvas.save();
        canvas.clipRect(mViewWidth / 2f - 200, mViewHeight / 2f - 200,
                mViewWidth / 2 + 200, mViewHeight / 2 + 200);
        canvas.drawColor(Color.GREEN);



        int saveId2 = canvas.save();
        canvas.rotate(5);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mViewWidth / 2f - 100, mViewHeight / 2f - 100,
                mViewWidth / 2 + 100, mViewHeight / 2 + 100, mPaint);


        canvas.restoreToCount(saveId2);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(mViewWidth / 2f - 300, mViewHeight / 2f - 300,
                mViewWidth / 2f + 300, mViewHeight / 2f + 300, mPaint);
    }

    private void saveTest1(Canvas canvas) {
        /*绘制一个红色矩形*/
        mPaint.setColor(Color.RED);
        canvas.drawRect(mViewWidth / 2f - 240, mViewHeight / 2f - 240,
                mViewWidth / 2f + 240, mViewHeight / 2f + 240, mPaint);

    /*保存画布并绘制一个蓝色的矩形*/
        //canvas.save();
//        canvas.saveLayer(mViewWidth / 2f - 100, mViewHeight / 2f - 100,
//                mViewWidth / 2 + 100, mViewHeight / 2 + 100, null, Canvas.ALL_SAVE_FLAG);
        canvas.saveLayerAlpha(mViewWidth / 2f - 100, mViewHeight / 2f - 100,
                mViewWidth / 2 + 100, mViewHeight / 2 + 100, 0x80, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.BLUE);
        canvas.rotate(5);
        canvas.drawRect(mViewWidth / 2f - 100, mViewHeight / 2f - 100,
                mViewWidth / 2 + 100, mViewHeight / 2 + 100, mPaint);
        canvas.restore();
    }
}

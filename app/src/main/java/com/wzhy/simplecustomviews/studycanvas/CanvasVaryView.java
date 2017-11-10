package com.wzhy.simplecustomviews.studycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.R;

/**
 * canvas的变换：scale, rotate, skew, translate
 */

public class CanvasVaryView extends View {

    private Bitmap mBitmap;
    private int mViewWidth;
    private int mViewHeight;

    public CanvasVaryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //从资源中获取位图对象
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_girl);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*获取控件宽高*/
        mViewWidth = w;
        mViewHeight = h;

        //缩放位图与控件一致
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mViewWidth, mViewHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x3000ff44);
        //scaleTest(canvas);

        //rotateTest(canvas);

        //skewTest(canvas);

        //translateTest(canvas);

        matrixTest(canvas);

    }

    private void matrixTest(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);

        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        matrix.postTranslate(100, 100);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    private void translateTest(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(mViewWidth / 4f, mViewHeight / 4f);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    private void skewTest(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.skew(0.5f, 0);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    private void rotateTest(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(30, mViewWidth / 2f, mViewHeight / 2f);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();

    }

    private void scaleTest(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(0.8f, 0.35f, mViewWidth / 2f, mViewHeight / 2f);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }
}

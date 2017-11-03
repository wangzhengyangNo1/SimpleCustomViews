package com.wzhy.simplecustomviews.studypath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Path简单使用
 * path.close()：闭合
 * path.quadTo()：二阶贝塞尔曲线
 */

public class PathView extends View {

    private Paint mPaint;
    private Path mPath;
    private TextPaint mTextPaint;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*实例化画笔并设置属性*/
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(3);

        //实例化路径
        mPath = new Path();

        //lineTo和闭合
        //lineToAndClosePath();

        //贝塞尔曲线
        //bzlCurve();

        //arcTo
//        arcToTest(0, 120);
//        arcToTest(120, 120);
//        arcToTest(240, 120);

        //addXxx()
//        addArcTest();


        //drawTextOnPath和闭合和方向
//        drawTextOnPathTest();

    }

    private void bzlCurve() {

        mPath.moveTo(100, 100);

        //链接路径到点
        mPath.quadTo(200, 200, 300, 100);
    }

    private void lineToAndClosePath() {
        mPath.moveTo(100, 100);
        //链接路径到点[100, 100]
        mPath.lineTo(300, 100);
        mPath.lineTo(400, 200);
        mPath.lineTo(200, 200);
        //mPath.lineTo(100, 100);
        mPath.close();
    }

    private void arcToTest(float startAngle, float swipeAngle) {
        mPath.moveTo(150, 150);
        //链接路径到点
        RectF oval = new RectF(100, 100, 200, 200);
        mPath.arcTo(oval, startAngle, swipeAngle, true);
        //mPath.close();
    }

    private void addArcTest() {
        mPath.moveTo(100, 100);
        mPath.lineTo(200, 200);

        //添加一条弧线到path中
        RectF oval = new RectF(100, 100, 300, 400);
        mPath.addArc(oval, 0, 90);
    }

    private void drawTextOnPathTest() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.DKGRAY);
        //设置文字大小
        mTextPaint.setTextSize(20);
        //添加一条弧线到Path中
        RectF rectF = new RectF(100, 100, 300, 400);
        mPath.addOval(rectF, Path.Direction.CCW);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //绘制路径
        canvas.drawPath(mPath, mPaint);

        //绘制路径上的文字
        canvas.drawTextOnPath("北京智慧星光信息技术有限公司", mPath, 5, -5, mTextPaint);
    }
}

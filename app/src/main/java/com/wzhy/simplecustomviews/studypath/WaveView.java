package com.wzhy.simplecustomviews.studypath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-11-1 0001.
 */

public class WaveView extends View {

    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;
    private int vWidth;
    private int vHeight;
    private float ctrY;
    private float waveY;
    private float ctrX;
    private boolean isInc;

    private float dx = 10f;
    private float dy = 1f;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        //实例化画笔并设置参数
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        mPaint.setColor(0x601f7df9);

        //实例化路径
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //获取控件宽高
        vWidth = w;
        vHeight = h;

        //计算端点Y坐标
        waveY = -1 / 16f * vHeight;

        //计算控制点Y坐标
        ctrY = 1 / 16f * vHeight;

        ctrX = -1 / 3f * vWidth;

        dx = 1 / 32f * vWidth;
        dy = 1 / 1024f * vHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x3000ff60);
        /*
        设置Path起点
        注意，将path起点设置在了控件外部看不到的区域
        如果将起点设置在控件左端x=0的位置，会使得贝塞尔曲线显得生硬
        所以需要让起点向外移动一点
         */
        mPath.moveTo(-1 / 3f * vWidth, waveY);

        /*
        以二阶曲线的方式通过控制点连接位于控件右边的终点
        终点位置也是在控件外部
        我们之需要不断让ctrX的大小变化即可实现水浪的效果
         */
        mPath.quadTo(ctrX, ctrY, vWidth + 1 / 3f * vWidth, waveY);

        //围绕控件闭合曲线
        mPath.lineTo(vWidth + 1 / 3f * vWidth, vHeight);
        mPath.lineTo(-1 / 3f * vWidth, vHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

        /*
        当控制点的x坐标大于或等于终点坐标时，更改标识值
         */
        if (ctrX >= vWidth + 1 / 3f * vWidth) {
            isInc = false;
        }
        /*
        当控制点x坐标小于或等于起点x坐标时更改标识值
         */
        if (ctrX <= -1 / 3f * vWidth) {
            isInc = true;
        }

        //根据标识值判断当前的控制点x坐标是该加还是该减
        ctrX = isInc ? ctrX + dx : ctrX - dx;



        //让水不断减少
        if (ctrY <= vHeight + 1 / 32f * vHeight) {
            ctrY += dy;
            waveY += dy;
        }

        mPath.reset();

        //重绘
        invalidate();
    }
}

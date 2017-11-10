package com.wzhy.simplecustomviews.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Region.Op：canvas做裁剪（clip：clipRect、clipPath、clipRegion)时
 */

public class RegionTestView extends View {

    //绘制边框的Paint对象
    private Paint mPaint;
    private Region mRegionA;
    private Region mRegionB;

    public RegionTestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //实例化画笔并设置属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2);

        //实例化区域A和区域B
        mRegionA = new Region(100, 100, 300, 300);
        mRegionB = new Region(200, 200, 400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.scale(0.75f, 0.75f);

        //填充颜色
        canvas.drawColor(Color.BLUE);

        canvas.save();

        //裁剪区域A
        canvas.clipRect(mRegionA.getBounds());
        //再通过组合方式裁剪区域B
        //canvas.clipRect(mRegionB.getBounds(), Region.Op.DIFFERENCE);//A与B不同的区域（A的)，A&!B
        //canvas.clipRect(mRegionB.getBounds(), Region.Op.REVERSE_DIFFERENCE);//B与A不同的区域（B的)，B&!A
        //canvas.clipRect(mRegionB.getBounds(), Region.Op.REPLACE);//A被B替换（A替换为B），A = B

        //canvas.clipRect(mRegionB.getBounds(), Region.Op.INTERSECT);//A和B相交区域， A&B
        //canvas.clipRect(mRegionB.getBounds(), Region.Op.UNION);//A和B合并区域， A|B

        canvas.clipRect(mRegionB.getBounds(), Region.Op.XOR);//A异或B，(A|B)&!(A&B)

        canvas.drawColor(Color.RED);

        canvas.restore();

        //绘制框框辅助观察
        canvas.drawRect(mRegionA.getBounds(), mPaint);
        canvas.drawRect(mRegionB.getBounds(), mPaint);
    }
}

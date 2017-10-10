package com.wzhy.simplecustomviews.patheffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 路径效果
 * Created by Administrator on 2017-9-28 0028.
 */

public class PathEffectView extends View {

    private Path mPath;//路径对象
    private Paint mPaint;//画笔对象
    private int mScreenWidth;
    private int mScreenHeight;
    private PathEffect[] mPathEffects;//路径效果数组

    private float mPhase;//偏移值

    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init();

    }

    private void init() {
        //实例化画笔并设置属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff80f080);
        mPaint.setStrokeWidth(5);

        //实例化路径
        mPath = new Path();
        //定义路径的起点
        mPath.moveTo(0, 0);

        //定义路径各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }

        mPathEffects = new PathEffect[7];

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**实例化各类特效*/
        mPathEffects[0] = null;
        mPathEffects[1] = new CornerPathEffect(50);
        mPathEffects[2] = new DiscretePathEffect(3f, 10f);
        mPathEffects[3] = new DashPathEffect(new float[]{30, 10, 20, 10, 40, 20}, mPhase);

        Path path = new Path();
        //path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        path.addCircle(0, 0, 3, Path.Direction.CCW);
        mPathEffects[4] = new PathDashPathEffect(path, 12, mPhase, PathDashPathEffect.Style.ROTATE);
        mPathEffects[5] = new ComposePathEffect(mPathEffects[2], mPathEffects[4]);
        mPathEffects[6] = new SumPathEffect(mPathEffects[4], mPathEffects[3]);

        /**绘制路径*/
        for (int i = 0; i < mPathEffects.length; i++) {
            mPaint.setPathEffect(mPathEffects[i]);
            canvas.drawPath(mPath, mPaint);

            //每绘制一条，将画布向下平移250个像素
            canvas.translate(0, 200);
        }

        //刷新偏移值并重绘视图实现动画效果
        mPhase += 1;
        invalidate();
    }
}

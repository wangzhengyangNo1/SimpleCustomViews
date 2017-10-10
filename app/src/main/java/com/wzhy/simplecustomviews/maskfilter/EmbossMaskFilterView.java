package com.wzhy.simplecustomviews.maskfilter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;
import com.wzhy.simplecustomviews.colorfilter.ViewColorMatrixFilter;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by Administrator on 2017-9-21 0021.
 */

public class EmbossMaskFilterView extends View {

    private static final int H_COUNT = 2, V_COUNT = 4;//水平和垂直切割数

    private Paint mPaint;//画笔

    private PointF[] mPointFs;//存储各个巧克力块左上坐标的点

    private int  width, height;//单个巧克力宽高

    private float coorY;//单个巧克力块左上Y轴坐标
    private int mScreenW;
    private int mScreenH;

    public EmbossMaskFilterView(Context context) {
        this(context, null);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //不使用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        //初始化画笔
        initPaint();

        //初始化资源
        initRes(context);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xff603811);

        //设置画笔遮罩滤镜
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{1f, 1f, 1f}, 0.1f, 10f, 20f));
    }

    private void initRes(Context context) {
        /**
         * 获取屏幕宽高
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //WindowManager windowManager = ((Activity) context).getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenW = metrics.widthPixels;
        mScreenH = metrics.heightPixels;

        width = mScreenW / H_COUNT;
        height = mScreenH / V_COUNT;

        int count = V_COUNT * H_COUNT;

        mPointFs = new PointF[count];
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                coorY = i * height / 2f;
                mPointFs[i] = new PointF(0, coorY);
            } else {
                mPointFs[i] = new PointF(width, coorY);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        for (int i = 0; i < V_COUNT * H_COUNT; i++) {
            canvas.drawRect(mPointFs[i].x, mPointFs[i].y, mPointFs[i].x + width, mPointFs[i].y + height, mPaint);
        }
    }
}

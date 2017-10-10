package com.wzhy.simplecustomviews.maskfilter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class MaskFilterView extends View {
    private static final int RECT_SIZE = 800;  
    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
  
    private int left, top, right, bottom;//
    private int mScreenW;
    private int mScreenH;
    private Rect mRect;

    public MaskFilterView(Context context) {  
        this(context, null);  
    }  
  
    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        mContext = context;

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // 初始化画笔  
        initPaint();  
  
        // 初始化资源  
        initRes(context);  
    }  
  
    /** 
     * 初始化画笔 
     */  
    private void initPaint() {  
        // 实例化画笔  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);  
        mPaint.setStyle(Paint.Style.FILL);  
        mPaint.setColor(0xFF603811);  
  
        // 设置画笔遮罩滤镜  
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));

    }  
  
    /** 
     * 初始化资源 
     */  
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

        /**计算图形绘制到屏幕中心时，图形的位置坐标*/
        left =  mScreenW / 2 - RECT_SIZE / 2;
        top = mScreenH / 2 - RECT_SIZE / 2;
        right = mScreenW / 2 + RECT_SIZE / 2;
        bottom = mScreenH / 2 + RECT_SIZE / 2;

    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  
        canvas.drawColor(Color.GRAY);
  
        // 画一个矩形  
        canvas.drawRect(left, top, right, bottom, mPaint);
    }  
}  
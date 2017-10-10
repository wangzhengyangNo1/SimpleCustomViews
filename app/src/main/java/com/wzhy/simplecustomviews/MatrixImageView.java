package com.wzhy.simplecustomviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

/**
 * 可平移缩放旋转的ImageView
 * <p>
 * Created by Administrator on 2017-9-30 0030.
 */

public class MatrixImageView extends ImageView {

    public static final int MODE_NONE = 0x00100;//默认触摸模式
    public static final int MODE_DRAG = 0x00101;//拖拽模式
    public static final int MODE_ZOOM = 0x00102;//缩放/旋转模式
    private Matrix currMatrix;//当前Matrix对象
    private Matrix savedMatrix;//已保存的Matrix对象
    private PointF start;//起点
    private PointF mid;//中点
    private int mode;//当前的触摸模式

    private float[] preEventCoor;//上一次个触摸点的坐标集合

    private float preMove = 1f;//上一次手指移动的距离
    private float savedRotation = 0f;//已保存的旋转角度
    private float rotation = 0f;//旋转角度


    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init();
    }

    private void init() {
        /**实例化对象*/
        currMatrix = new Matrix();
        savedMatrix = new Matrix();

        start = new PointF();
        mid = new PointF();

        Size screenSize = DisplayUtil.getScreenSize();
        int screenW = screenSize.getWidth();
        int screenH = screenSize.getHeight();

        //模式初始化
        mode = MODE_NONE;

        /**设置图片资源*/
        Bitmap bitmap = BitmapFactory.decodeResource(MyApp.getAppContext().getResources(), R.drawable.img_girl);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenW, screenH, true);

        setImageBitmap(bitmap);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://单点接触屏幕时
                savedMatrix.set(currMatrix);
                start.set(event.getX(), event.getY());
                mode = MODE_DRAG;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://第二个点接触屏幕时
                preMove = calSpacing(event);
                if (preMove > 10f) {
                    savedMatrix.set(currMatrix);
                    calMidPoint(mid, event);
                    mode = MODE_ZOOM;
                }
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                savedRotation = calRotation(event);
                break;
            case MotionEvent.ACTION_UP://单点离开屏幕时
            case MotionEvent.ACTION_POINTER_UP://第二点离开屏幕时
                mode = MODE_NONE;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_MOVE://触摸点移动时
                /*单点触控拖拽平移*/
                if (mode == MODE_DRAG) {
                    currMatrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    currMatrix.postTranslate(dx, dy);
                } else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {
                    /*两点触控缩放旋转*/
                    float currMove = calSpacing(event);
                    currMatrix.set(savedMatrix);
                    /*指尖移动距离大于10f缩放*/
                    if (currMove > 10f) {
                        float scale = currMove / preMove;
                        currMatrix.postScale(scale, scale, mid.x, mid.y);
                    }


                    /*保持两点时旋转*/
                    if (preEventCoor != null) {
                        rotation = calRotation(event);
                        float r = rotation - savedRotation;
                        currMatrix.postRotate(r, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }

                break;

        }

        setImageMatrix(currMatrix);
        return true;
    }

    /**
     * 计算两个触点间的距离
     */
    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 计算两个触摸点的中点坐标
     */
    private void calMidPoint(PointF point, MotionEvent event) {
        float doubleX = event.getX(0) + event.getX(1);
        float doubleY = event.getY(0) + event.getY(1);
        point.set(doubleX / 2, doubleY / 2);
    }

    private float calRotation(MotionEvent event) {
        double deltaX = event.getX(0) - event.getX(1);
        double deltaY = event.getY(0) - event.getY(1);
        double radius = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radius);
    }


}

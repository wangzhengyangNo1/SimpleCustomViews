package com.wzhy.simplecustomviews.bitmapmesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wzhy.simplecustomviews.R;

/**
 * 来自博客：http://blog.csdn.net/aigestudio/article/details/41960507
 * 方法理解和使用{@link android.graphics.Canvas#drawBitmapMesh(Bitmap, int, int, float[], int, int[], int, Paint)}
 * Created by Administrator on 2017-10-9 0009.
 */

public class BitmapMeshView2nd extends View {

    //分割数
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    //交点数
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    //位图对象
    private Bitmap mBitmap;

    //基准点、变换点、和线段绘制对应画笔
    private Paint oriPaint;
    private Paint movePaint;
    private Paint linePaint;

    //基准交点坐标数组
    private float[] matrixOriginal = new float[COUNT * 2];
    //变换后交点坐标数组
    private float[] matrixMoved = new float[COUNT * 2];
    //触摸屏幕时手指的x,y坐标
    private float eventX, eventY;


    public BitmapMeshView2nd(Context context, AttributeSet attrs) {
        super(context, attrs);


        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setFocusable(true);

        //实例化画笔并设置颜色
        oriPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oriPaint.setColor(0x660000ff);
        movePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(0x99ff0000);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xfffffb00);

        //获取位图资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_girl);


        //初始化坐标数组
        int index = 0;

        for (int y = 0; y <= HEIGHT; y++) {
            float fy = 1.0f * mBitmap.getHeight() * y / HEIGHT;

            for (int x = 0; x <= WIDTH; x++) {
                float fx = 1.0f * mBitmap.getWidth() * x / WIDTH;
                setXY(matrixMoved, index, fx, fy);
                setXY(matrixOriginal, index, fx, fy);
                index += 1;
            }
        }
    }

    /**
     * 设置坐标数组
     * @param coorArr 坐标数组
     * @param index 标识值
     * @param fx x坐标
     * @param fy y坐标
     */
    private void setXY(float[] coorArr, int index, float fx, float fy) {
        coorArr[index * 2 + 0] = fx;
        coorArr[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制网格位图
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, matrixMoved, 0, null, 0, null);

        //绘制参考元素
        drawGuide(canvas);
    }

    /**
     * 绘制参考元素
     * @param canvas
     */
    private void drawGuide(Canvas canvas) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float oriX = matrixOriginal[i + 0];
            float oriY = matrixOriginal[i + 1];
            canvas.drawCircle(oriX, oriY, 4, oriPaint);


            float curX = matrixMoved[i + 0];
            float curY = matrixMoved[i + 1];
            canvas.drawCircle(curX, curY, 4, movePaint);

            canvas.drawLine(oriX, oriY, curX, curY, linePaint);
        }
        canvas.drawCircle(eventX, eventY, 6, linePaint);
    }

    /**
     * 计算变换数组坐标
     */
    private void smudge() {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float oriX = matrixOriginal[i + 0];
            float oriY = matrixOriginal[i + 1];

            float event_ori_x = eventX - oriX;
            float event_ori_y = eventY - oriY;

            float kv_kat = event_ori_x * event_ori_x + event_ori_y * event_ori_y;

            float pull = (float) (1000000 / kv_kat / Math.sqrt(kv_kat));

            if (pull >= 1) {
                matrixMoved[i + 0] = eventX;
                matrixMoved[i + 1] = eventY;
            } else {
                matrixMoved[i + 0] = oriX + event_ori_x * pull;
                matrixMoved[i + 1] = oriY + event_ori_y * pull;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        eventX = event.getX();
        eventY = event.getY();
        smudge();
        invalidate();
        return true;
    }
}

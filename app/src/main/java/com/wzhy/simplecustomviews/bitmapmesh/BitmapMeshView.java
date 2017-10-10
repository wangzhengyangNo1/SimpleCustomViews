package com.wzhy.simplecustomviews.bitmapmesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

import com.wzhy.simplecustomviews.R;

/**
 * 方法使用{@link android.graphics.Canvas#drawBitmapMesh(Bitmap, int, int, float[], int, int[], int, Paint)}
 * Created by Administrator on 2017-10-9 0009.
 */

public class BitmapMeshView extends View {

    private static final int WIDTH = 19;//横向分割网格数
    private static final int HEIGHT = 19;//纵向分割网格数
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);//横纵向网格交织
    private final Bitmap mBitmap;//位图资源
    private final float[] verts;//交点坐标数组

    public BitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取位图资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_girl);

        //实例化数组
        verts = new float[COUNT * 2];

        //takeSkew();

        takeScale();


    }

    /**
     * 错切效果
     */
    private void takeSkew() {
    /*生成各个交点坐标*/
        int index = 0;
        float multiple = mBitmap.getWidth();
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = 1.0f * mBitmap.getHeight() * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = 1.0f * mBitmap.getWidth() * x / WIDTH + (HEIGHT - y) * 1.0f / HEIGHT * multiple;
                setXY(fx, fy, index++);
            }
        }
    }

    /**
     * 放大效果
     */
    private void takeScale() {
        int index = 0;
        float multipleY = 1.0f * mBitmap.getHeight() / HEIGHT;
        float multipleX = 1.0f * mBitmap.getWidth() / WIDTH;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = multipleY * y;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = multipleX * x;

                setXY(fx, fy, index);

                if (8 == y) {
                    if (9 == x) {
                        setXY(fx - multipleX, fy-multipleY, index);
                    }
                    if (10 == x) {
                        setXY(fx + multipleX, fy - multipleY, index);
                    }
                }

                if (9 == y) {
                    if (9 == x) {
                        setXY(fx - multipleX, fy + multipleY, index);
                    }
                    if (10 == x) {
                        setXY(fx + multipleX, fy + multipleY, index);
                    }
                }

                index += 1;
            }
        }
    }

    /**
     * 将计算后的交点坐标存入数组
     * @param fx x坐标
     * @param fy y坐标
     * @param index 标识值
     */
    private void setXY(float fx, float fy, int index) {
        verts[index * 2 + 0] = fx;
        verts[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //位置网格位图
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }
}

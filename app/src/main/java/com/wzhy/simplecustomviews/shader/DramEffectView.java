package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.MyApp;
import com.wzhy.simplecustomviews.R;
import com.wzhy.simplecustomviews.utils.DisplayUtil;
import com.wzhy.simplecustomviews.utils.Size;

/**
 * Created by Administrator on 2017-9-29 0029.
 */

public class DramEffectView extends View {
    private Paint mBitmapPaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mXfermode;
    private int screenW;
    private int screenH;
    private int x, y;
    private Paint mShaderPaint;
    private Bitmap mDarkCornerBitmap;

    public DramEffectView(Context context) {
        this(context, null);
    }

    public DramEffectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DramEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initRes();
        initPaint();
    }

    /**
     * 初始化资源
     */
    private void initRes() {
        //获取位图
        mBitmap = BitmapFactory.decodeResource(MyApp.getAppContext().getResources(), R.drawable.img_girl);

        //实例化混合模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        //获取屏幕宽高
        Size screen = DisplayUtil.getScreenSize();
        screenW = screen.getWidth();
        screenH = screen.getHeight();

        //计算坐标
        x = screenW / 2 - mBitmap.getWidth() / 2;
        y = screenH / 2 - mBitmap.getHeight() / 2;
    }

    /**
     * 初始变化画笔
     */
    private void initPaint() {
        //实例化画笔
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //去饱和、提亮、色相矫正
        mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                0.8587F, 0.2940F, -0.0927F, 0, 6.79F,
                0.0821F, 0.9145F, 0.0634F, 0, 6.79F,
                0.2019F, 0.1097F, 0.7483F, 0, 6.79F,
                0, 0, 0, 1, 0
        }));

        /**==================================================**/

        //实例化Shader图形的画笔
        mShaderPaint = new Paint();

        //根据我们的源图大小生成暗角Bitmap
        mDarkCornerBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //将暗角Bitmap注入Canvas
        Canvas canvas = new Canvas(mDarkCornerBitmap);

        //计算径向渐变半径
        float radius = canvas.getHeight() * (2f / 3f);

        //实例化径向渐变
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth() / 2f, canvas.getHeight() / 2, radius, new int[]{0, 0x33000000, 0x80000000}, new float[]{0f, 0.7f, 1.0f}, Shader.TileMode.CLAMP);

        //实例化一个矩阵
        Matrix matrix = new Matrix();

        //设置矩阵的缩放
        matrix.setScale(canvas.getWidth() / (radius * 2f), 1.0f);

        //设置矩阵的预平移
        matrix.preTranslate(((radius * 2) - canvas.getWidth()) / 2, 0);

        //将矩阵注入到径向渐变
        radialGradient.setLocalMatrix(matrix);

        //设置画笔Shader
        mShaderPaint.setShader(radialGradient);

        //绘制矩形
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        //新建图层
        int sc = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //绘制混合颜色
        canvas.drawColor(0xcc1c093e);

        //设置混合模式
        mBitmapPaint.setXfermode(mXfermode);

        //绘制位图
        canvas.drawBitmap(mBitmap, x, y, mBitmapPaint);

        //还原混合模式
        mBitmapPaint.setXfermode(null);

        //还原画布
        canvas.restoreToCount(sc);

        /**=========================**/
        //绘制我们画好的径向渐变图
        canvas.drawBitmap(mDarkCornerBitmap, x, y, null);

    }
}

package com.wzhy.simplecustomviews.studypath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 折线图
 * 还没涉及到测量，尺寸均用比例表示
 * 来自爱哥的博客（专栏：自定义控件其实很简单）：http://blog.csdn.net/aigestudio/article/details/41960507
 */

public class PolylineView extends View {

    /**
     * 网格区域相对位置
     */
    public static final float LEFT = 1 / 16f;
    public static final float TOP = 1 / 16f;
    public static final float RIGHT = 15 / 16f;
    public static final float BOTTOM = 7 / 8f;

    /**
     * 文字区域相对位置
     */
    public static final float TIME_X = 3 / 32f;
    public static final float TIME_Y = 1 / 16f;
    public static final float MONEY_X = 31 / 32f;
    public static final float MONEY_y = 15 / 16f;

    /**
     * 文字相对大小
     */
    public static final float TEXT_SIGN = 1 / 32f;

    /**
     * 粗线和细线相对大小
     */
    public static final float THICK_LINE_WIDTH = 1 / 128f;
    public static final float THIN_LINE_WIDTH = 1 / 512f;

    /**
     * 文字画笔、线条画笔、点画笔
     */
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private Paint mPointPaint;

    /**
     * 路径
     */
    private Path mPath;

    /**
     * 绘制曲线的Bitmap对象
     */
    private Bitmap mBitmap;
    /**
     * 装载mBitmap的Canvas对象
     */
    private Canvas mCanvas;

    /**
     * 数据列表
     */
    private List<PointF> pointFs;

    /**
     * x、y轴刻度值
     */
    private float[] xDivValues;
    private float[] yDivValues;

    /**
     * 控件尺寸
     */
    private int viewSize;

    /**
     * 文字坐标
     */
    private float textY_x;
    private float textY_y;
    private float textX_x;
    private float textX_y;

    /**
     * x、y坐标标识文字字体大小
     */
    private float textSignSize;

    /**
     * 网格区域上下左右坐标
     */
    private float left;
    private float top;
    private float right;
    private float bottom;

    /**
     * 粗线和细线宽度
     */
    private float thickLineWidth;
    private float thinLineWidth;

    /**
     * 设置x、y坐标分别表示什么数据（文字）
     */
    private String signX;
    private String SignY;

    /**
     * 横纵轴向最大刻度
     */
    private float maxX;
    private float maxY;

    /**
     * 刻度间隔
     */
    private float spaceX;
    private float spaceY;


    public PolylineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //实例化文本画笔并设置参数
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);

        //实例化线条画笔并设置参数
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.WHITE);

        //实例化点画笔并设置参数
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.WHITE);

        //实例化Path对象
        mPath = new Path();

        //实例化canvas对象
        mCanvas = new Canvas();

        //初始化数据
        initData();
    }

    /**
     * 初始化数据支持
     * View初始化时，可以考虑给予一个模拟数据
     * 当然我们可以通过setData方法设置自己的数据
     */
    private void initData() {
        Random random = new Random();
        pointFs = new ArrayList<PointF>();

        for (int i = 0; i < 20; i++) {
            PointF pointF = new PointF();
            pointF.x = (float) (random.nextInt(100) * i);
            pointF.y = (float) (random.nextInt(100) * i);
            pointFs.add(pointF);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //获取控件尺寸
        viewSize = w;

        //计算纵轴标识文本坐标
        textY_x = viewSize * TIME_X;
        textY_y = viewSize * TIME_Y;

        //计算横轴标识文本坐标
        textX_x = viewSize * MONEY_X;
        textX_y = viewSize * MONEY_y;

        //计算x、y轴标识文字大小
        textSignSize = viewSize * TEXT_SIGN;

        //计算网格左上右下坐标
        left = viewSize * LEFT;
        top = viewSize * TOP;
        right = viewSize * RIGHT;
        bottom = viewSize * BOTTOM;

        //计算粗线宽度
        thickLineWidth = viewSize * THICK_LINE_WIDTH;

        //计算细线宽度
        thinLineWidth = viewSize * THIN_LINE_WIDTH;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //填充背景
        canvas.drawColor(0xff9596c4);

        //绘制标识元素
        drawSign(canvas);

        //绘制网格
        drawGrid(canvas);

        //绘制折线
        drawPolyline(canvas);

    }

    /**
     * 绘制标识元素
     *
     * @param canvas 画布
     */
    private void drawSign(Canvas canvas) {
        //锁定画布
        canvas.save();

        //设置文本画笔文字尺寸
        mTextPaint.setTextSize(textSignSize);

        //绘制纵轴标识文字
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(null == SignY ? "y" : SignY, textY_x, textY_y, mTextPaint);

        //绘制横轴标识文字
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(null == signX ? "x" : signX, textX_x, textX_y, mTextPaint);

        //释放画布
        canvas.restore();
    }

    /**
     * 绘制网格
     *
     * @param canvas 画布
     */
    private void drawGrid(Canvas canvas) {

        //锁定画布
        canvas.save();

        //设置线条画笔宽度
        mLinePaint.setStrokeWidth(thickLineWidth);

        //计算x、y轴Path
        mPath.moveTo(left, top);
        mPath.lineTo(left, bottom);
        mPath.lineTo(right, bottom);

        //绘制x、y轴
        canvas.drawPath(mPath, mLinePaint);

        //绘制网格线条
        drawGridLines(canvas);

        //释放画布
        canvas.restore();

    }

    private void drawGridLines(Canvas canvas) {
        //计算刻度文字尺寸
        float textRulerSize = textSignSize / 2f;

        //重置文字画笔文字尺寸
        mTextPaint.setTextSize(textRulerSize);

        //重置线条画笔描边宽度
        mLinePaint.setStrokeWidth(thinLineWidth);

        //获取数据长度
        int count = pointFs.size();

        //计算除数值为数据长度减1
        int divisor = count - 1;
        //计算横轴数据最大值
        maxX = 0;
        for (int i = 0; i < count; i++) {
            if (maxX < pointFs.get(i).x) {
                maxX = pointFs.get(i).x;
            }
        }

        //计算横轴最近的能被count整除的值
        int remainderX = ((int) maxX) % divisor;
        maxX = remainderX == 0 ? ((int) maxX) : ((int) maxX) + divisor - remainderX;

        //计算纵轴数据最大值
        maxY = 0;
        for (int i = 0; i < count; i++) {
            if (maxY < pointFs.get(i).y) {
                maxY = pointFs.get(i).y;
            }
        }

        //计算纵轴最近的能被count整除的值
        int remainderY = ((int) maxY) % divisor;
        maxY = remainderY == 0 ? ((int) maxY) : ((int) maxY) + divisor - remainderY;

        //生成横轴刻度值
        xDivValues = new float[count];
        for (int i = 0; i < count; i++) {
            xDivValues[i] = maxX / divisor * i;
        }

        //生成纵轴刻度值
        yDivValues = new float[count];
        for (int i = 0; i < count; i++) {
            yDivValues[i] = maxY / divisor * i;
        }

        //计算横纵坐标刻度间隔
        spaceX = viewSize * (RIGHT - LEFT) / count;
        spaceY = viewSize * (BOTTOM - TOP) / count;

        //锁定画布并设置画布透明度为75%
        int saveCount = canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 75, Canvas.ALL_SAVE_FLAG);

        //绘制横纵线段
        for (int i = 1; i < count; i++) {
            canvas.drawLine(viewSize * LEFT, viewSize * BOTTOM - spaceY * i, viewSize * LEFT + spaceX * divisor, viewSize * BOTTOM - spaceY * i, mLinePaint);
            canvas.drawLine(viewSize * LEFT + spaceX * i, viewSize * BOTTOM, viewSize * LEFT + spaceX * i, viewSize * BOTTOM - spaceY * divisor, mLinePaint);
        }

        //还原画布
        canvas.restoreToCount(saveCount);

        //绘制原点值
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseLineY = viewSize * BOTTOM - fontMetrics.top + 12f;
        canvas.drawText("0", left - 12f, baseLineY, mTextPaint);

        //绘制横轴刻度值
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 1; i < count; i++) {
            float divX = viewSize * LEFT + spaceX * i;
            canvas.drawText(String.valueOf(xDivValues[i]), divX, baseLineY, mTextPaint);

        }

        //绘制纵轴刻度值
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 1; i < count; i++) {
            float divY = viewSize * BOTTOM - spaceY * i;
            baseLineY = divY - (fontMetrics.ascent + fontMetrics.descent) / 2f;
            canvas.drawText(String.valueOf(yDivValues[i]), viewSize*LEFT - 12f, baseLineY, mTextPaint);
        }

    }

    /**
     * 绘制折线
     *
     * @param canvas 画笔
     */
    private void drawPolyline(Canvas canvas) {
        //生成一个Bitmap对象大小和我们的网格大小一致
        mBitmap = Bitmap.createBitmap((int) (viewSize * (RIGHT - LEFT) - spaceX), (int) (viewSize * (BOTTOM - TOP) - spaceY), Bitmap.Config.ARGB_8888);

        //将Bitmap注入Canvas
        mCanvas.setBitmap(mBitmap);

        //为画布填充一个半透明的红色
        mCanvas.drawARGB(75, 255, 0, 0);

        //重置曲线
        mPath.reset();

        /*生成Path和绘制Point*/
        for (int i = 0; i < pointFs.size(); i++) {
            //计算x坐标
            float x = mCanvas.getWidth() / maxX * pointFs.get(i).x;

            //计算Y坐标
            float y = mCanvas.getHeight() / maxY * pointFs.get(i).y;
            y = mCanvas.getHeight() - y;

            //绘制点
            mCanvas.drawCircle(x, y, thickLineWidth, mPointPaint);

            /*如果是第一个点，浙江其设置为Path的起点*/
            if (i == 0) {
                mPath.moveTo(x, y);
            } else {
                mPath.lineTo(x, y);
            }
        }

        //设置PathEffect
        //mLinePaint.setPathEffect(new CornerPathEffect(200));

        //重置线条宽度
        mLinePaint.setStrokeWidth(thinLineWidth);

        //将Path绘制到我们自定义的Canvas上
        mCanvas.drawPath(mPath, mLinePaint);

        //将bitmap绘制到原来的canvas
        canvas.drawBitmap(mBitmap, left, top + spaceY, null);
    }

    /**
     * 设置数据
     *
     * @param pointFs 点集合
     * @param signX   x轴标识
     * @param signY   y轴标识
     */
    public synchronized void setData(List<PointF> pointFs, String signX, String signY) {
        /* 数据为空直接GG */
        if (null == pointFs || pointFs.size() == 0) {
            throw new IllegalArgumentException("No data to display!");
        }

        /*
        * 控制数据长度不超过10个
        */
        if (pointFs.size() > 10) {
            throw new IllegalArgumentException("The data is too long to display!");
        }

        //设置数据并重新绘制视图
        this.pointFs = pointFs;
        this.signX = signX;
        this.SignY = signY;
        invalidate();

    }
}

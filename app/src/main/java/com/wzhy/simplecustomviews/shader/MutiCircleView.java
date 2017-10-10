package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

/**
 * Created by Administrator on 2017-9-30 0030.
 */

public class MutiCircleView extends View {
    private static final float STROKE_WIDTH = 1F / 256F;//描边宽度占比
    private static final float LINE_LENGTH = 3F / 32F;//线段长度占比
    private static final float LARGE_CIRCLE_RADIUS = 3F / 32F;//大圆半径
    private static final float SMALL_CIRCLE_RADIUS = 5F / 64F;//小圆半径
    private static final float ARC_RADIUS = 1F / 8F;//弧半径
    private static final float ARC_TEXT_RADIUS = 5F / 32F;//弧围绕文字半径
    private static final float SPACE = 1F / 64F;//大圆小圆线段两端间隔占比
    private static final float TEXT_SIZE = 1F / 32F;


    private Paint mStrokePaint;//描边画笔
    private int size;//控件边长（强制宽高相等）
    private float strokeWidth;//描边宽度
    private float largeCircleRadius;//大圆半径
    private float smallCircleRadius;
    private float ccY;//中心圆圆心Y坐标
    private int ccX;//中心圆圆心X坐标
    private float lineLength;
    private float space;
    private float textSize;
    private float arcRadius;
    private float arcTextRadius;


    public MutiCircleView(Context context) {
        super(context, null);
    }

    public MutiCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutiCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化描边画笔
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);//强制长宽一致
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //获取控件边长
        size = w;

        //参数计算
        paramsCal();
    }

    private void paramsCal() {
        //计算描边宽度
        strokeWidth = STROKE_WIDTH * size;

        //计算大圆半径
        largeCircleRadius = LARGE_CIRCLE_RADIUS * size;

        //计算小圆半径
        smallCircleRadius = SMALL_CIRCLE_RADIUS * size;

        //计算线段长度
        lineLength = size * LINE_LENGTH;

        //计算大圆小圆线段两端间隔
        space = size * SPACE;

        //字体大小
        textSize = TEXT_SIZE * size;

        arcRadius = ARC_RADIUS * size;

        arcTextRadius = ARC_TEXT_RADIUS * size;

        //计算中心圆圆心坐标
        ccX = size / 2;
        ccY = size / 2 + largeCircleRadius;

        //设置参数
        setParams();

    }

    private void setParams() {
        //设置描边宽度
        mStrokePaint.setStrokeWidth(strokeWidth);
        mStrokePaint.setTextSize(textSize);
        mStrokePaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawColor(0xfff29b76);
        //绘制中心圆
        canvas.drawCircle(ccX, ccY, largeCircleRadius, mStrokePaint);

        //绘制左上方图形
        drawTopLeft(canvas);

        //绘制右上方图形
        drawTopRight(canvas);

        //绘制右下方图形
        drawBottomRight(canvas);

        //绘制下方图形
        drawBottom(canvas);

        //绘制左下方图形
        drawBottomLeft(canvas);
    }

    private void drawText(Canvas canvas, String text, float x, float y) {
        mStrokePaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics fontMetrics = mStrokePaint.getFontMetrics();
        canvas.drawText(text, x, y - (fontMetrics.ascent + fontMetrics.descent) / 2, mStrokePaint);
        mStrokePaint.setStyle(Paint.Style.STROKE);
    }

    private void drawTopLeft(Canvas canvas) {
        //锁定画布
        canvas.save();

        //平移和旋转画布
        canvas.translate(ccX, ccY);
        drawText(canvas, "I love fruit!", 0, 0);
        canvas.rotate(-30);


        //依次画线-圆-线-圆
        canvas.drawLine(0, -largeCircleRadius, 0, -largeCircleRadius - lineLength, mStrokePaint);
        canvas.drawCircle(0, -2 * largeCircleRadius - lineLength, largeCircleRadius, mStrokePaint);
        canvas.drawLine(0, -3 * largeCircleRadius - lineLength, 0, -3 * largeCircleRadius - 2 * lineLength, mStrokePaint);
        canvas.drawCircle(0, -4 * largeCircleRadius - 2 * lineLength, largeCircleRadius, mStrokePaint);

        drawText(canvas, "apple", 0, -2 * largeCircleRadius - lineLength);
        drawText(canvas, "pineapple", 0, -4 * largeCircleRadius - 2 * lineLength);

        //释放画布
        canvas.restore();
    }

    private void drawTopRight(Canvas canvas) {
        //锁定画布
        canvas.save();

        canvas.translate(ccX, ccY);
        canvas.rotate(30);

        canvas.drawLine(0, -largeCircleRadius, 0, -largeCircleRadius - lineLength, mStrokePaint);
        canvas.drawCircle(0, -2 * largeCircleRadius - lineLength, largeCircleRadius, mStrokePaint);

        drawText(canvas, "banana", 0, -2 * largeCircleRadius - lineLength);

        drawTopRightArc(canvas, -2 * largeCircleRadius - lineLength);

        //释放画布
        canvas.restore();
    }


    private void drawTopRightArc(Canvas canvas, float circleY) {
        canvas.save();

        canvas.translate(0, circleY);

        canvas.rotate(-30);

        RectF rectFArc = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        mStrokePaint.setStyle(Paint.Style.FILL);
        mStrokePaint.setColor(0x55ec6941);
        canvas.drawArc(rectFArc, -22.5f, -135, true, mStrokePaint);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.WHITE);
        canvas.drawArc(rectFArc, -22.5f, -135, false, mStrokePaint);

        canvas.save();
        //把画布旋转到扇形左端方向
        canvas.rotate(-135f / 2f);

        mStrokePaint.setStyle(Paint.Style.FILL);
        /**
         * 每隔33.75度画一次文本
         */
        for (float i = 0; i < 5 * 33.75f; i += 33.75f) {
            canvas.save();
            canvas.rotate(i);
            canvas.drawText("Angle", 0, -arcTextRadius, mStrokePaint);
            canvas.restore();
        }
        mStrokePaint.setStyle(Paint.Style.STROKE);

        canvas.restore();

        canvas.restore();
    }


    private void drawBottomRight(Canvas canvas) {
        //锁定画布
        canvas.save();

        canvas.translate(ccX, ccY);
        canvas.rotate(100);

        canvas.drawLine(0, -largeCircleRadius - space, 0, -largeCircleRadius - space - lineLength, mStrokePaint);
        canvas.drawCircle(0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius, smallCircleRadius, mStrokePaint);

        drawText(canvas, "orange", 0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius);

        //释放画布
        canvas.restore();
    }


    private void drawBottom(Canvas canvas) {
        //锁定画布
        canvas.save();

        canvas.translate(ccX, ccY);
        canvas.rotate(180);

        canvas.drawLine(0, -largeCircleRadius - space, 0, -largeCircleRadius - space - lineLength, mStrokePaint);
        canvas.drawCircle(0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius, smallCircleRadius, mStrokePaint);

        drawText(canvas, "mango", 0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius);

        //释放画布
        canvas.restore();
    }

    private void drawBottomLeft(Canvas canvas) {
        //锁定画布
        canvas.save();

        canvas.translate(ccX, ccY);
        canvas.rotate(250);

        canvas.drawLine(0, -largeCircleRadius - space, 0, -largeCircleRadius - space - lineLength, mStrokePaint);
        canvas.drawCircle(0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius, smallCircleRadius, mStrokePaint);

        drawText(canvas, "peach", 0, -largeCircleRadius - 2 * space - lineLength - smallCircleRadius);

        //释放画布
        canvas.restore();
    }


}

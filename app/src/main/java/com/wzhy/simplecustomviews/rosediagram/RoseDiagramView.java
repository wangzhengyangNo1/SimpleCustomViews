package com.wzhy.simplecustomviews.rosediagram;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * 自定义玫瑰图控件
 * <p>
 * Created by wangzhengyang on 2017-9-21 0021.
 */

public class RoseDiagramView extends View {

    //画笔
    private Paint mPaint;
    //用于画引线的Path
    private Path mLabelPath;
    //画弧度的矩形
    private RectF mArcRect;
    //玫瑰图外圆半径
    private float outerRadius;
    //玫瑰图内圆半径
    private float innerRadius;
    //最大描边宽度
    private float maxStrokeWidth;
    //中心X坐标
    private float centerX;
    //中心Y坐标
    private float centerY;
    //扇形数量
    private int sectorCount = 6;
    //每个扇形所占角度（平均）
    private float aSectorAngle;
    //引线转折点所在圆半径
    private float labelRadius;
    //角度，用于加载动画时扫描
    private float angle = 360;
    //缩放比例（高度度相对于800px)
    private float scale = 1;

    public RoseDiagramView(Context context) {
        this(context, null);
    }

    public RoseDiagramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoseDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //画笔，去锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //引线的path实例
        mLabelPath = new Path();

        mArcRect = new RectF();

        petalInfoList.add(new PetalInfo<Float>("微信", 0xFFC13531, 1.0f));
        petalInfoList.add(new PetalInfo<Float>("微博", 0xFF2F4554, 0.7f));
        petalInfoList.add(new PetalInfo<Float>("论坛", 0xFF619FA7, 0.5f));
        petalInfoList.add(new PetalInfo<Float>("博客", 0xFFD38165, 0.6f));
        petalInfoList.add(new PetalInfo<Float>("平媒", 0xFF90C6AD, 0.2f));
        petalInfoList.add(new PetalInfo<Float>("视频", 0xFF749E82, 0.7f));
        petalInfoList.add(new PetalInfo<Float>("新闻网站", 0xFFC98522, 0.4f));
        petalInfoList.add(new PetalInfo<Float>("新闻APP", 0xFFBCA199, 0.8f));
        petalInfoList.add(new PetalInfo<Float>("其它", 0xFF6E7074, 0.3f));
        setSectorCount(petalInfoList.size());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff081629);

        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int spaceWidth = width - paddingLeft - paddingRight;
        int spaceHeight = height - paddingTop - paddingBottom;

        centerX = paddingLeft + spaceWidth / 2f;
        centerY = paddingTop + spaceHeight / 2f;

        float computeHeight = spaceHeight;
        float ratioOfWH = 1.0f * spaceWidth / spaceHeight;
        if (ratioOfWH < 6f / 5f) {
            computeHeight = 5f / 6f * spaceWidth;
        }

        scale = computeHeight / 800f;


        //计算最大半径
        //float minDimen = computeHeight;
        outerRadius = computeHeight * 3f / 10f;
        //最大尺寸
        maxStrokeWidth = outerRadius * 2f / 3f;
        //最小半径
        innerRadius = outerRadius - maxStrokeWidth;
        //引线转折点所在圆半径
        labelRadius = computeHeight * 3 / 8f;

        //drawStrokeCircle(canvas);

        for (int i = 0; i < petalInfoList.size(); i++) {
            PetalInfo petalInfo = petalInfoList.get(i);
            boolean isDrawn = drawArcArea(canvas, petalInfo.name, petalInfo.color, i, (float) petalInfo.objT);
            if (!isDrawn) {
                break;
            }
        }
    }

    /**
     * 绘制内圆和外圆
     *
     * @param canvas
     */
    private void drawStrokeCircle(Canvas canvas) {
        //绘制小圆和大圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(centerX, centerY, innerRadius, mPaint);
        canvas.drawCircle(centerX, centerY, outerRadius, mPaint);
    }

    /**
     * 绘制扇形
     *
     * @param canvas
     * @param color
     * @param position
     * @param percent
     * @param name
     */
    private boolean drawArcArea(final Canvas canvas, String name, int color, final int position, float percent) {

        float sweepAngle = angle - position * aSectorAngle;
        if (sweepAngle <= 0) {
            return false;
        } else if (sweepAngle > aSectorAngle) {
            sweepAngle = aSectorAngle;
        }

        /** 1. 绘制扇形 */
        //设置阴影
        mPaint.setShadowLayer(1, 1.6f, 1.2f, Color.DKGRAY);
        //设置画笔风格为描边
        mPaint.setStyle(Paint.Style.STROKE);

        //画笔宽度
        float strokeWidth = maxStrokeWidth * percent;
        if (strokeWidth < 1f) {
            strokeWidth = 1f;
        }
        //为画笔设置描边
        mPaint.setStrokeWidth(strokeWidth);
        //计算出当前要画扇形的半径
        float currRadius = strokeWidth / 2f + innerRadius;
        //重置扇形所在矩形
        mArcRect.set(centerX - currRadius, centerY - currRadius, centerX + currRadius, centerY + currRadius);
        mPaint.setColor(color);//设置画笔颜色
        //绘制扇形，根据位置确定起始角度，
        // 0位置起始角度为-90°，i位置则为-90° + i * aSectorAngle，aSectorAngle--一个扇形的角度
        canvas.drawArc(mArcRect, -90 + position * aSectorAngle, sweepAngle, false, mPaint);

        //清除阴影
        mPaint.clearShadowLayer();

        //扫描角度小于单个扇形角度的一般时，不绘制标注
        if (sweepAngle < aSectorAngle / 2) {
            return true;
        }

        /** 2. 绘制引线（标注线） */
        //计算出弧度，radian（弧度） = Math.PI（π） * angle（角度） / 180f
        float radianNum = -0.5f + aSectorAngle / 360.0f + position * aSectorAngle / 180.0f;
        double radian = Math.PI * radianNum;//弧度
        //计算出扇形外圆弧线的中点坐标，把它作为引线的起始点
        float startX = (float) (centerX + (innerRadius + strokeWidth) * Math.cos(radian));
        float startY = (float) (centerY + (innerRadius + strokeWidth) * Math.sin(radian));

        //计算引线的转折点坐标，所有转折点在一个圆上，半径为labelRadius
        float endX = (float) (centerX + labelRadius * Math.cos(radian));
        float endY = (float) (centerY + labelRadius * Math.sin(radian));

        //设置描边宽度
        mPaint.setStrokeWidth(1.2f);
        mLabelPath.reset();//重置路径
        mLabelPath.moveTo(startX, startY);//从起始位置
        mLabelPath.lineTo(endX, endY);//画斜线到转折点
        mLabelPath.moveTo(endX, endY);//再从转折点
        //判断得出引线结束点坐标，同时确定文字对齐

        boolean isRightSemicircle = true;
        mPaint.setTextAlign(Paint.Align.LEFT);
        if (radianNum > 0.5 && radianNum < 1.5) {
            isRightSemicircle = false;
            mPaint.setTextAlign(Paint.Align.RIGHT);
        }

        //文字大小
        float textSize = 26f * scale < 15f ? 15f : 26f * scale;
        mPaint.setTextSize(textSize);//设置文字大小
        float nameWidth = mPaint.measureText(name, 0, name.length());

        float textStartX = endX;
        endX += isRightSemicircle ? nameWidth + 10 : -nameWidth - 10;
        mLabelPath.lineTo(endX, endY);//绘制水平线段
        //绘制引线
        canvas.drawPath(mLabelPath, mPaint);


        /**绘制文字*/
        mPaint.setStyle(Paint.Style.FILL);//设置画笔风格为填充

        //计算出基线位置
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLineY = endY - fontMetrics.bottom - 3;
        //根据基线位置绘制文字
        textStartX += isRightSemicircle ? 5f : -5f;
        canvas.drawText(name, textStartX, baseLineY, mPaint);


        textStartX += isRightSemicircle ? 0f : -nameWidth;
        drawNewContent(canvas, mPaint, textStartX, baseLineY + fontMetrics.bottom + 6);

        return true;
    }

    /**
     * 绘制其它内容
     *
     * @param canvas     画布
     * @param mPaint     画笔
     * @param nameLeft   名字文字左边坐标
     * @param nameBottom 名字文字右边坐标
     */
    private void drawNewContent(Canvas canvas, Paint mPaint, float nameLeft, float nameBottom) {
        mPaint.setStrokeWidth(1);
        float textSize = 14f * scale < 8f ? 8f : 14f * scale;
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        //计算出基线位置
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLineY = nameBottom - fontMetrics.top;
        //根据基线位置绘制文字
        mPaint.setColor(Color.GREEN);
        canvas.drawText("正面        25%", nameLeft, baseLineY, mPaint);

//        //计算出基线位置
//        mPaint.setColor(Color.GREEN);
//        baseLineY += fontMetrics.descent - fontMetrics.ascent;
//        //根据基线位置绘制文字
//        canvas.drawText("正面        25%",nameLeft, baseLineY, mPaint);

        //计算出基线位置
        mPaint.setColor(Color.YELLOW);
        baseLineY += fontMetrics.bottom - fontMetrics.top;
        //根据基线位置绘制文字
        canvas.drawText("中性        65%", nameLeft, baseLineY, mPaint);

        //计算出基线位置
        baseLineY += fontMetrics.bottom - fontMetrics.top;
        //根据基线位置绘制文字
        mPaint.setColor(Color.RED);
        canvas.drawText("负面        10%", nameLeft, baseLineY, mPaint);
    }


    private void setAngle(float angle) {
        if (angle < 0) {
            angle = 0;
        }
        if (angle > 360) {
            angle = 360;
        }
        this.angle = angle;
        invalidate();
    }

    private float getAngle() {
        return this.angle;
    }

    Animator[] deployAnimArr = null;

    AnimatorSet deployAnimSet = null;

    /**
     * 启动展开动画
     */
    public void startDeployAnimSet() {

        if (petalInfoList.size() == 0) {
            deployAnimArr = null;
            return;
        }
        if (null == deployAnimArr) {
            deployAnimArr = new ObjectAnimator[petalInfoList.size()];
        } else {
            if (deployAnimArr.length != petalInfoList.size()) {
                deployAnimArr = new ObjectAnimator[petalInfoList.size()];
            }
        }
        int petalCount = petalInfoList.size();
        for (int i = 0; i < petalCount; i++) {
            if (null == deployAnimArr[i]) {
                deployAnimArr[i] = createDeployAnim();
            }
            ObjectAnimator deployAnim = (ObjectAnimator) deployAnimArr[i];
            deployAnim.setDuration(50);
            deployAnim.setFloatValues(i * aSectorAngle, (i + 1) * aSectorAngle);
        }

        if (null == deployAnimSet) {
            deployAnimSet = new AnimatorSet();
            deployAnimSet.setInterpolator(new LinearInterpolator());
        }
        deployAnimSet.playSequentially(deployAnimArr);
        deployAnimSet.start();
    }

    ObjectAnimator deployAnim;

    public void startDeployAnim(){
        if (null == deployAnim) {
            deployAnim = createDeployAnim();
            deployAnim.setFloatValues(0, 360);
        }
        deployAnim.setDuration(1000);
        deployAnim.start();
    }

    private ObjectAnimator createDeployAnim() {
        ObjectAnimator deployAnim = new ObjectAnimator();
        deployAnim.setTarget(this);
        deployAnim.setPropertyName("angle");
        deployAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        return deployAnim;
    }

    public void recycle() {
        if (null != deployAnim) {
            deployAnim.cancel();
            deployAnim = null;
        }

        if (null != deployAnimSet) {
            deployAnimSet.cancel();
            deployAnimSet = null;
        }
        if (null != deployAnimArr) {
            deployAnimArr = null;
        }
        petalInfoList.clear();

    }

    ArrayList<PetalInfo> petalInfoList = new ArrayList<>();

    /**
     * 设置扇形数量
     *
     * @param sectorCount 扇形数量
     */
    private void setSectorCount(int sectorCount) {
        this.sectorCount = sectorCount;
        aSectorAngle = 360f / sectorCount;
        invalidate();
    }

    /**
     * 加载扇形（花瓣）信息集合
     * 默认清除上次数据
     *
     * @param petalList 扇形（花瓣）信息集合
     */
    public void putPetalList(ArrayList<PetalInfo> petalList) {
        putPetalList(petalList, true);
    }

    /**
     * 加载扇形（花瓣）信息集合
     *
     * @param petalList 扇形（花瓣）信息集合
     * @param isClear   是否清除数据，true，清除数据，重新添加；false，不清除数据，添加数据
     */
    public void putPetalList(ArrayList<PetalInfo> petalList, boolean isClear) {
        if (null == petalList || petalList.size() == 0) {
            return;
        }
        if (isClear) {
            petalInfoList.clear();
        }
        petalInfoList.addAll(petalList);
        setSectorCount(petalInfoList.size());
        invalidate();
    }


    /**
     * 扇形（花瓣）信息
     *
     * @param <T> 除了名字和颜色意外其他信息构成的类
     */
    public class PetalInfo<T> {

        public String name;

        @ColorInt
        public int color;

        public T objT;

        public PetalInfo() {
        }

        public PetalInfo(String name, int color, T objT) {
            this.name = name;
            this.color = color;
            this.objT = objT;
        }

        public PetalInfo(String name, int color) {
            this.name = name;
            this.color = color;
        }

        public void setObjT(T objT) {
            this.objT = objT;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}

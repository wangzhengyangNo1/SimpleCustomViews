package com.wzhy.simplecustomviews.measure;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 *  notes:
 *      1. ViewGroup中的onLayout是一个抽象方法，这意味着我们在继承时必须实现，onLayout的目的是为了
 *    确定子元素在父容器中的位置。
 *      2. 一个View的大小由其父容器的测量规格MeasureSpec和View本身的布局参数LayoutParams共同决定。
 *    see {@link ViewGroup#getChildMeasureSpec(int, int, int)}.
 *      3. Android对ViewGroup的测量由两方面构成，一是，对父容器和子元素大小尺寸的测量，主要体现在
 *    onMeasure方法；二是，对父容器中的子元素在其区域内的定位，主要体现在onLayout方法。
 */

public class SquareLayout extends ViewGroup {

    //横向排列标识
    public static final int ORIENTATION_HORIZONTAL = 0;

    //纵向排列标识
    public static final int ORIENTATION_VERTICAL = 1;

    //默认最大行数
    public static final int DEFAULT_MAX_ROW = Integer.MAX_VALUE;

    //默认最大列数
    public static final int DEFAULT_MAX_COLUMN = Integer.MAX_VALUE;

    //最大行数
    private int mMaxRow = DEFAULT_MAX_ROW;

    //最大列数
    private int mMaxColumn = DEFAULT_MAX_COLUMN;

    //排列方向，默认横向
    private int mOrientation = ORIENTATION_VERTICAL;


    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //声明临时变量存储父容器的期望值
        //该值应该等于父容器的内边距加上所有子元素的测量宽高和外边距
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        //声明临时变量存储子元素的测量状态
        int childMeasureState = 0;

        //如果父容器内有子元素
        if (getChildCount() > 0) {

            //遍历子元素
            for (int i = 0; i < getChildCount(); i++) {
                //拿到当前下标下的子控件
                View child = getChildAt(i);

                //如果该子控件没有以“不占用空间”的方式隐藏，则需要被测量计算
                if (child.getVisibility() != View.GONE) {
                    //测量子控件，并考虑其外边距
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    
                    //比较子控件测量宽高取其较大值
                    int childMeasureSize = Math.max(child.getMeasuredWidth(), child.getMeasuredHeight());

                    //重新封装子控件测量规格
                    int childMeasureSpec = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.EXACTLY);

                    //重新测量子控件
                    child.measure(childMeasureSpec, childMeasureSpec);

                    //获取子控件布局参数
                    MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();

                    //计算子控件实际宽高，考虑外边距
                    int childActualWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
                    int childActualHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;

                    //如果为横向排列
                    if (mOrientation == ORIENTATION_HORIZONTAL) {
                        //累加子控件的实际宽度，作为父容器期望宽度
                        parentDesireWidth += childActualWidth;

                        //获取子控件中高度最大值，作为父容器期望高度
                        parentDesireHeight = Math.max(parentDesireHeight, childActualHeight);

                    //如果为纵向排列
                    } else if (mOrientation == ORIENTATION_VERTICAL) {
                        //累加子控件的实际高度，作为父容器期望高度
                        parentDesireHeight += childActualHeight;

                        //获取子控件中宽度最大值，作为父容器期望宽度
                        parentDesireWidth = Math.max(parentDesireWidth, childActualWidth);
                    }
                    //合并子控件的测量状态
                    childMeasureState = combineMeasuredStates(childMeasureState, child.getMeasuredState());
                }
            }

            //考虑父容器内边距，将其累加到期望值
            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();

            //尝试比较父容器期望值与Android建议的最小值，取较大者
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        }

        //确定父容器的测量宽高
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //如果父容器中有子控件
        if (getChildCount() > 0) {
            //声明临时变量存储宽高加和
            int sumDimen = 0;

            //遍历子控件
            for (int i = 0; i < getChildCount(); i++) {
                //拿到当前下标下的子控件
                View child = getChildAt(i);

                //如果该子控件没有以“不占用空间”的方式隐藏，需要被测量计算
                if (child.getVisibility() != View.GONE) {
                    //获取子控件布局参数
                    MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();

                    //获取控件尺寸
                    int childActualSize = child.getMeasuredWidth();//=child.getMeasuredHeight();

                    //如果为横向排列
                    if (mOrientation == ORIENTATION_HORIZONTAL) {
                        //确定子控件左上、右下坐标
                        child.layout(
                                getPaddingLeft() + mlp.leftMargin + sumDimen,
                                getPaddingTop() + mlp.topMargin,
                                getPaddingLeft() + mlp.leftMargin + sumDimen + childActualSize,
                                getPaddingTop() + mlp.topMargin + childActualSize
                        );

                        //累加尺寸
                        sumDimen += childActualSize + mlp.leftMargin + mlp.rightMargin;

                    //如果为横向排列
                    } else if (mOrientation == ORIENTATION_VERTICAL) {
                        //确定子元素左上、右下坐标
                        child.layout(
                                getPaddingLeft() + mlp.leftMargin,
                                getPaddingTop() + mlp.topMargin + sumDimen,
                                getPaddingLeft() + mlp.leftMargin + childActualSize,
                                getPaddingTop() + mlp.topMargin + sumDimen + childActualSize
                        );

                        //累加尺寸
                        sumDimen += childActualSize + mlp.topMargin + mlp.bottomMargin;
                    }

                }
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}

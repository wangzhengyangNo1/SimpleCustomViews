package com.wzhy.simplecustomviews.measure;

import android.content.Context;
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
 *
 *
 *    考虑到行列
 *
 *    VIM（Very Important Method）：
 *    {@link #measureChildWithMargins(View, int, int, int, int)}
 *    {@link MeasureSpec#makeMeasureSpec(int, int)}
 *    {@link View#measure(int, int)}
 *    {@link #combineMeasuredStates(int, int)}
 *    {@link View#getMeasuredState()}
 *    {@link #getSuggestedMinimumWidth()}
 *    {@link #getSuggestedMinimumHeight()}
 *    {@link #resolveSizeAndState(int, int, int)}
 *    {@link #setMeasuredDimension(int, int)}
 *    {@link View#layout(int, int, int, int)}
 */

public class SquareLayoutNew extends ViewGroup {

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
    private int mOrientation = ORIENTATION_HORIZONTAL;


    public SquareLayoutNew(Context context, AttributeSet attrs) {
        super(context, attrs);

        //初始化最大行列数
        mMaxRow = 3;
        mMaxColumn = 3;
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

            //声明两个一维数组分别存储子控件宽高数据
            int[] childWidths = new int[getChildCount()];
            int[] childHeights = new int[getChildCount()];

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
                    childWidths[i] = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
                    childHeights[i] = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;

                    //合并子控件的测量状态
                    childMeasureState = combineMeasuredStates(childMeasureState, child.getMeasuredState());
                }
            }

            //声明临时变量存储行/列宽高
            int iSumWidth = 0;
            int iSumHeight = 0;

            //如果为横向排列
            if (mOrientation == ORIENTATION_HORIZONTAL) {

                //计算余数
                int remainder = getChildCount() % mMaxColumn;

                //计算产生的行数
                int row = getChildCount() / mMaxColumn + 1;

                //声明临时变量存储子控件宽高数组下标
                int index = 0;

                //遍历数组计算父容器期望宽高值
                for (int x = 0; x < row; x++) {

                    int columnNum = x == row - 1 ? remainder : mMaxColumn;

                    for (int y = 0; y < columnNum; y++) {
                        //单行宽度累加
                        iSumWidth += childWidths[index];
                        //单行高度取最大值
                        iSumHeight = Math.max(iSumHeight, childHeights[index++]);
                    }

                    //每一行遍历完后将该行宽度与上一行宽度比较取最大值
                    parentDesireWidth = Math.max(parentDesireWidth, iSumWidth);

                    //每一行遍历完后，累加各行高度
                    parentDesireHeight += iSumHeight;

                    //重置参数
                    iSumWidth = iSumHeight = 0;
                }


                //如果为纵向排列
            } else if (mOrientation == ORIENTATION_VERTICAL) {
                //计算余数
                int remainder = getChildCount() % mMaxRow;

                //计算产生的列数
                int column = getChildCount() / mMaxRow + 1;

                //声明临时变量存储子控件宽高数组下标
                int index = 0;

                //遍历数组计算父容器期望宽高值
                for (int x = 0; x < column; x++) {

                    int rowNum = x == column - 1 ? remainder : mMaxRow;

                    for (int y = 0; y < rowNum; y++) {
                        //单行高度度累加
                        iSumHeight += childHeights[index];
                        //单行宽度取最大值
                        iSumWidth = Math.max(iSumWidth, childWidths[index++]);
                    }

                    //每一列遍历完后将该行高度与上一列高度比较取最大值
                    parentDesireHeight = Math.max(parentDesireHeight, iSumHeight);

                    //每一列遍历完后，累加各列宽度
                    parentDesireWidth += iSumWidth;

                    //重置参数
                    iSumWidth = iSumHeight = 0;
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

            //下标
            //int indexPlus = 1;

            //声明临时变量存储行/列的宽高
            int tempWidth = 0;
            int tempHeight = 0;
            int linearWidth = 0;
            int linearHeight = 0;

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
                        //每次换行重置tempWidth
                        if (i % mMaxColumn == 0) {
                            tempWidth = 0;
                            tempHeight += linearHeight;
                            linearHeight = 0;
                        }

                        //确定子控件左上、右下坐标
                        child.layout(
                                getPaddingLeft() + mlp.leftMargin + tempWidth,
                                getPaddingTop() + mlp.topMargin + tempHeight,
                                getPaddingLeft() + mlp.leftMargin + tempWidth + childActualSize,
                                getPaddingTop() + mlp.topMargin + + tempHeight + childActualSize
                        );

                        //累加尺寸
                        tempWidth += childActualSize + mlp.leftMargin + mlp.rightMargin;
                        linearHeight = Math.max(linearHeight, childActualSize + mlp.topMargin + mlp.bottomMargin);

                    //如果为横向排列
                    } else if (mOrientation == ORIENTATION_VERTICAL) {

                        if (i % mMaxRow == 0) {
                            tempHeight = 0;
                            tempWidth += linearWidth;
                            linearWidth = 0;
                        }
                        //确定子元素左上、右下坐标
                        child.layout(
                                getPaddingLeft() + mlp.leftMargin + tempWidth,
                                getPaddingTop() + mlp.topMargin + tempHeight,
                                getPaddingLeft() + mlp.leftMargin + tempWidth + childActualSize,
                                getPaddingTop() + mlp.topMargin + tempHeight + childActualSize
                        );

                        //累加尺寸
                        tempHeight += childActualSize + mlp.topMargin + mlp.bottomMargin;
                        linearWidth = Math.max(linearWidth, childActualSize + mlp.leftMargin + mlp.rightMargin);
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

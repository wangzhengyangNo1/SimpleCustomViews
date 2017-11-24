package com.wzhy.simplecustomviews.measure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.R.attr.paddingLeft;
import static android.R.attr.paddingTop;

/**
 * 自定义布局
 * notes:
 *      1. ViewGroup中的onLayout是一个抽象方法，这意味着我们在继承时必须实现，onLayout的目的是为了
 *    确定子元素在父容器中的位置。
 *      2. 一个View的大小由其父容器的测量规格MeasureSpec和View本身的布局参数LayoutParams共同决定。
 *    see {@link ViewGroup#getChildMeasureSpec(int, int, int)}.
 */

public class CustomLayout extends ViewGroup {

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //声明临时变量存储父容器的期望值
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        //如果有子元素
        if (getChildCount() > 0) {
            //那么遍历子元素并对其进行测量
            for (int i = 0; i < getChildCount(); i++) {
                //获取子元素
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    //获取子元素的布局参数
                    CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();

                    //测量子元素并考虑外边距
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                    //计算父容器的期望值
                    parentDesireWidth += child.getMeasuredWidth() + clp.leftMargin + clp.rightMargin;
                    parentDesireHeight += child.getMeasuredHeight() + clp.topMargin + clp.bottomMargin;
                }
            }

            //考虑父容器的内边距
            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();

            //尝试比较建议最小值和期望值的大小并取大值
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());

            //measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
        //设置最终测量值
        setMeasuredDimension(resolveSize(parentDesireWidth, widthMeasureSpec), resolveSize(parentDesireHeight, heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();

        /*如果有子元素*/
        if (getChildCount() > 0) {
            //声明一个临时变量用于存储高度方向位置
            int multiHeight = 0;

            //那么遍历子元素并对其进行定位布局
            for (int i = 0; i < getChildCount(); i++) {

                //获取一个子元素
                View child = getChildAt(i);

                CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();

                //通知子元素进行布局
                child.layout(parentPaddingLeft + clp.leftMargin, multiHeight + parentPaddingTop + clp.topMargin,
                        child.getMeasuredWidth() + parentPaddingLeft + clp.leftMargin,
                        child.getMeasuredHeight() + multiHeight + parentPaddingTop + clp.topMargin);

                //重新确定高度方向位置
                multiHeight += child.getMeasuredHeight() + clp.topMargin + clp.bottomMargin;

            }
        }
    }

    /**
     * 生成默认的布局参数
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 生成布局参数
     * 从属性配置中生成我们的布局参数
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    /**
     * 生成布局参数
     * 将布局参数包装成我们的
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    /**
     * 检查当前布局参数是否是我们定义的类型，这在code声明布局参数时常常用到
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }


    public static class CustomLayoutParams extends MarginLayoutParams{

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}

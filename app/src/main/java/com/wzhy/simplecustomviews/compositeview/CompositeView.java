package com.wzhy.simplecustomviews.compositeview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzhy.simplecustomviews.R;

/**
 * 自定义控件：组合方式
 * Created by Administrator on 2017-11-24 0024.
 */

public class CompositeView extends LinearLayout {

    private ImageView iconView;
    private TextView titleView;


    public CompositeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //inflateFromXml(context);

        initView(context);
    }

    private void initView(Context context) {
        //设置线性排列方式
        setOrientation(VERTICAL);

        //设置对齐方式
        setGravity(Gravity.CENTER);

        //实例化子控件
        iconView = new ImageView(context);
        iconView.setImageResource(R.drawable.img_girl);

        titleView = new TextView(context);
        titleView.setText("wandryoung");
        titleView.setTextSize(48);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);

        //将子元素添加到符合控件
        addView(iconView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(titleView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * 从布局文件中加载
     * @param context
     */
    private void inflateFromXml(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_composite_view, this);
        //inflater.inflate(R.layout.layout_composite_view, this, true);

        //获取控件
        iconView = (ImageView) findViewById(R.id.iv_composite_img);
        titleView = (TextView) findViewById(R.id.tv_composite_title);
    }

    public void setIcon(@DrawableRes int resId) {
        this.iconView.setImageResource(resId);
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    public void setTitle(@StringRes int resId) {
        this.titleView.setText(resId);
    }
}

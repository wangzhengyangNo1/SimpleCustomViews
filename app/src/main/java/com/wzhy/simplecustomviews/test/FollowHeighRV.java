package com.wzhy.simplecustomviews.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017-11-17 0017.
 */

public class FollowHeighRV extends RecyclerView {

    public FollowHeighRV(Context context) {
        super(context);
    }

    public FollowHeighRV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowHeighRV(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }
}

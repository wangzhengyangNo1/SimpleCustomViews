package com.wzhy.simplecustomviews.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.MyApp;

/**
 * Created by Administrator on 2017-9-28 0028.
 */

public class DisplayUtil {


    public static Size getScreenSize(){

        /**
         * 获取屏幕宽高
         */
        WindowManager windowManager = (WindowManager) MyApp.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        //WindowManager windowManager = ((Activity) context).getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return new Size(metrics.widthPixels, metrics.heightPixels);
    }


}

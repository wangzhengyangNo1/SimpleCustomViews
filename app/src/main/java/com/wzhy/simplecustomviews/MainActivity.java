package com.wzhy.simplecustomviews;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzhy.simplecustomviews.measure.ImgView;
import com.wzhy.simplecustomviews.rosediagram.RoseDiagramView;
import com.wzhy.simplecustomviews.studypath.PolylineView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoseDiagramView mRoseDiagramView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

//===============================================================================
//        TextView textView = new TextView(this);
//        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件

//===============================================================================
//        玫瑰图
//        mRoseDiagramView = (RoseDiagramView) findViewById(R.id.rdv);
//        mRoseDiagramView .startDeployAnim();

//===============================================================================
      /*  ImageView mIvCanvasBmp = (ImageView) findViewById(R.id.iv_canvas_bmp);

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.RED);
        mIvCanvasBmp.setImageBitmap(bitmap);*/

//===============================================================================

        /*//折线图
        PolylineView mPv = (PolylineView) findViewById(R.id.pv);

        List<PointF> pointFs = new ArrayList<PointF>();
        pointFs.add(new PointF(0.3F, 0.5F));
        pointFs.add(new PointF(1F, 2.7F));
        pointFs.add(new PointF(2F, 3.5F));
        pointFs.add(new PointF(3F, 3.2F));
        pointFs.add(new PointF(4F, 1.8F));
        pointFs.add(new PointF(5F, 1.5F));
        pointFs.add(new PointF(6F, 2.2F));
        pointFs.add(new PointF(7F, 5.5F));
        pointFs.add(new PointF(8F, 7F));
        pointFs.add(new PointF(8.6F, 5.7F));

        mPv.setData(pointFs, "Money", "Time");*/
//===============================================================================

        //measure         ImgView
      /*  ImgView imgView = (ImgView) findViewById(R.id.img_view);
        imgView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_girl));*/

//===============================================================================

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRoseDiagramView.recycle();
    }
}

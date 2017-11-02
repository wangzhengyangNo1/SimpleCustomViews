package com.wzhy.simplecustomviews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzhy.simplecustomviews.rosediagram.RoseDiagramView;

public class MainActivity extends AppCompatActivity {

    private RoseDiagramView mRoseDiagramView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

//        TextView textView = new TextView(this);
//        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件

//        玫瑰图
//        mRoseDiagramView = (RoseDiagramView) findViewById(R.id.rdv);
//        mRoseDiagramView .startDeployAnim();

      /*  ImageView mIvCanvasBmp = (ImageView) findViewById(R.id.iv_canvas_bmp);

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.RED);
        mIvCanvasBmp.setImageBitmap(bitmap);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRoseDiagramView.recycle();
    }
}

package com.jay.kotlin.customerview.demo;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.jay.kotlin.customerview.R;


public class PorterDuffActivity extends Activity {
    private int screenWidth = 0;
    private int screenHeight = 0;
    private PorterDuff.Mode porterduffmodel = null;
    private ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_porter_duff);

        //initial porter-duff mode is Src
        porterduffmodel = PorterDuff.Mode.SRC;

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        //width of screen
        screenWidth = point.x;
        //height of screen
        screenHeight = point.y;


        imageView = ((ImageView) findViewById(R.id.image));
        setXfermodeImage(imageView);

        //choose src
        findViewById(R.id.radio0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SRC;
                setXfermodeImage(imageView);
            }
        });

        //choose Dst
        findViewById(R.id.radio1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DST;
                setXfermodeImage(imageView);
            }
        });

        //choose SrcOver
        findViewById(R.id.radio2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SRC_OVER;
                setXfermodeImage(imageView);
            }
        });

        //choose DstOver
        findViewById(R.id.radio3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DST_OVER;
                setXfermodeImage(imageView);
            }
        });

        //choose SrcIn
        findViewById(R.id.radio4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SRC_IN;
                setXfermodeImage(imageView);
            }
        });

        //choose DstIn
        findViewById(R.id.radio5).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DST_IN;
                setXfermodeImage(imageView);
            }
        });

        //choose SrcOut
        findViewById(R.id.radio6).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SRC_OUT;
                setXfermodeImage(imageView);
            }
        });

        //choose DstOut
        findViewById(R.id.radio7).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DST_OUT;
                setXfermodeImage(imageView);
            }
        });

        //choose SrcATop
        findViewById(R.id.radio8).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SRC_ATOP;
                setXfermodeImage(imageView);
            }
        });

        //choose DstATop
        findViewById(R.id.radio9).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DST_ATOP;
                setXfermodeImage(imageView);
            }
        });

        //choose Xor
        findViewById(R.id.radio10).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.XOR;
                setXfermodeImage(imageView);
            }
        });

        //choose Darken
        findViewById(R.id.radio11).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.DARKEN;
                setXfermodeImage(imageView);
            }
        });

        //choose lighten
        findViewById(R.id.radio12).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.LIGHTEN;
                setXfermodeImage(imageView);
            }
        });

        //choose multiply
        findViewById(R.id.radio13).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.MULTIPLY;
                setXfermodeImage(imageView);
            }
        });

        //choose screen
        findViewById(R.id.radio14).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.SCREEN;
                setXfermodeImage(imageView);
            }
        });

        //clear
        findViewById(R.id.radio15).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                porterduffmodel = PorterDuff.Mode.CLEAR;
                setXfermodeImage(imageView);
            }
        });
    }

    private void setXfermodeImage(ImageView imageView) {
        //open hardware accelerate rendering, it's software rendering default.
        imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        Bitmap separate_bitmap = Bitmap.createBitmap(screenWidth, screenHeight / 2, Bitmap.Config.ARGB_8888);
        Canvas separate_canvas = new Canvas(separate_bitmap);


        Bitmap destination_bitmap = Bitmap.createBitmap(screenWidth, screenHeight / 2, Bitmap.Config.ARGB_8888);
        //create destination canvas
        Canvas destination_canvas = new Canvas(destination_bitmap);
        Paint destination_paint = new Paint();
        destination_paint.setColor(Color.YELLOW);
        destination_paint.setStyle(Paint.Style.FILL);
        //drawing destination circle to destination canvas
        destination_canvas.drawCircle(screenWidth / 2, screenHeight / 4, screenHeight / 8, destination_paint);


        Bitmap source_bitmap = Bitmap.createBitmap(screenWidth, screenHeight / 2, Bitmap.Config.ARGB_8888);
        //create source canvas
        Canvas source_canvas = new Canvas(source_bitmap);
        Paint source_paint = new Paint();
        source_paint.setColor(Color.BLUE);
        source_paint.setStyle(Paint.Style.FILL);
        //drawing source rectangle to source canvas
        source_canvas.drawRect(screenWidth / 8, screenHeight / 4, screenWidth / 2, 7 * screenHeight / 16, source_paint);


        Paint separate_paint = new Paint();
        separate_canvas.drawBitmap(destination_bitmap, 0, 0, separate_paint);
        separate_paint.setXfermode(new PorterDuffXfermode(porterduffmodel));
        separate_canvas.drawBitmap(source_bitmap, 0, 0, separate_paint);

        //set separate_bitmap to target view
        imageView.setImageBitmap(separate_bitmap);

        //retrieve layer type to software layer
        imageView.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null);
    }
}
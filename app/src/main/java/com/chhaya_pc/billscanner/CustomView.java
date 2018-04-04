package com.example.chhaya_pc.billscanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.View;



public class CustomView extends View {
    private Rect rectangle;
    private Paint paint;

    public CustomView(Context context,Rect rect) {
        super(context);
        rectangle=rect;
        paint=new Paint();


    }
        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawRect(rectangle, paint);
        }

}

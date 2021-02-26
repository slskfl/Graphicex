package com.example.graphices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGraphicView myGraphic=new MyGraphicView(this);
        setContentView(new MyGraphicView(this));
    }
    private static class MyGraphicView extends View{
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            Bitmap picture= BitmapFactory.decodeResource(getResources(), R.drawable.nina);
            int x=(this.getWidth()-picture.getWidth())/2;
            int y=(this.getHeight()-picture.getHeight())/2;
            int cenX=this.getWidth()/2; //배경 자체를 회전
            int cenY=this.getHeight()/2;
//            canvas.rotate(45, cenX, cenY);
//            canvas.scale(2,2,cenX,cenY);
//            canvas.translate(-150, 200);
//            canvas.skew(0.3f, 0.3f); //기울기
//            BlurMaskFilter bMask=new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
//            paint.setMaskFilter(bMask);
            float[] array= {2,0,0,0,-25,
                            0,2,0,0,-25,
                            0,0,2,0,-25,
                            0,0,0,1,0};// 마지막 숫자가 밝기 (음수값은 밝아짐)
            ColorMatrix cm=new ColorMatrix(array);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(picture, x, y, paint);
            picture.recycle();
        }
    }
}
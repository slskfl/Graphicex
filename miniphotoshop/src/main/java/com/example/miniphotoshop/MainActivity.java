package com.example.miniphotoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton ibZoomIn, ibZoomOut, ibRotate, ibBright, ibDark, ibGray, ibBlur;
    LinearLayout Lilayout;
    MyGraphiceView graphiceView;
    static float scaleX=1, scaleY=1, rotate=0, color=1, satur=1;
    static boolean blur=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibZoomIn=findViewById(R.id.ibZoomIn);
        ibZoomOut=findViewById(R.id.ibZoomOut);
        ibRotate=findViewById(R.id.ibRotate);
        ibBright=findViewById(R.id.ibBright);
        ibDark=findViewById(R.id.ibDark);
        ibGray=findViewById(R.id.ibGray);
        ibBlur=findViewById(R.id.ibBlur);
        Lilayout=findViewById(R.id.Lilayout);
        graphiceView=new MyGraphiceView(this);
        Lilayout.addView(graphiceView);
        registerForContextMenu(graphiceView);

        ibZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX+=0.1f;
                scaleY+=0.1f;
                graphiceView.invalidate(); //onDraw()를 자동으로 호출
            }
        });
        ibZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scaleX==0 || scaleY==0){
                    scaleX=1;
                    scaleY=1;
                } else{
                    scaleX-=0.1f;
                    scaleY-=0.1f;
                }
                graphiceView.invalidate();
            }
        });
        ibRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate+=5;
                graphiceView.invalidate();
            }
        });
        ibBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color+=0.1f;
                graphiceView.invalidate();
            }
        });
        ibDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color-=0.1f;
                graphiceView.invalidate();
            }
        });
        ibGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satur==1){
                    satur=0;
                } else{
                    satur=1;
                }
                graphiceView.invalidate();
            }
        });
        ibBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blur==true){
                    blur=false;
                } else{
                    blur=true;
                }
                graphiceView.invalidate();
            }
        });
        /*Lilayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("원본 이미지");
                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scaleX=1;
                        scaleY=1;
                        rotate=0;
                        color=1;
                        satur=1;
                        blur=false;
                        graphiceView.invalidate();
                    }
                });
                builder.setPositiveButton("취소", null);
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v==graphiceView){
            menu.add(0,1,1,"원래대로");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                scaleX=1;
                scaleY=1;
                rotate=0;
                color=1;
                satur=1;
                blur=false;
                graphiceView.invalidate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //그래픽 클래스
    private static class MyGraphiceView extends View{
        public MyGraphiceView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            Bitmap picture= BitmapFactory.decodeResource(getResources(), R.drawable.nina);
            int x=(this.getWidth()-picture.getWidth())/2;
            int y=(this.getHeight()-picture.getHeight())/2;
            int cenX=this.getWidth()/2;
            int cenY=this.getHeight()/2;
            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(rotate, cenX, cenY);
            float[] array= {color,0,0,0,0,
                            0,color,0,0,0,
                            0,0,color,0,0,
                            0,0,0,color,0};
            ColorMatrix cm=new ColorMatrix(array);
            if(satur==0){
                cm.setSaturation(satur);
            }
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            if(blur==true){
                BlurMaskFilter bMask=new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
                paint.setMaskFilter(bMask);
            }
            canvas.drawBitmap(picture, x, y, paint);
            picture.recycle();
        }
    }
}
package com.example.a1111.yetingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by a1111 on 2017/11/11.
 */

public class YuanImageview extends ImageView {

//边框加颜色
    private  int co;
    private  int borderwidth;


    private Paint paint ;

    public YuanImageview(Context context) {
        this(context,null);
    }

    public YuanImageview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public YuanImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

    }
    //设置颜色
    public  void  setColour(int colour){

      co = colour;
    }
    //设置边框宽度
    public  void  setBorderwidth(int width){

        borderwidth = width;
    }

    /**
     * 绘制圆形图片
     * @author caizhiming
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap, 14);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);

//            Rect rec = canvas.getClipBounds();
//            rec.bottom--;
//            rec.right--;
//
//            paint.setColor(co);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(borderwidth);
//
//            canvas.drawRect(rec,paint);




        } else {
            super.onDraw(canvas);

        }
    }

    /**
     * 获取圆形图片方法
     * @param bitmap
     * @param pixels
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

//        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);



        int x = bitmap.getWidth();

        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
//        canvas.drawRect(rec,paint);
        return output;


    }
}



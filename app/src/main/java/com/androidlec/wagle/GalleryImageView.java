package com.androidlec.wagle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.Log;


public class GalleryImageView extends androidx.appcompat.widget.AppCompatImageView {


    public GalleryImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth();
        int h = getHeight();


        Bitmap roundBitmap = getRectArcCroppedBitmap(bitmap, w, h);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }


    public static Bitmap getRectArcCroppedBitmap(Bitmap bitmap, int width, int height) {

        //이미지와 같은 크기의 비트맵 생성
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //빈 비트맵을 캔버스 재료로 사용
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);


        paint.setColor(Color.parseColor("#5a5a5a"));
        canvas.drawRoundRect(rectF, 300, 300, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);



        return output;
    }


}

package com.androidlec.wagle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


public class GalleryImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float radius = 100.0f;
    private Path path;
    private RectF rect;


    public GalleryImageView(Context context) {
        super(context);
        init();
    }

    public GalleryImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0,0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }



}

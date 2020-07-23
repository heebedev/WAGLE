package com.androidlec.wagle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;


public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float radius;
    private Path path;
    private RectF rect;


    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.getWidth() < this.getHeight()) {
            radius = this.getWidth();
        } else {
            radius = this.getHeight();
        }
        rect = new RectF();
        path.addCircle(radius / 2 + 0.7f, radius / 2 + 0.7f, radius / 2 + 0.1f, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }

}

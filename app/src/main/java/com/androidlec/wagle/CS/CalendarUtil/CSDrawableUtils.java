package com.androidlec.wagle.CS.CalendarUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.androidlec.wagle.R;
import com.applandeo.materialcalendarview.CalendarUtils;
import com.bumptech.glide.load.engine.Resource;

/**
 * Created by Mateusz Kornakiewicz on 02.08.2018.
 */

public final class CSDrawableUtils {

    private CSDrawableUtils() {
    }

//    public static Drawable getCircleDrawableWithText(Context context, String string) {
//        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
//        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);
//
//        Drawable[] layers = {background, text};
//        return new LayerDrawable(layers);
//    }

    public static Drawable getCircleDrawableWithTextDue(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.cs_circle_due);
        Drawable text = CalendarUtils.getDrawableText(context, "D", null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    public static Drawable getCircleDrawableWithTextStart(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.cs_circle_start);
        Drawable text = CalendarUtils.getDrawableText(context, "S", null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    public static Drawable getCircleDrawableWithTextEnd(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.cs_circle_end);
        Drawable text = CalendarUtils.getDrawableText(context, "E", null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

//    public static Drawable getThreeDots(Context context){
//        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);
//
//        //Add padding to too large icon
//        return new InsetDrawable(drawable, 100, 0, 100, 0);

//    }
}

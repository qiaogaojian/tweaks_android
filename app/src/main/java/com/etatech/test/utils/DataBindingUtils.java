package com.etatech.test.utils;

import android.content.Context;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.ArrayMap;

/**
 * Created by Michael
 * Data: 2020/1/15 10:40
 * Desc: DataBinding工具类
 */
public class DataBindingUtils {
    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    // 根据参数类型和返回值类型进行适配
    @BindingConversion
    public static Typeface convertStringTotypeFace(String fontName) {
        try {
            return getTypeface(fontName, App.getInstance());
        } catch (Exception e) {
            throw e;
        }
    }

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface tf = fontCache.get(fontName);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(fontName, tf);
        }
        return tf;
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}

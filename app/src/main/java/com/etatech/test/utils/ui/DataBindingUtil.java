package com.etatech.test.utils.ui;

import android.content.Context;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.ArrayMap;

import com.etatech.test.utils.App;

/**
 * Created by Michael
 * Data: 2020/1/15 10:40
 * Desc: DataBinding工具类
 */
public class DataBindingUtil {
    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    /**
     * 根据参数类型和返回值类型进行适配
     * String 转 Typeface
     *
     * @param fontPath 字体文件路径
     * @return
     */
    @BindingConversion
    public static Typeface convertStringTotypeFace(String fontPath) {
        try {
            return getTypeface(fontPath, App.getInstance());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据路径创建获取字体资源
     *
     * @param fontPath
     * @param context
     * @return
     */
    public static Typeface getTypeface(String fontPath, Context context) {
        Typeface tf = fontCache.get(fontPath);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(fontPath, tf);
        }
        return tf;
    }

    /**
     * 颜色转Drawable
     *
     * @param color 颜色资源id
     * @return
     */
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}

package com.etatech.test.utils;

import android.graphics.drawable.Drawable;
import android.text.Html;

import androidx.core.content.ContextCompat;

/**
 * Created by Michael
 * Date:  2021/1/19
 * Desc:
 */
public class MyImageGetter implements Html.ImageGetter {

    @Override
    public Drawable getDrawable(String source) {
        // 获取本地图片
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(App.getInstance().getApplicationContext(), Integer.parseInt(source));
            // 必须设为图片的边际,不然TextView显示不出图片
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            // 将其返回
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
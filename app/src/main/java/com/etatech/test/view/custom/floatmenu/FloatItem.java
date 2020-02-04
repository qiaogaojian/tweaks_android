package com.etatech.test.view.custom.floatmenu;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Michael
 * Date:  2020/2/3
 * Func:
 */
public class FloatItem {
    public String title;
    public Bitmap icon;

    public FloatItem(String title, Bitmap icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) return true;

        if (obj instanceof FloatItem) {
            FloatItem floatItem = (FloatItem) obj;
            return floatItem.title.equals(this.title);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "FloatItem{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}

package com.etatech.test.widget.wallpaper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.etatech.test.utils.Tools;

import static java.lang.Math.min;

public class SettingsPref {
    public static final String FIRST_RUN = "first_run";
    public static final String LINE_COLOR = "lineColor";
    public static final String PARTICLE_COLOR = "particleColor";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String PARTICLE_COUNT = "particle_count";
    public static final String LINE_LENGTH = "line_length";
    public static final String PARTICLE_VELOCITY = "particle_velocity";
    public static final String PARTICLE_SIZE = "particle_size";
    public static final String LINE_THICK = "line_thick";
    public static final String TOUCH_EFFECT = "touch_effect";
    public static final String BACKGROUND_IMAGE_PATH = "background_image_path";

    SettingsPref() {
        if (Tools.getShare().getBoolean(FIRST_RUN, true)) {
            int lineLength = min(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
            lineLength = lineLength / 2 - 50;
            Tools.getShare().edit().putBoolean(FIRST_RUN, false);
            Tools.getShare().edit().putInt(LINE_LENGTH, lineLength);
            Tools.getShare().edit().commit();
        }
    }

    public static void setParticleCount(int x) {
        Tools.getShare().edit().putInt(PARTICLE_COUNT, x);
        Tools.getShare().edit().commit();
    }

    public static int getParticleCount() {
        return Tools.getShare().getInt(PARTICLE_COUNT, 30);
    }

    public static void setParticleVelocity(int x) {
        Tools.getShare().edit().putInt(PARTICLE_VELOCITY, x);
        Tools.getShare().edit().commit();
    }

    public static int getParticleVelocity() {
        return Tools.getShare().getInt(PARTICLE_VELOCITY, 300);
    }

    public static void setBackgroundImagePath(String x) {
        Tools.getShare().edit().putString(BACKGROUND_IMAGE_PATH, x);
        Tools.getShare().edit().commit();
    }

    public static String getBackgroundImagePath() {
        return Tools.getShare().getString(BACKGROUND_IMAGE_PATH, "0");
    }

    public static void setParticleColor(int x) {
        String particleColor = String.format("#%06X", (0xFFFFFF & x));
        Tools.getShare().edit().putString(PARTICLE_COLOR, particleColor);
        Tools.getShare().edit().commit();
    }

    public static String getParticleColorStr() {
        return Tools.getShare().getString(PARTICLE_COLOR, "#65D9EF");
    }

    public static int getParticleColor() {
        String particleColor = Tools.getShare().getString(PARTICLE_COLOR, "#65D9EF");
        return Tools.getColorInt(particleColor);
    }

    public static void setBackgroundColor(int x) {
        String backgroundColor = String.format("#%06X", (0xFFFFFF & x));
        Tools.getShare().edit().putString(BACKGROUND_COLOR, backgroundColor);
        Tools.getShare().edit().commit();
    }

    public static String getBackgroundColorStr() {
        return Tools.getShare().getString(BACKGROUND_COLOR, "#46A7F5FF");
    }

    public static int getBackgroundColor() {
        String backgroundColor = Tools.getShare().getString(BACKGROUND_COLOR, "#46A7F5FF");
        return Tools.getColorInt(backgroundColor);
    }

    public static void setParticleSize(int x) {

        Tools.getShare().edit().putInt(PARTICLE_SIZE, x);
        Tools.getShare().edit().commit();
    }

    public static int getParticleSize() {
        return Tools.getShare().getInt(PARTICLE_SIZE, 5);
    }

    public static void setLineColor(int x) {
        String lineColor = String.format("#%06X", (0xFFFFFF & x));
        Tools.getShare().edit().putString(LINE_COLOR, lineColor);
        Tools.getShare().edit().commit();
    }

    public static String getLineColorStr() {
        return Tools.getShare().getString(LINE_COLOR, "#65D9EF");
    }

    public static int getLineColor() {
        String lineColor = Tools.getShare().getString(LINE_COLOR, "#65D9EF");
        return Tools.getColorInt(lineColor);
    }

    public static void setTouchEffect(int x) {
        Tools.getShare().edit().putInt(TOUCH_EFFECT, x);
        Tools.getShare().edit().commit();
    }

    public static int getTouchEffect() {
        return Tools.getShare().getInt(TOUCH_EFFECT, 1);
    }

    public static void setLineLength(int x) {
        Tools.getShare().edit().putInt(LINE_LENGTH, x);
        Tools.getShare().edit().commit();
    }

    public static int getLineLength() {
        return Tools.getShare().getInt(LINE_LENGTH, 300);
    }

    public static void setLineThickness(int x) {
        Tools.getShare().edit().putInt(LINE_THICK, x);
        Tools.getShare().edit().commit();
    }

    public static int getLineThickness() {
        return Tools.getShare().getInt(LINE_THICK, 2);
    }
}

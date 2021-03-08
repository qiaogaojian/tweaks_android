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

    public String particleColor = "#65D9EF";
    public String lineColor = "#65D9EF";
    public int particleSize = 5;
    public int particleCount = 30;
    public int particleVelocity = 300;
    public String backgroundImagePath = "0";
    public String backgroundColor = "#33AE81FF";
    public int touchEffect = 1;
    public int lineLength = 300;
    public int lineThickness = 2;

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    SettingsPref() {
        pref = Tools.getShare();
        editor = pref.edit();

        if (pref.getBoolean(FIRST_RUN, true)) {
            lineLength = min(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
            lineLength = lineLength / 2 - 50;
            editor.putBoolean(FIRST_RUN, false);
            editor.putInt(LINE_LENGTH, lineLength);
            editor.commit();
        }

        getParticleColor();
        getLineColor();
        getParticleSize();
        getParticleCount();
        getParticleVelocity();
        getBackgroundImagePath();
        getBackgroundColor();
        getTouchEffect();
        getLineLength();
        getLineThickness();
    }

    public static void setParticleCount(int x) {
        editor.putInt(PARTICLE_COUNT, x);
        editor.commit();
    }

    public static int getParticleCount() {
        return pref.getInt(PARTICLE_COUNT, 30);
    }

    public static void setParticleVelocity(int x) {
        editor.putInt(PARTICLE_VELOCITY, x);
        editor.commit();
    }

    public static int getParticleVelocity() {
        return pref.getInt(PARTICLE_VELOCITY, 300);
    }

    public static void setBackgroundImagePath(String x) {
        editor.putString(BACKGROUND_IMAGE_PATH, x);
        editor.commit();
    }

    public static String getBackgroundImagePath() {
        return pref.getString(BACKGROUND_IMAGE_PATH, "0");
    }

    public static void setParticleColor(int x) {
        String particleColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(PARTICLE_COLOR, particleColor);
        editor.commit();
    }

    public static String getParticleColorStr() {
        return pref.getString(PARTICLE_COLOR, "#65D9EF");
    }

    public static int getParticleColor() {
        String particleColor = pref.getString(PARTICLE_COLOR, "#65D9EF");
        return Tools.getColorInt(particleColor);
    }

    public static void setBackgroundColor(int x) {
        String backgroundColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(BACKGROUND_COLOR, backgroundColor);
        editor.commit();
    }

    public static String getBackgroundColorStr() {
        return pref.getString(BACKGROUND_COLOR, "#46A7F5FF");
    }

    public static int getBackgroundColor() {
        String backgroundColor = pref.getString(BACKGROUND_COLOR, "#46A7F5FF");
        return Tools.getColorInt(backgroundColor);
    }

    public static void setParticleSize(int x) {

        editor.putInt(PARTICLE_SIZE, x);
        editor.commit();
    }

    public static int getParticleSize() {
        return pref.getInt(PARTICLE_SIZE, 5);
    }

    public static void setLineColor(int x) {
        String lineColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(LINE_COLOR, lineColor);
        editor.commit();
    }

    public static String getLineColorStr() {
        return pref.getString(LINE_COLOR, "#65D9EF");
    }

    public static int getLineColor() {
        String lineColor = pref.getString(LINE_COLOR, "#65D9EF");
        return Tools.getColorInt(lineColor);
    }

    public static void setTouchEffect(int x) {
        editor.putInt(TOUCH_EFFECT, x);
        editor.commit();
    }

    public static int getTouchEffect() {
        return pref.getInt(TOUCH_EFFECT, 1);
    }

    public static void setLineLength(int x) {
        editor.putInt(LINE_LENGTH, x);
        editor.commit();
    }

    public static int getLineLength() {
        return pref.getInt(LINE_LENGTH, 300);
    }

    public static void setLineThickness(int x) {
        editor.putInt(LINE_THICK, x);
        editor.commit();
    }

    public static int getLineThickness() {
        return pref.getInt(LINE_THICK, 2);
    }
}

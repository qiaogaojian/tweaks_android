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

    public static String particleColor = "#65D9EF";
    public static String lineColor = "#65D9EF";
    public static int particleSize = 5;
    public static int particleCount = 30;
    public static int particleVelocity = 300;
    public static String backgroundImagePath = "0";
    public static String backgroundColor = "#33AE81FF";
    public static int touchEffect = 1;
    public static int lineLength = 300;
    public static int lineThickness = 2;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

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

    public void setParticleCount(int x) {

        editor.putInt(PARTICLE_COUNT, x);
        editor.commit();
        particleCount = x;
    }

    public int getParticleCount() {
        particleCount = pref.getInt(PARTICLE_COUNT, 30);
        return particleCount;
    }

    public void setParticleVelocity(int x) {
        editor.putInt(PARTICLE_VELOCITY, x);
        editor.commit();
        particleVelocity = x;
    }

    public int getParticleVelocity() {
        particleVelocity = pref.getInt(PARTICLE_VELOCITY, 300);
        return particleVelocity;
    }

    public void setBackgroundImagePath(String x) {
        editor.putString(BACKGROUND_IMAGE_PATH, x);
        editor.commit();
        backgroundImagePath = x;
    }

    public String getBackgroundImagePath() {
        backgroundImagePath = pref.getString(BACKGROUND_IMAGE_PATH, "0");
        return backgroundImagePath;
    }

    public void setParticleColor(int x) {
        particleColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(PARTICLE_COLOR, particleColor);
        editor.commit();
    }

    public int getParticleColor() {
        particleColor = pref.getString(PARTICLE_COLOR, "#65D9EF");
        return Tools.getColorInt(particleColor);
    }

    void setBackgroundColor(int x) {
        backgroundColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(BACKGROUND_COLOR, backgroundColor);
        editor.commit();
    }

    public int getBackgroundColor() {
        backgroundColor = pref.getString(BACKGROUND_COLOR, "#46A7F5FF");
        return Tools.getColorInt(backgroundColor);
    }

    public void setParticleSize(int x) {

        editor.putInt(PARTICLE_SIZE, x);
        editor.commit();
        particleSize = x;
    }

    public int getParticleSize() {
        particleSize = pref.getInt(PARTICLE_SIZE, 5);
        return particleSize;
    }

    public void setLineColor(int x) {
        lineColor = String.format("#%06X", (0xFFFFFF & x));
        editor.putString(LINE_COLOR, lineColor);
        editor.commit();
    }

    public int getLineColor() {
        lineColor = pref.getString(LINE_COLOR, "#65D9EF");
        return Tools.getColorInt(lineColor);
    }

    public void setTouchEffect(int x) {

        editor.putInt(TOUCH_EFFECT, x);
        editor.commit();
        touchEffect = x;
    }

    public int getTouchEffect() {
        touchEffect = pref.getInt(TOUCH_EFFECT, 1);
        return touchEffect;
    }

    public void setLineLength(int x) {

        editor.putInt(LINE_LENGTH, x);
        editor.commit();
        lineLength = x;
    }

    public int getLineLength() {
        lineLength = pref.getInt(LINE_LENGTH, 300);
        return lineLength;
    }

    public void setLineThickness(int x) {

        editor.putInt(LINE_THICK, x);
        editor.commit();
        lineThickness = x;
    }

    public int getLineThickness() {
        lineThickness = pref.getInt(LINE_THICK, 2);
        return lineThickness;
    }
}

package com.etatech.test.spine;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.spine.SpineBaseAdapter;
import com.etatech.test.widget.wallpaper.Particle;
import com.etatech.test.widget.wallpaper.SettingsPref;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/5
 * Desc:
 */
public class RaptorAdapter extends SpineBaseAdapter {
    private SpriteBatch batcher;
    private Sprite sprite;
    private int max_height;
    private int max_width;
    private Vector2 clickPos;
    private ShapeRenderer sr;
    private int touchCount;

    /**
     * 设置动画文件资源路径
     */
    @Override
    public void onInit() {
        setAssetsPath("raptor3.8/raptor.atlas", "raptor3.8/raptor.skel");
        setDebug(false);
        setPadding(AdaptScreenUtils.pt2Px(100));
    }

    /**
     * 设置动画初始状态
     */
    @Override
    public void onCreated() {
        animate("walk");
        initImage();
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDrawBg() {
        super.onDrawBg();

        batcher.begin();
        batcher.draw(sprite, (max_width - sprite.getWidth()) / 2, (max_height - sprite.getHeight()) / 2, sprite.getWidth(), sprite.getHeight());
        batcher.end();

    }

    private void initImage() {
        Texture textureBg;
        if (SettingsPref.getBackgroundImagePath().equals("0")) {
            textureBg = new Texture("bg.png");
            batcher.setColor(Color.valueOf(SettingsPref.getBackgroundColorStr()));
        } else {
            batcher = new SpriteBatch();
            if (Gdx.files.absolute(SettingsPref.getBackgroundImagePath()).exists()) {
                textureBg = new Texture(Gdx.files.absolute(SettingsPref.getBackgroundImagePath()));
            } else {
                textureBg = new Texture("bg.png");
                batcher.setColor(Color.valueOf(SettingsPref.getBackgroundColorStr()));
            }
        }

        float scale_factor;
        max_height = Gdx.graphics.getHeight();
        max_width = Gdx.graphics.getWidth();
        sprite = new Sprite(textureBg);
        scale_factor = max(max_height / sprite.getHeight(), max_width / sprite.getWidth());
        sprite.setSize(sprite.getWidth() * scale_factor, sprite.getHeight() * scale_factor);
        sprite.scale(scale_factor);
        Log.d("Sprite size:", sprite.getHeight() + " " + sprite.getWidth());

        clickPos = new Vector2();
        sr = new ShapeRenderer();
    }

    @Override
    public void onDrawFg() {
        super.onDrawFg();
        if (Gdx.input.isTouched()) {
            touchCount++;
            if (touchCount % 2 == 1) {
                animate("roar", "walk");
            } else {
                animate("jump", "walk");
            }

            clickPos.set(Gdx.input.getX(), abs(Gdx.input.getY() - max_height));
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(clickPos.x, clickPos.y, SettingsPref.getParticleSize());
            sr.end();
        }
    }
}

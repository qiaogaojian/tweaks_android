package com.etatech.test.spine;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.etatech.spine.SpineBaseAdapter;
import com.etatech.test.R;
import com.etatech.test.utils.App;
import com.etatech.test.utils.SoundManager;
import com.etatech.test.widget.wallpaper.SettingsPref;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/5
 * Desc:
 */
public class RaptorAdapter extends SpineBaseAdapter {
    private SpriteBatch batcher;
    private SpriteBatch batch;
    private Sprite sprite;
    private int max_height;
    private int max_width;
    private Vector2 clickPos;
    private ShapeRenderer mShapeRenderer;
    private int touchCount;
    private boolean gyroscopeAvail;
    private BitmapFont font;

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
    public void onClick(BoundingBoxAttachment attachment) {
        if (attachment != null) {
            System.out.println("attachment:" + attachment.getName());
        }
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
        batcher = new SpriteBatch();
        if (SettingsPref.getBackgroundImagePath().equals("0")) {
            textureBg = new Texture("bg.png");
            batcher.setColor(Color.valueOf(SettingsPref.getBackgroundColorStr()));
        } else {
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
        mShapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/mono.fnt"), false);
        batch = new SpriteBatch();
    }

    @Override
    public void onDrawFg() {
        super.onDrawFg();
        if (Gdx.input.isTouched()) {
            touchCount++;
            if (touchCount % 3 == 1) {
                animate("roar", "walk");
                SoundManager.getInstance().playSingleAsset("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.roar);
            } else {
                animate("jump", "walk");
            }

            clickPos.set(Gdx.input.getX(), abs(Gdx.input.getY() - max_height));
            mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            mShapeRenderer.circle(clickPos.x, clickPos.y, SettingsPref.getParticleSize());
            mShapeRenderer.end();
        }


        smoothAcceler(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());

        setXoffset(accelValues[0] * AdaptScreenUtils.pt2Px(30));
        setYoffset(accelValues[1] * AdaptScreenUtils.pt2Px(30));
        batch.setProjectionMatrix(mCamera.combined); //or your matrix to draw GAME WORLD, not UI
        batch.begin();
        String s = String.format("Accelerometer: X %.2f Y %.2f ", accelValues[0], accelValues[1]);
        font.draw(batch, s, 30, 60);
        batch.end();

    }

    /*
     * time smoothing constant for low-pass filter
     * 0 ≤ α ≤ 1 ; a smaller value basically means more smoothing
     * See: http://en.wikipedia.org/wiki/Low-pass_filter#Discrete-time_realization
     */
    protected static final float ALPHA = 0.2f;

    protected float[] accelValues;

    public void smoothAcceler(float x, float y, float z) {
        float[] values = new float[]{x, y, z};
        accelValues = lowPass(values, accelValues);
    }

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) return input;

        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
}

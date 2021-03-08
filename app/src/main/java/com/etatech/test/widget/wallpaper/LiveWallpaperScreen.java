package com.etatech.test.widget.wallpaper;

import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import javax.microedition.khronos.opengles.GL10;

import static com.badlogic.gdx.Gdx.gl;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LiveWallpaperScreen implements Screen {
    Game game;
    FPSLogger logger;

    static OrthographicCamera camera;
    static Texture textureBg;
    static SpriteBatch batcher;
    static World world;
    static ShapeRenderer sr, sr2;
    static int particleCount;
    static float dist;
    static float scale_factor;
    static float particleVelocity;
    static int particleSize;
    static int touchEffect;
    static int lineThickness;
    static int lineLength;
    static Vector2 pos2;

    static Color particleColor;
    static Color lineColor;
    static Sprite sprite;
    static Particle particle[];

    //Resolution of device
    static int max_height = Gdx.graphics.getHeight();
    static int max_width = Gdx.graphics.getWidth();

    public LiveWallpaperScreen(final Game game) {
        this.game = game;

        particleCount = SettingsPref.getParticleCount();
        particleVelocity = SettingsPref.getParticleVelocity();
        particleSize = SettingsPref.getParticleSize();
        touchEffect = SettingsPref.getTouchEffect();
        lineThickness = SettingsPref.getLineThickness();
        lineLength = SettingsPref.getLineLength();
        camera = new OrthographicCamera(max_width, max_height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        batcher = new SpriteBatch();


        //////////////////////////new particle system//////////////////////
        particle = new Particle[200];
        for (int i = 0; i < 200; i++) {
            particle[i] = new Particle(max_width, max_height, particleVelocity);
        }

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


        sprite = new Sprite(textureBg);
        scale_factor = max(max_height / sprite.getHeight(), max_width / sprite.getWidth());
        sprite.setSize(sprite.getWidth() * scale_factor, sprite.getHeight() * scale_factor);
        sprite.scale(scale_factor);
        Log.d("Sprite size:", String.valueOf(sprite.getHeight() + " " + String.valueOf(sprite.getWidth())));
        logger = new FPSLogger();


        world = new World(new Vector2(0, 0), true);

        sr = new ShapeRenderer();
        sr2 = new ShapeRenderer();

        particleColor = new Color(Color.valueOf(SettingsPref.getParticleColorStr()));
        lineColor = new Color(Color.valueOf(SettingsPref.getLineColorStr()));
        pos2 = new Vector2();
        Log.d("LWP Created", "True");
    }

    private void draw(float delta) {
        world.step(1f / 360f, 6, 2);
        camera.update();
        gl.glClearColor(particleColor.r, particleColor.g, particleColor.b, particleColor.a);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.begin();
        batcher.draw(sprite, (max_width - sprite.getWidth()) / 2, (max_height - sprite.getHeight()) / 2, sprite.getWidth(), sprite.getHeight());
        batcher.end();

        logger.log();
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        sr.setProjectionMatrix(batcher.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr2.begin(ShapeRenderer.ShapeType.Filled);
        sr2.setColor(particleColor);
        //sr.setColor(lcolor);

        for (int i = 0; i < particleCount; i++) {
            particle[i].update();
            sr2.circle(particle[i].x, particle[i].y, particleSize);
            for (int j = 0; j < particleCount; j++)
                if (Particle.distance(particle[i], particle[j]) < lineLength && i != j) {
                    dist = Particle.distance(particle[i], particle[j]);
                    lineColor.a = max(0, 1 - (dist / (lineLength - 70)));

                    sr.rectLine(particle[i].x, particle[i].y, particle[j].x, particle[j].y, lineThickness, lineColor, lineColor);
                }

            particle[i].check_bounds();

            if (touchEffect == 1 && Gdx.input.isTouched()) {
                // Log.d("is touched",String.valueOf(Gdx.input.getX())+" "+String.valueOf(Gdx.input.getY()));
                // pos2=new Vector2(Gdx.input.getX(),abs(Gdx.input.getY()-max_height));
                pos2.set(Gdx.input.getX(), abs(Gdx.input.getY() - max_height));
                if (Particle.get_pos(particle[i]).dst(pos2) < lineLength + 200) {
                    dist = Particle.get_pos(particle[i]).dst(pos2);
                    lineColor.a = max(0, 1 - (dist / (lineLength + 130)));
                    sr.rectLine(particle[i].x, particle[i].y, pos2.x, pos2.y, lineThickness, lineColor, lineColor);
                }


            }

        }

        sr.end();
        sr2.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {
        Log.d("LWP hidden", "True");
    }

    @Override
    public void pause() {
        Log.d("LWP paused", "True");
    }

    @Override
    public void render(float delta) {
        draw(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
        Log.d("LWP resumed", "True");
        max_height = Gdx.graphics.getHeight();
        max_width = Gdx.graphics.getWidth();
        Particle.max_height = max_height;
        Particle.max_width = max_width;
        /*
        Particle.bound_height_top=-(max_height)/4;
        Particle.bound_width_left=-(max_width)/4;
        Particle.bound_height_bottom=max_height +max_width/4;
        Particle.bound_width_right=max_width+max_width/4;
        */

        particleCount = SettingsPref.getParticleCount();

        Particle.velocity = SettingsPref.getParticleVelocity() / 300;
        particleSize = SettingsPref.getParticleSize();
        touchEffect = SettingsPref.getTouchEffect();
        lineThickness = SettingsPref.getLineThickness();
        lineLength = SettingsPref.getLineLength();
        Log.d("velo", String.valueOf(particleVelocity));
        camera.viewportWidth = max_width;
        camera.viewportHeight = max_height;// = new OrthographicCamera(max_width, max_height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        /*batcher.dispose();
        batcher = new SpriteBatch();
        */
        textureBg.dispose();

        if (SettingsPref.getBackgroundImagePath().equals("0")) {

            textureBg = new Texture("bg.png");
            batcher.setColor(Color.valueOf(SettingsPref.getBackgroundColorStr()));

        } else {
            if (Gdx.files.external(SettingsPref.getBackgroundImagePath()).exists()) {
                textureBg = new Texture(Gdx.files.external(SettingsPref.getBackgroundImagePath()));
            } else {
                textureBg = new Texture("bg.png");
                batcher.setColor(Color.valueOf(SettingsPref.getBackgroundColorStr()));
            }
        }

        sprite.getTexture().dispose();
        sprite.setTexture(textureBg);
        scale_factor = max(max_height / sprite.getHeight(), max_width / sprite.getWidth());
        sprite.setSize(sprite.getWidth() * scale_factor, sprite.getHeight() * scale_factor);
        sprite.scale(scale_factor);
        // Log.d("Sprite size:", String.valueOf(sprite.getHeight()+ " "+String.valueOf(sprite.getWidth())));
        //logger = new FPSLogger();


        //world = new World(new Vector2(0, 0), true);
       /* sr.dispose();
        sr2.dispose();
        sr = new ShapeRenderer();
        sr2=new ShapeRenderer();
        */
        particleColor.set(Color.valueOf(SettingsPref.getParticleColorStr()));
        lineColor.set(Color.valueOf(SettingsPref.getLineColorStr()));//
        // =new Color(Color.valueOf(SettingsPref.line_color));

    }


    @Override
    public void show() {
        // TODO Auto-generated method stub

        Log.d("LWP show", "True");
    }
}

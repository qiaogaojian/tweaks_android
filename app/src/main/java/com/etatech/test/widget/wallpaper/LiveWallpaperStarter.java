package com.etatech.test.widget.wallpaper;

import com.badlogic.gdx.Game;

public class LiveWallpaperStarter extends Game {

    @Override
    public void create() {
        setScreen(new LiveWallpaperScreen(this));
    }
}

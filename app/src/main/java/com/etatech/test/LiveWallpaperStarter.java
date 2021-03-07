package com.etatech.test;

import com.badlogic.gdx.Game;

public class LiveWallpaperStarter extends Game {

    @Override
    public void create() {
        setScreen(new LiveWallpaperScreen(this));
    }
}

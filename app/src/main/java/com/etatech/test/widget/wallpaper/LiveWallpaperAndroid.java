package com.etatech.test.widget.wallpaper;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.etatech.test.spine.RaptorAdapter;
import com.etatech.test.spine.SpineBoyAdapter;
import com.etatech.test.widget.wallpaper.LiveWallpaperStarter;

public class LiveWallpaperAndroid extends AndroidLiveWallpaperService {

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 2;
        config.useCompass = false;
        config.useWakelock = false;
        config.getTouchEventsForLiveWallpaper = true;
        // ApplicationListener listener = new LiveWallpaperStarter();
        ApplicationListener listener = new RaptorAdapter();
        initialize(listener, config);
    }
}
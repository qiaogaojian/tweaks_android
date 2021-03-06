package com.etatech.test.widget.wallpaper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LiveWallpaperAndroid extends Service {
    public LiveWallpaperAndroid() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
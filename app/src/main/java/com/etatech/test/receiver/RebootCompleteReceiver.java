package com.etatech.test.receiver;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.etatech.test.widget.wallpaper.LiveWallpaperAndroid;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/10
 * Desc:
 */
class RebootCompleteReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intents) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        String cls = LiveWallpaperAndroid.class.getCanonicalName();
        i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context.getPackageName(), cls));

        context.startActivity(i);
    }
}
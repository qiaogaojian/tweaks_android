package com.etatech.test;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.etatech.test.utils.App;

/**
 * Created by Michael
 * Date:  2020/7/6
 * Func:
 */
public class ResourcesHelper {

    private static String packageName;
    //    public static void playSplashVideoResource(VideoView videoView) {
    //        int resource = R.raw.splash;
    //        String uri = "android.resource://" + videoView.getContext().getApplicationContext().getPackageName() + "/" + resource;
    //        videoView.setVideoURI(Uri.parse(uri));
    //    }

    public static Uri getObbResourceUri(String fileName) {
        switch (fileName) {
            case "test.mp3":
                return Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
            default:
                Log.e("ResourcesHelper","Cann't find resource");
                return null;
        }
    }

    private static String getPackageName() {
        if (TextUtils.isEmpty(packageName)) {
            packageName = App.getInstance().getPackageName();
        }
        return packageName;
    }
}

package com.sdbean.localize;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;


import java.util.Locale;

/**
 * Created by Michael
 * Date:  2019/8/30
 * Func:
 */
public class LocalManageUtil
{

    private static final String TAG = "LocalManageUtil";

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context)
    {
        return SaveUtil.getInstance(context).getSystemCurrentLocal();
    }

    public static String getSavedLanguage(Context context)
    {
        switch (SaveUtil.getInstance(context).getSelectLanguage())
        {
            case 0:
                return "自动";
            case 1:
                return "中文";
            case 2:
                return "繁體";
            case 3:
                return "ENGLISH";
            case 4:
                return "日本語";
            case 5:
                return "한국어";
            default:
                return "ENGLISH";
        }
    }


    public static Locale getSavedLocale(Context context)
    {

        switch (SaveUtil.getInstance(context).getSelectLanguage())
        {
            case 0:
                return getSystemLocale(context);
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.TAIWAN;
            case 3:
                return Locale.ENGLISH;
            case 4:
                return Locale.JAPAN;
            case 5:
                return Locale.KOREA;
            default:
                return Locale.ENGLISH;
        }
    }


    public static void saveSystemCurrentLanguage(Context context)
    {
        SaveUtil.getInstance(context).setSystemCurrentLocal(MultiLanguage.getSystemLocal(context));
    }

    /**
     * 保存系统语言
     *
     * @param context
     * @param newConfig
     */
    public static void saveSystemCurrentLanguage(Context context, Configuration newConfig)
    {

        SaveUtil.getInstance(context).setSystemCurrentLocal(MultiLanguage.getSystemLocal(newConfig));
    }

    public static void saveSelectLanguage(Context context, int select)
    {
        SaveUtil.getInstance(context).saveLanguage(select);
        MultiLanguage.setApplicationLanguage(context);
    }

    public static Context getLocalContext(Context context)
    {
        return updateResources(context, getSavedLocale(context));
    }

    private static Context updateResources(Context context, Locale locale)
    {
        Locale.setDefault(locale);

        Resources     res    = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17)
        {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else
        {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
}

package com.sdbean.localize;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Michael
 * Date:  2019/8/30
 * Func:
 */
public class MultiLanguage
{
    public static Context setLocal(Context context)
    {
        return updateResources(context, getSetLanguageLocale(context));
    }

    public static void saveSystemCurrentLanguage(Context context)
    {
        LocalManageUtil.saveSystemCurrentLanguage(context);
    }

    public static Context getLocalContext(Context context)
    {
        return LocalManageUtil.getLocalContext(context);
    }

    public static void saveSelectLanguage(Context context, Language select)
    {
        LocalManageUtil.saveSelectLanguage(context, select.ordinal());
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context)
    {
        Resources      resources = context.getApplicationContext().getResources();
        DisplayMetrics dm        = resources.getDisplayMetrics();
        Configuration  config    = resources.getConfiguration();
        Locale         locale    = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }


    /**
     * @param context
     */
    public static void onConfigurationChanged(Context context, Configuration newConfig)
    {
        LocalManageUtil.saveSystemCurrentLanguage(context, newConfig);
        setLocal(context);
        setApplicationLanguage(context);
    }


    /**
     * 获取选择的语言
     *
     * @param context
     * @return
     */
    private static Locale getSetLanguageLocale(Context context)
    {
        return LocalManageUtil.getSavedLocale(context);
    }

    /**
     * 更新语言设置
     *
     * @param context
     * @param locale
     * @return
     */
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

    /**
     * 获取系统语言
     *
     * @param newConfig
     * @return
     */
    public static Locale getSystemLocal(Configuration newConfig)
    {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            locale = newConfig.getLocales().get(0);
        } else
        {
            locale = newConfig.locale;
        }
        return locale;
    }

    /**
     * 获取系统语言
     *
     * @param context
     * @return
     */
    public static Locale getSystemLocal(Context context)
    {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            locale = LocaleList.getDefault().get(0);
        } else
        {
            locale = Locale.getDefault();
        }
        return locale;
    }
}

package com.sdbean.localize;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by Michael
 * Date:  2019/8/30
 * Func:
 */
public class SaveUtil
{
    private final           String   SP_NAME      = "language_setting";
    private final           String   TAG_LANGUAGE = "language_select";
    private static volatile SaveUtil instance;

    private final SharedPreferences mSharedPreferences;

    private Locale systemCurrentLocal = Locale.ENGLISH;


    public SaveUtil(Context context)
    {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public void saveLanguage(int select)
    {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(TAG_LANGUAGE, select);
        edit.commit();
    }

    public int getSelectLanguage()
    {
        return mSharedPreferences.getInt(TAG_LANGUAGE, 0);
    }


    public Locale getSystemCurrentLocal()
    {
        return systemCurrentLocal;
    }

    public void setSystemCurrentLocal(Locale local)
    {
        systemCurrentLocal = local;
    }

    public static SaveUtil getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (SaveUtil.class)
            {
                if (instance == null)
                {
                    instance = new SaveUtil(context);
                }
            }
        }
        return instance;
    }
}

package com.godavari.premiuminvestmentcal.application;

import android.app.Application;
import android.content.Context;

import com.godavari.premiuminvestmentcal.utility.LocaleLanguageHelper;

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleLanguageHelper.onAttach(base, "en"));
    }

    public static Context getContext() {
        return mContext;
    }
}

package com.salehni.salehni.util;

import android.app.Application;
import android.content.Context;

import com.salehni.salehni.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;


public class MyApplication extends Application {

    public static MyApplication sInstance;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        context = getApplicationContext();


        changeDefaultFont();

    }

    public static void changeDefaultFont() {

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/" + Constants.EnglishFont)
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
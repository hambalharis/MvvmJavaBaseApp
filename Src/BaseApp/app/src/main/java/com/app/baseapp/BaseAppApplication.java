package com.app.baseapp;

import android.app.Application;

import com.app.baseapp.dagger.component.ApplicationComponent;
import com.app.baseapp.dagger.component.DaggerApplicationComponent;
import com.app.baseapp.dagger.modules.ApplicationModule;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class BaseAppApplication extends Application {

    private static BaseAppApplication mainApplication;
    private ApplicationComponent mAppComponent;

    public static BaseAppApplication getApp() {

        return mainApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        System.setProperty("http.keepAlive", "false");
        setUpDagger();


//        Fabric will not be added when build type is debug.
//        For Build type -staging & -release, it will be added.

        if (!BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug"))
            Fabric.with(this, new Crashlytics());

    }

    /**
     * Method used to setUp dagger
     */

    private void setUpDagger() {
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    /* *
     * Method used to get DaggerAppComponent instance to get required injection
     *
     * @return AppComponent
     */
    public ApplicationComponent getDaggerAppComponent() {
        return mAppComponent;
    }

}


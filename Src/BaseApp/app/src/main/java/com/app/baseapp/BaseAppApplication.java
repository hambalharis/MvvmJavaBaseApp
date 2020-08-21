package com.app.baseapp;

import android.app.Application;

import com.app.baseapp.dagger.component.ApplicationComponent;
import com.app.baseapp.dagger.component.DaggerApplicationComponent;
import com.app.baseapp.dagger.modules.ApplicationModule;

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


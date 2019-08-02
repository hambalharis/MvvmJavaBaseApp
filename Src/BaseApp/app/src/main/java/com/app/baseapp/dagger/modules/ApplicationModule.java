package com.app.baseapp.dagger.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/*Created by Vinod Kumar (Aug 2019)*/

/*Module class that will provide application class object to inject via Dagger...*/
@Module
public class ApplicationModule {


    public Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application getApplication() {
        return mApplication;
    }

    @Provides
    Context getApplicationContext() {
        return mApplication;
    }

}

package com.app.baseapp.dagger.component;


import com.app.baseapp.dagger.modules.ApiModule;
import com.app.baseapp.dagger.modules.ApplicationModule;
import com.app.baseapp.dagger.modules.NetworkModule;
import com.app.baseapp.dagger.modules.ViewModelModule;
import com.app.baseapp.dagger.scopes.AppScope;

import javax.inject.Singleton;

import dagger.Component;
/*Created by Vinod Kumar (Aug 2019)*/

/*Component class that define all the modules used by Dagger ...*/
@Singleton
@AppScope
@Component(modules = {
        ApplicationModule.class,
        ViewModelModule.class,
        ApiModule.class,
        NetworkModule.class
})
public interface ApplicationComponent extends Injector {
}

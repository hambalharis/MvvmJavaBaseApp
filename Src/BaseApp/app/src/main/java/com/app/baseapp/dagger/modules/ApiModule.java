package com.app.baseapp.dagger.modules;

import com.app.baseapp.dagger.scopes.AppScope;
import com.app.baseapp.networking.ApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
/*Created by Vinod Kumar (Aug 2019)*/

/*Module class that will provide instance of variable that are related to network Api's to inject via Dagger...*/

@Module
public class ApiModule {

    @Provides
    @AppScope
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


}

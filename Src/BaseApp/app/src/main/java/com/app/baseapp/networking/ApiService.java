package com.app.baseapp.networking;

/*Created by Vinod Kumar (Aug 2019)*/

import com.app.baseapp.feature.login_module.login_screen.LoginModel;
import com.app.baseapp.feature.login_module.login_screen.PostSignUpModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/*This is the service interface in which all the method define which will used for data...*/
public interface ApiService {

    @Headers("Authorization: sadas21321")
    @POST("getLogin")
    Observable<LoginModel> login(@Body PostSignUpModel.PostLogiModel model);

    @Headers("Authorization: sadas21321")
    @POST("Registration")
    Observable<LoginModel> signup(@Body PostSignUpModel model);

}

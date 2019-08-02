package com.app.baseapp.feature.login_module.login_screen;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.baseapp.baseui.BaseRepository;
import com.app.baseapp.networking.ApiService;
import com.app.baseapp.networking.BaseNetworkSubscriber;
import com.app.baseapp.networking.Resource;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class LoginRepository extends BaseRepository {
    private ApiService mApiService;
    private Application mApplication;
    private MutableLiveData<Resource<LoginModel>> mFactsLiveDataStatus = new MutableLiveData<>();

    @Inject
    LoginRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void loginAction(LoginModel loginModel) {
        PostSignUpModel.PostLogiModel model = new PostSignUpModel.PostLogiModel();
        model.username = loginModel.emailAddress;
        model.password = loginModel.password;
        model.gsmToken = "abcd";

        mFactsLiveDataStatus.setValue(Resource.loading(null));
        addSubscription(
                mApiService.login(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<LoginModel>(mApplication) {
                            @Override
                            public void onNext(LoginModel factsModels) {
                                super.onNext(factsModels);
                                mFactsLiveDataStatus.setValue(Resource.success(factsModels));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mFactsLiveDataStatus.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public void signUpAction(LoginModel loginModel) {
        PostSignUpModel model = new PostSignUpModel();
        model.email = loginModel.emailAddress;
        model.mobile = loginModel.mobileNumber;
        model.password = loginModel.password;
        model.gsmToken = "abcd";
        model.usertype = "1";

        mFactsLiveDataStatus.setValue(Resource.loading(null));
        addSubscription(
                mApiService.signup(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<LoginModel>(mApplication) {
                            @Override
                            public void onNext(LoginModel factsModels) {
                                super.onNext(factsModels);
                                mFactsLiveDataStatus.setValue(Resource.success(factsModels));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mFactsLiveDataStatus.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<LoginModel>> getLoginData() {
        return mFactsLiveDataStatus;
    }
}

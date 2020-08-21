package com.app.baseapp.feature.login_module.login_screen;

import android.app.Application;
import android.util.Log;

import com.app.baseapp.networking.Resource;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class LoginViewModel extends AndroidViewModel implements Observable {

    private LoginRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<LoginModel>> mMediatorLiveData = new MediatorLiveData<>();
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    public String emailAddress = "vinod@gmail.com";


    @Inject
    LoginViewModel(@NonNull Application application, LoginRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getLoginData(), listResource -> {

            switch (listResource.mStatus) {
                case LOADING:
                    mMediatorLiveData.setValue(Resource.loading(null));
                    break;

                case SUCCESS:
                    mMediatorLiveData.setValue(Resource.success(listResource.mData));
                    break;

                case ERROR:
                    mMediatorLiveData.setValue(Resource.error(listResource.mMessage, null));
                    break;
            }
        });
    }


    public MutableLiveData<Resource<LoginModel>> getUserDetail() {
        return mMediatorLiveData;

    }

    public void onClick(LoginModel loginUser) {
        mMediatorLiveData.setValue(Resource.validation(loginUser));

    }


    public void loginAction(LoginModel loginModel) {
        mRepository.loginAction(loginModel);
    }

    public void signUpAction(LoginModel loginModel, String token) {
        mRepository.signUpAction(loginModel);
    }

    public void showMessage(){
        Log.d("TAHHH", "Message");
    }

    @Override
    public void addOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

}

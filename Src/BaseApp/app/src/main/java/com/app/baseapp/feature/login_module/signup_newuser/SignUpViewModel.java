package com.app.baseapp.feature.login_module.signup_newuser;

import android.app.Application;
import android.util.Patterns;

import com.app.baseapp.apputils.BaseUtils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SignUpViewModel extends AndroidViewModel {

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<SignUpModel> userMutableLiveData;
    private SignUpModel signUpModel;


    public MutableLiveData<SignUpModel> getUserDetail() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(String firstName, String lastName, String email, String phoneNumber,
                        String password, String confirmPassword) {
        signUpModel = new SignUpModel();
        signUpModel.firstName = firstName;
        signUpModel.lastName = lastName;
        signUpModel.emailAddress = email;
        signUpModel.phone = phoneNumber;
        signUpModel.password = password;
        signUpModel.confirmPassword = confirmPassword;
        userMutableLiveData.setValue(signUpModel);

    }

    public void updateModel() {

    }

    public void validPassword(String emailAddress, String password) {
        SignUpModel signUpModel = new SignUpModel();
        signUpModel.emailAddress = emailAddress;
        signUpModel.password = password;
        userMutableLiveData.setValue(signUpModel);
    }


    public void inputValid(SignUpModel signUpModel) {
        userMutableLiveData.setValue(signUpModel);
    }

    public boolean isEmailValid(String email) {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.length() > 10;
    }

    public HashMap<String, Boolean> checkalidPassword(String emailAddress, String password) {
        return BaseUtils.passwordError(emailAddress, password);
    }
}

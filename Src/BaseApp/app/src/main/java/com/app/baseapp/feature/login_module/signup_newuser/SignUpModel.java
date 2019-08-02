package com.app.baseapp.feature.login_module.signup_newuser;

import android.text.TextUtils;
import android.util.Patterns;

import com.app.baseapp.apputils.BaseUtils;

import java.util.HashMap;

public class SignUpModel {

    public String firstName = "";
    public String lastName = "";
    public String phone = "";
    public String emailAddress = "";
    public String password = "";
    public String confirmPassword = "";


    public boolean isFirstNameVaild() {
        return TextUtils.isEmpty(firstName);
    }

    public boolean isLastNameVaild() {
        return TextUtils.isEmpty(lastName);
    }


    public boolean isValidPhoneNumber(){
        return phone.length() >10;
    }

    public boolean isEmailValid() {
        return !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }


    public HashMap<String, Boolean> isPasswordValid() {
        return BaseUtils.passwordError(emailAddress, password);
    }

    public boolean isPasswordMatch() {
        return !password.matches(confirmPassword);
    }

}
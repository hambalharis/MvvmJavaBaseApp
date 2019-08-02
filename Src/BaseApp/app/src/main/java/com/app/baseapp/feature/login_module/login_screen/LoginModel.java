package com.app.baseapp.feature.login_module.login_screen;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status_code")
    @Expose
    public String mServerStatus;
    @SerializedName("message")
    @Expose
    public String mMessage;
    @SerializedName("data")
    @Expose
    public LoginData mData = null;

    public String emailAddress;
    public String password;
    public String name;
    public String mobileNumber;


    public boolean isEmailValid() {
       // return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        return !TextUtils.isEmpty(emailAddress);
    }


    public boolean isPasswordValid() {
        return password.length() > 5;
    }

    public boolean isNameValid() {
        return TextUtils.isEmpty(name);
    }

    public boolean isPhoneNoValid() {
        return mobileNumber.length() <= 10;
    }

    public class LoginData {

        @SerializedName("uid")
        @Expose
        public String uid;
        @SerializedName("firstname")
        @Expose
        public String firstname;
        @SerializedName("lastname")
        @Expose
        public String lastname;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("gsm_token")
        @Expose
        public String gsmToken;
        @SerializedName("profile_pic")
        @Expose
        public String profilePic;
        @SerializedName("usertype")
        @Expose
        public String usertype;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("cdate")
        @Expose
        public String cdate;
        @SerializedName("checkstatus")
        @Expose
        public String checkstatus;

    }
}
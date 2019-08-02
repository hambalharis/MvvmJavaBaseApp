package com.app.baseapp.feature.login_module.login_screen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostSignUpModel {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("gsm_token")
    @Expose
    public String gsmToken;
    @SerializedName("usertype")
    @Expose
    public String usertype;

    public static class PostLogiModel{
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("gsm_token")
        @Expose
        public String gsmToken;
    }

}
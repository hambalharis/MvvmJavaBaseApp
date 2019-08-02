package com.app.baseapp.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*Shared mSharedPreferences.. */
public class AppPreferences implements PreferenceAttribute {

    public static AppPreferences instance;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;


    private AppPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static AppPreferences getPreferenceInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context);
            return instance;
        }
        return instance;

    }


    /**
     * Sets user login status
     *
     * @param isLogin
     */
    public void setUserLogin(boolean isLogin) {
        mEditor.putBoolean(IS_USER_LOGIN, isLogin);
        mEditor.commit();
    }

    public boolean getIsUserLogin() {
        return mSharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    /**
     * Sets user type
     *
     * @param userId
     */
    public void setUserId(String userId) {
        mEditor.putString(USER_ID, userId);
        mEditor.commit();
    }

    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, null);
    }

    public void clearPreferenceData() {
        mEditor.clear();
        mEditor.apply();
    }

    /**
     * Sets entity values
     *
     * @param fcmToken
     */
    public void setFCMToken(String fcmToken) {
        mEditor.putString(FCM_TOKEN, fcmToken);
        mEditor.commit();
    }

    public String getFCMToken() {
        return mSharedPreferences.getString(FCM_TOKEN, null);
    }
}

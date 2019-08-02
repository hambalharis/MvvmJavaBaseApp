package com.app.baseapp.service;

import android.util.Log;

import com.app.baseapp.apputils.AppConstants;
import com.app.baseapp.preference.AppPreferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/*Service starts when the push notification received...*/
public class FcmMessagingService extends FirebaseMessagingService implements AppConstants {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(getClass().getSimpleName(), "Refreshed token: " + token);
        AppPreferences.getPreferenceInstance(this).setFCMToken(token);
    }
}



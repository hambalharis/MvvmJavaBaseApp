package com.app.baseapp.networking;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/*Created by Vinod Kumar (Aug 2019)*/

/*This class is used to intercept the request created by retrofit and add required parameter for network bidding... */
public class AuthenticationInterceptor implements Interceptor {


    private static final String KEY_AUTHORIZATION = "Authorization";


  /*  @Inject
    AppPreferences mSharedPreferences;

    public AuthenticationInterceptor(Context context) {
        BlueprintApplication.getApp().getDaggerAppComponent().inject(this);
    }*/

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        // Nothing to add to intercepted request if:
        // a) Authorization value is empty because user is not logged in yet
        // b) There is already a header with updated Authorization value
        Request.Builder builder = originalRequest.newBuilder();
        if (alreadyHasAuthorizationHeader(originalRequest)) {
            Request modifiedRequest = builder
                    // .header(API_ACCESS_KEY, API_ACCESS_KEY_VALUE)
                    .build();
            return chain.proceed(modifiedRequest);
        }

        // Add authorization header with updated authorization value to intercepted request
        Request modifiedRequest = builder
                .header(KEY_AUTHORIZATION, "")
                .build();
        return chain.proceed(modifiedRequest);
    }


    private boolean alreadyHasAuthorizationHeader(Request request) {
        for (String header : request.headers().names()) {
            if (header.equals(KEY_AUTHORIZATION)) {
                return true;
            }
        }
        return false;
    }
}

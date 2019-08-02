package com.app.baseapp.feature;

import android.os.Bundle;
import android.os.Handler;

import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.landing_activity.HomeActivity;
import com.app.baseapp.feature.login_module.login_screen.LoginActivity;
import com.app.baseapp.preference.AppPreferences;
/*Created by Vinod Kumar (Aug 2019)*/

public class SplashActivity extends BaseActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        runnable = () -> {
            if (AppPreferences.getPreferenceInstance(this).getIsUserLogin())
                switchActivity(HomeActivity.class);
            else
                switchActivity(LoginActivity.class);
            finish();
        };

    }

    /**
     * In this method we are applying delay of SPLASH_DISPLAY_LENGTH
     */
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);
    }


    /**
     * This method is called when activity is destroyed.
     * In this, callbacks of handler removed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}

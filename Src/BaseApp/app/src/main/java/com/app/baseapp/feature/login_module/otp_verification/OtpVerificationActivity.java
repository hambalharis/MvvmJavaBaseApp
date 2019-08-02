package com.app.baseapp.feature.login_module.otp_verification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.landing_activity.HomeActivity;
import com.app.baseapp.feature.login_module.reset_password.ResetPasswordActivity;

public class OtpVerificationActivity extends BaseActivity {

    private int validatePinFrom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        initializeView();
    }

    private void initializeView() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
            validatePinFrom = bundle.getInt(VALIDATE_PIN_FROM);

        setUpToolBar(getString(R.string.verify_email_address), true);
        findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(this);
        spanClick();


        findViewById(R.id.img_next).setOnClickListener(v -> {
            if (validatePinFrom == 1)
                switchActivity(ResetPasswordActivity.class);
            else {
                switchActivity(HomeActivity.class);
            }

        });
    }

    /*Click spannable string on Resend OTP*/
    private void spanClick() {
        TextView mResendOtp = findViewById(R.id.tv_resend_otp);
        View.OnClickListener resendOtpListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getClass().getSimpleName(), "Resend Otp");
            }
        };
        onSpannableClick(getString(R.string.resend_pin), mResendOtp, resendOtpListner);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.toolbar_up_btn_activity:
                onBackPressed();
                break;
        }
    }
}

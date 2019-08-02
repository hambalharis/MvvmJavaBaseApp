package com.app.baseapp.feature.login_module.forgot_password;

import android.os.Bundle;
import android.view.View;

import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.login_module.otp_verification.OtpVerificationActivity;

public class ForgotPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeView();
    }

    private void initializeView() {
       findViewById(R.id.img_next).setOnClickListener(this);
    }

    private void showAlertMsg() {
        View.OnClickListener loginListner = v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(VALIDATE_PIN_FROM, VALIDATE_PIN_FROM_FORGOT);
            switchActivity(OtpVerificationActivity.class, bundle);
            dismissMessageAlertDialog();
        };
        showAlertMessageDialog(getString(R.string.confirmation), getString(R.string.forgot_sent_to_email),
                loginListner, getString(R.string.ok), null, null);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.img_next:
                showAlertMsg();
                break;
        }
    }
}

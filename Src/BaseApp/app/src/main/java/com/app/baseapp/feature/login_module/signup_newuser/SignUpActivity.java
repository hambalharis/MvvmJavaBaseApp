package com.app.baseapp.feature.login_module.signup_newuser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.login_module.login_screen.LoginActivity;
import com.app.baseapp.feature.login_module.otp_verification.OtpVerificationActivity;

import java.util.HashMap;

public class SignUpActivity extends BaseActivity implements View.OnFocusChangeListener {

    private EditText mFirstName, mLastName, mEmailAddress, mPhoneNumber, mPassword, mConfirmPassword;
    private TextView mFirstNameError, mLastNameError, mEmailError, mPhoneError, mPasswordError,
            mConfirmPassError;
    private SignUpViewModel signUpViewModel;
    private SignUpModel signUpModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        signUpModel = new SignUpModel();

        initializeView();
        listenLiveData();
    }

    private void initializeView() {
        setUpToolBarBlack(getString(R.string.sign_up));
        findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(this);

        findViewById(R.id.img_info).setOnClickListener(v -> passwordValidationDialog(true));
        findViewById(R.id.img_next).setOnClickListener(v -> loginAction());
        loginSpanClick();

        mFirstName = findViewById(R.id.et_first_name);
        mLastName = findViewById(R.id.et_last_name);
        mEmailAddress = findViewById(R.id.et_email);
        mPhoneNumber = findViewById(R.id.et_phone_number);
        mPassword = findViewById(R.id.et_password);
        mConfirmPassword = findViewById(R.id.et_confirm_password);

        mFirstNameError = findViewById(R.id.tv_first_name_error);
        mLastNameError = findViewById(R.id.tv_last_name_error);
        mEmailError = findViewById(R.id.tv_email_error);
        mPhoneError = findViewById(R.id.tv_phone_number_error);
        mPasswordError = findViewById(R.id.tv_password_error);
        mConfirmPassError = findViewById(R.id.tv_confirm_password_error);


        mFirstName.setOnFocusChangeListener(this);
        mLastName.setOnFocusChangeListener(this);
        mEmailAddress.setOnFocusChangeListener(this);
        mPhoneNumber.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
        mConfirmPassword.setOnFocusChangeListener(this);


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_first_name:
                String firstName = mFirstName.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(firstName)) {
                    setErrorView(mFirstNameError, getString(R.string.valid_first_name), mFirstName,
                            TextUtils.isEmpty(firstName));
                }
                break;

            case R.id.et_last_name:
                String lastName = mLastName.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(lastName)) {
                    setErrorView(mLastNameError, getString(R.string.valid_last_name), mLastName,
                            TextUtils.isEmpty(lastName));
                }
                break;

            case R.id.et_email:
                String email = mEmailAddress.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(email)) {
                    setErrorView(mEmailError, getString(R.string.error_email_invalid), mEmailAddress,
                            signUpViewModel.isEmailValid(email));
                }
                break;

            case R.id.et_phone_number:
                String phone = mPhoneNumber.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(phone)) {
                    setErrorView(mPhoneError, getString(R.string.valid_phone_number), mPhoneNumber,
                            TextUtils.isEmpty(phone));
                }
                break;

            case R.id.et_password:
                if (!hasFocus) {
                    passwordValidationDialog(false);
                }
                break;

            case R.id.et_confirm_password:
                if (!hasFocus) {
                    signUpModel.confirmPassword = mConfirmPassword.getText().toString().trim();
                    signUpViewModel.inputValid(signUpModel);

                } else {

                }
                break;
        }
    }

    private void passwordValidationDialog(boolean isClick) {
        HashMap<String, Boolean> map = signUpViewModel.checkalidPassword(mEmailAddress.getText().toString().trim(),
                mPassword.getText().toString().trim());

    }

    private void loginAction() {
        switchActivity(OtpVerificationActivity.class);
    }

    private void loginSpanClick() {
        TextView mSignIn = findViewById(R.id.tv_sign_in);
        View.OnClickListener signInListner = v -> switchActivity(LoginActivity.class);
        onSpannableClick(getString(R.string.sign_in_now), mSignIn, signInListner);
    }

    private void listenLiveData() {
        signUpViewModel.getUserDetail().observe(this, signUpUser -> {

        });
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

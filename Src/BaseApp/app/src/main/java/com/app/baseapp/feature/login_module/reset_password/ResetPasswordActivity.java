package com.app.baseapp.feature.login_module.reset_password;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.login_module.login_screen.LoginActivity;

public class ResetPasswordActivity extends BaseActivity implements View.OnFocusChangeListener {

    private EditText mPassword, mConfirmPassword;
    private TextView mPasswordError, mConfirmPassError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        initializeView();
    }

    private void initializeView() {
        //findViewById(R.id.img_info).setOnClickListener(v -> showPasswordAlertDialog(SignUpActivity.this, signUpUser.isPasswordValid()));
        findViewById(R.id.img_next).setOnClickListener(v -> resetPasswordAction());
        loginSpanClick();


        mPassword = findViewById(R.id.et_password);
        mConfirmPassword = findViewById(R.id.et_confirm_password);

        mPasswordError = findViewById(R.id.tv_password_error);
        mConfirmPassError = findViewById(R.id.tv_confirm_password_error);

        mPassword.setOnFocusChangeListener(this);
        mConfirmPassword.setOnFocusChangeListener(this);


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_password:
                if (!hasFocus) {
                    /*HashMap<String, Boolean> map = signUpViewModel.checkalidPassword(mEmailAddress.getText().toString().trim(),
                            mPassword.getText().toString().trim());

                    showPasswordAlertDialog(this, map);*/
                }
                break;

            case R.id.et_confirm_password:
                if (!hasFocus) {
                    /*signUpModel.confirmPassword = mConfirmPassword.getText().toString().trim();
                    signUpViewModel.inputValid(signUpModel);*/

                } else {

                }
                break;
        }
    }

    private void resetPasswordAction() {
        View.OnClickListener loginListner = v -> {
            switchActivity(LoginActivity.class);
        };
       /* showAlertMessageDialog(this, getString(R.string.confirmation), getString(R.string.password_changed_successfully),
                loginListner, getString(R.string.log_in), null, null);*/
    }

    private void loginSpanClick() {
        TextView mSignIn = findViewById(R.id.tv_sign_in);
        mSignIn.setOnClickListener(v -> switchActivity(LoginActivity.class));
    }


}

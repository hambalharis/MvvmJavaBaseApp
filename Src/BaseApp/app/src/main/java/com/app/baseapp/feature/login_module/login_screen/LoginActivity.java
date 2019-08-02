package com.app.baseapp.feature.login_module.login_screen;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.app.baseapp.BaseAppApplication;
import com.app.baseapp.R;
import com.app.baseapp.apputils.BaseUtils;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.baseui.BaseViewModelFactory;
import com.app.baseapp.feature.landing_activity.HomeActivity;
import com.app.baseapp.feature.login_module.forgot_password.ForgotPasswordActivity;
import com.app.baseapp.feature.login_module.signup_newuser.SignUpActivity;
import com.app.baseapp.networking.Resource;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    private TextView mSignUp, mEmailError, mPasswordError;
    private EditText mEmail, mPassword;
    private ImageView mSignIn;
    private LoginViewModel mLoginViewModel;
    private boolean mIsPasswordShown;

    @Inject
    public BaseViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        mLoginViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);

        initializeView();
        addPasswordViewToggle();
        observeData();
    }

    private void initializeView() {

        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE_DATA);

        setUpToolBarBlack(getString(R.string.login));
        findViewById(R.id.toolbar_up_btn_activity).setOnClickListener(this);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mEmailError = findViewById(R.id.tv_email_error);
        mPasswordError = findViewById(R.id.tv_password_error);
        mSignIn = findViewById(R.id.img_next);
        mSignIn.setOnClickListener(v -> {
            LoginModel model = new LoginModel();
            model.emailAddress = mEmail.getText().toString();
            model.password = mPassword.getText().toString();
            mLoginViewModel.onClick(model);
        });

        findViewById(R.id.tv_forgot_password).setOnClickListener(v -> switchActivity(ForgotPasswordActivity.class));

        setClickSpan();
    }


    /**
     * post login data to call login api
     */
    private void loginAction() {
        switchActivity(HomeActivity.class);
    }


    /**
     * Set sign up text and click function
     */
    private void setClickSpan() {
        mSignUp = findViewById(R.id.tv_sign_up);
        View.OnClickListener signUpListner = v -> switchActivity(SignUpActivity.class);
        onSpannableClick(getString(R.string.sign_up_now), mSignUp, signUpListner);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addPasswordViewToggle() {
        mPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; //index

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (mIsPasswordShown) {
                        mIsPasswordShown = false;
                        // 129 is obtained by bitwise OR-ing InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                        mPassword.setInputType(129);

                        // Need to call following as the font is changed to mono-space by default for password fields
                        mPassword.setTypeface(Typeface.SANS_SERIF);
                        mPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_close_eye, 0); // This is lock icon
                    } else {
                        mIsPasswordShown = true;
                        mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                        mPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_open_eye, 0); // Unlock icon
                    }

                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_up_btn_activity:
                onBackPressed();
                break;

            default:
                break;
        }
    }


    /**
     * Listen live data from login view model
     */
    private void observeData() {
        mLoginViewModel.getUserDetail().observe(this, this::observeLoginData);
    }


    /**
     * Observe data from view model and update ui according to user field validation and api success
     * or error case
     */
    private void observeLoginData(Resource<LoginModel> loginModelResource) {
        switch (loginModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                LoginModel loginModel = loginModelResource.mData;
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.error_email_empty),
                        null, getString(R.string.tv_ok), null, null);
                break;

            case VALIDATION:
                LoginModel loginUser = loginModelResource.mData;
                if (loginUser.isEmailValid() && loginUser.isPasswordValid()) {
                    setResetView(mEmailError, mEmail);
                    setResetView(mPasswordError, mPassword);

                    /*Call login api when all condition are true*/
                    if (!BaseUtils.checkNetwork(this))
                        showInternetErrorDialog(null);
                    else
                        loginAction();

                } else {
                    setErrorView(mEmailError, getString(R.string.wrong_credential), mEmail,
                            loginUser.isEmailValid());      // Called when user enter empty email

                    setErrorView(mPasswordError, getString(R.string.wrong_credential), mPassword,
                            loginUser.isPasswordValid());     // Called when user enter empty password
                }
                break;

            default:
                break;
        }
    }


}

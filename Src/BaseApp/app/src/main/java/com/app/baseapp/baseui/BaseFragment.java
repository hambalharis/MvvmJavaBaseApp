package com.app.baseapp.baseui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.baseapp.apputils.AppConstants;
import com.app.baseapp.feature.landing_activity.HomeActivity;

import androidx.fragment.app.Fragment;

/**
 * Created by Vinod Kumar (Aug 2019).
 */
public class BaseFragment extends Fragment implements AppConstants {

    /**
     * Method used to switch from current activity to other
     *
     * @param destinationActivity activity to open
     */
    public void startActivity(Class<?> destinationActivity) {
        ((BaseActivity) getActivity()).switchActivity(destinationActivity);
    }

    /**
     * Method used to switch from current activity to other with data
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     */
    public void startActivity(Class<?> destinationActivity, Bundle bundle) {
        ((BaseActivity) getActivity()).switchActivity(destinationActivity, bundle);
    }


    /**
     * Method used to start another activity for result
     *
     * @param destinationActivity activity to open
     * @param requestCode         requestCode
     */
    public void switchActivityForResult(Class<?> destinationActivity, int requestCode) {
        ((BaseActivity) getActivity()).switchActivityForResult(destinationActivity, requestCode);
    }

    protected void openDrawer() {
        ((HomeActivity) getActivity()).openDrawer();
    }



    public void showAlertMessageDialog(String alertHeader, String message,
                                       View.OnClickListener positiveButtonListener, String positiveButtonText,
                                       String negativeButtonText, View.OnClickListener negativeButtonListener) {
        ((BaseActivity) getActivity()).showAlertMessageDialog(alertHeader, message, positiveButtonListener,
                positiveButtonText, negativeButtonText, negativeButtonListener);
    }

    public void dismissMessageAlertDialog() {
        ((BaseActivity) getActivity()).dismissMessageAlertDialog();
    }

    public void hideKeyBoard(Activity mActivity) {
        ((BaseActivity) getActivity()).hideKeyBoard(mActivity);
    }

    /**
     * Common method to set the toolbar
     *
     * @param title
     */
    public void setUpToolbar(String title) {
//        isActivity is always false for fragment

        ((BaseActivity) getActivity()).setUpToolBar(title, false);
    }

    /**
     * This method is used to open date picker
     * */
    public void openDatePicker(TextView mTvDate){
        ((BaseActivity) getActivity()).openDatePicker(mTvDate);
    }

    /**
     * This method is used to open date picker
     * */
    public void openTimePicker(TextView mTvTime){
        ((BaseActivity) getActivity()).openTimePicker(mTvTime);
    }
}

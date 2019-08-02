package com.app.baseapp.baseui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;

import com.app.baseapp.R;
import com.app.baseapp.apputils.AppConstants;
import com.app.baseapp.apputils.BaseUtils;
import com.app.baseapp.apputils.FileUtils;
import com.app.baseapp.apputils.RotateImageUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/*Created by Vinod Kumar (Aug 2019)*/

/*Base activity class for all the activity used throughout application to share common methods...*/
public class BaseActivity extends AppCompatActivity implements AppConstants, View.OnClickListener {

    private Dialog mAlertDialog;
    private AlertDialog mChooseImageAlert;
    private final int REQUEST_CODE_TAKE_PICTURE = 3000;
    private final int REQUEST_CODE_GALLERY = 3001;


    private Uri mImageCaptureUri;
    private OnCapturedListener mCapturedListener;
    private File mFile;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private ProgressDialog pDialog;
    private LatLng sourceLatLang, destLatLang;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE_GALLERY:
                    try {
                        Uri mImageUri = savingImage(RotateImageUtil.handleSamplingAndRotationBitmap
                                (getApplicationContext(), data.getData()));
                        showPicTypeDialog(mImageUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_CODE_TAKE_PICTURE:
                    if (data != null && data.getData() != null) {
                        mImageCaptureUri = data.getData();
                    } else if (mImageCaptureUri != null) {
                        try {
                            Uri mImageUri = savingImage(RotateImageUtil.handleSamplingAndRotationBitmap
                                    (getApplicationContext(), mImageCaptureUri));
                            showPicTypeDialog(mImageUri);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public Uri savingImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/fonhome/saved_images");

        if (!myDir.isDirectory()) {
            myDir.mkdirs();
        }
        String fname = "Image-" + System.currentTimeMillis() + ".jpg";
        File file = new File(myDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Uri.fromFile(file);

    }

    private void showPicTypeDialog(Uri uri) {
        try {

            mFile = FileUtils.getFileToKeepImage(BaseActivity.this);
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(mFile);
            FileUtils.copyStream(inputStream, fileOutputStream);
            fileOutputStream.close();
            if (inputStream != null)
                inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCapturedListener != null) {
            mCapturedListener.onCaptured(Uri.fromFile(mFile));
        }
    }


    /**
     * This method is used to start camera
     */
    private void startCamera() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String BASE_DIR = getExternalCacheDir().getPath();

            File file = new File(BASE_DIR, "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

            mImageCaptureUri = FileProvider.getUriForFile(this, getPackageName(), file);

            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                photoPickerIntent.setClipData(ClipData.newRawUri("", mImageCaptureUri));
                photoPickerIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            startActivityForResult(photoPickerIntent, REQUEST_CODE_TAKE_PICTURE);

        } else {
            Log.v(getClass().getSimpleName(), "Media not mounted.");
        }
    }

    /**
     * This method is used to take picture from gallery
     */
    private void openGallery() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(
                    Intent.createChooser(photoPickerIntent, "Select Picture"),
                    REQUEST_CODE_GALLERY);
        } else {
            Log.v(getClass().getSimpleName(), "Media not mounted.");
        }
    }

    /**
     * This method is used to initialize the OnPicCapturedListener
     *
     * @param listener
     */
    public void setOnCapturedListener(OnCapturedListener listener) {
        mCapturedListener = listener;
    }

    /**
     * This interface is used to catch the photo bitmap from camera or gallery
     * and navigate, on where this interface is implemented
     */
    public interface OnCapturedListener {
        void onCaptured(Uri uri);
    }

    /**
     * Method used to switch from current activity to other
     *
     * @param destinationActivity activity to open
     */
    public void switchActivity(Class<?> destinationActivity) {
        startActivity(new Intent(this, destinationActivity));
    }

    /**
     * Method used to switch from current activity to other with data
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(this, destinationActivity);
        intent.putExtra(KEY_BUNDLE_DATA, bundle);
        startActivity(intent);
    }

    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     * @param requestCode         result code
     */
    public void switchActivityForResult(Class<?> destinationActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        intent.putExtra(KEY_BUNDLE_DATA, bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param requestCode         result code
     */
    public void switchActivityForResult(Class<?> destinationActivity, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * This method displays provided message on SnackBar
     *
     * @param view
     * @param message
     */
    public void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, 3500);
        final View snackBarView = snackbar.getView();
        snackBarView.setPadding(0, (int) getResources().getDimension(R.dimen.padding_minus_8),
                0, (int) getResources().getDimension(R.dimen.padding_minus_8));

        snackBarView.setBackgroundColor(getResources().getColor(android.R.color.black));
        TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(5);
        snackbar.show();
    }

    /**
     * Method used to display short duration toast
     *
     * @param resId resource id of the message string to be displayed
     */
    public void showSnackBar(View view, int resId) {
        showSnackBar(view, getString(resId));
    }

    public void showAlertMessageDialog(String alertHeader, String message,
                                       View.OnClickListener positiveButtonListener, String positiveButtonText,
                                       String negativeButtonText, View.OnClickListener negativeButtonListener) {

        if (BaseUtils.isKeyboardOpen) {
            hideKeyBoard(this);
        }

        /*
         * no need to re-initialized every time
         */
        if (mAlertDialog == null) {
            mAlertDialog = new Dialog(this, R.style.CustomDialogTheme);
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            mAlertDialog.setContentView(R.layout.alert_dialog_with_both_btns);
            mAlertDialog.setCanceledOnTouchOutside(false);
            mAlertDialog.setCancelable(false);
        }
        View view = mAlertDialog.findViewById(R.id.center_divider);

        TextView header = mAlertDialog.findViewById(R.id.header);

        if (alertHeader != null)
            header.setText(alertHeader);
        else
            header.setVisibility(View.GONE);

        TextView title = mAlertDialog.findViewById(R.id.message);
        if (message != null)
            title.setText(message);
        TextView ok = mAlertDialog.findViewById(R.id.positive_btn);
        ok.setText(positiveButtonText);

        if (positiveButtonText != null) {
            if (positiveButtonListener == null) {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });
            } else
                ok.setOnClickListener(positiveButtonListener);
        }

        TextView cancel = mAlertDialog.findViewById(R.id.negative_btn);
        cancel.setText(negativeButtonText);

        if (negativeButtonText != null) {
            cancel.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        } else {
            cancel.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }


        if (negativeButtonListener == null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
        } else
            cancel.setOnClickListener(negativeButtonListener);

        cancel.setVisibility(negativeButtonText == null ? View.GONE : View.VISIBLE);

        /*
         * do not show if already showing
         */
        if (!mAlertDialog.isShowing())
            mAlertDialog.show();
    }

    public void dismissMessageAlertDialog() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }


    /**
     * Method used to setUp toolbar i.e common in all activities
     *
     * @param title title
     */
    public void setUpToolBar(String title, boolean isActivity) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (!TextUtils.isEmpty(title)) {
            TextView textView = toolbar.findViewById(R.id.toolbar_text);
            textView.setText(title);
        }
        if (isActivity) {
            toolbar.findViewById(R.id.toolbar_up_btn_activity).setVisibility(View.VISIBLE);
            toolbar.findViewById(R.id.toolbar_up_btn_fragment).setVisibility(View.GONE);
        } else {
            toolbar.findViewById(R.id.toolbar_up_btn_activity).setVisibility(View.GONE);
            toolbar.findViewById(R.id.toolbar_up_btn_fragment).setVisibility(View.VISIBLE);
        }

    }


    /**
     * Method used to setUp toolbar i.e common in all activities
     *
     * @param title title
     */
    public void setUpToolBarBlack(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (!TextUtils.isEmpty(title)) {
            TextView textView = toolbar.findViewById(R.id.toolbar_text);
            textView.setTextColor(getResources().getColor(R.color.color_light_gray));
            textView.setText(title);
        }
        toolbar.findViewById(R.id.toolbar_up_btn_activity).setVisibility(View.VISIBLE);
        ((ImageView) toolbar.findViewById(R.id.toolbar_up_btn_activity)).setImageResource(R.mipmap.ic_back_arrow);
        toolbar.findViewById(R.id.toolbar_up_btn_fragment).setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {

    }

    public void onSpannableClick(String message, TextView view, View.OnClickListener spanClickListener) {
        SpannableString ss = new SpannableString(message);

        ClickableSpan signUp = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                view.setOnClickListener(spanClickListener);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(signUp, message.indexOf(SPAN_SPLIT) + 1, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, message.indexOf(SPAN_SPLIT) + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), message.indexOf(SPAN_SPLIT) + 1, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ss);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }


    /**
     * Method used to set error message i.e common in all activities
     *
     * @param textView error msg view
     * @param errorMsg show error
     * @param mInput   current edittext
     * @param isValid  check condition is true or false
     */
    public void setErrorView(TextView textView, String errorMsg, EditText mInput, boolean isValid) {
        ColorStateList colorError = ColorStateList.valueOf(Color.RED);
        ColorStateList colorSuccess = ColorStateList.valueOf(getResources().getColor(R.color.edittext_line_color));

        textView.setVisibility(isValid ? View.GONE : View.VISIBLE);
        textView.setText(errorMsg);
        ViewCompat.setBackgroundTintList(mInput, isValid ? colorSuccess : colorError);
        textView.requestFocus();
    }


    /**
     * Set error reset view
     */
    public void setResetView(TextView textView, EditText mInput) {
        ColorStateList colorSuccess = ColorStateList.valueOf(getResources().getColor(R.color.edittext_line_color));
        ViewCompat.setBackgroundTintList(mInput, colorSuccess);
        textView.setVisibility(View.GONE);
    }


    /**
     * Method to hide soft keyboard
     *
     * @param mActivity
     */
    public static void hideKeyBoard(Activity mActivity) {
// Check if no view has focus:
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void showProgressDialog() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Please wait");
        pDialog.show();
    }

    public void dismissProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    public void openDatePicker(TextView mTvDate) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mTvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    int count = 0;
    String time = null;

    public void openTimePicker(TextView mTvTime) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {

            if (time == null)
                time = selectedHour + ":" + selectedMinute + ((hour < 12) ? " AM" : " PM");
            else
                time = time + " - " + selectedHour + ":" + selectedMinute + ((hour < 12) ? " AM" : " PM");

            mTvTime.setText(time);

            count++;

            if (count == 1)
                openTimePicker(mTvTime);

        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void addMarker(GoogleMap mMap, String address) {
        destLatLang = BaseUtils.getLatLongFromAddress(this, address);
        MarkerOptions marker_dest = new MarkerOptions().position(destLatLang);
        marker_dest.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(marker_dest);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                destLatLang, 12);
        mMap.animateCamera(location);

    }

    /**
     * Set Image in imageView
     *
     * @param imageUrl
     * @param imageView
     */
    public void setImage(String imageUrl, ImageView imageView, int placeHolder) {

        imageUrl = TextUtils.isEmpty(imageUrl) ? null : imageUrl;

        Picasso.get()
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                });
    }


    /*Method to show network error dialog...*/
    public void showInternetErrorDialog(View.OnClickListener listener) {
        showAlertMessageDialog(getResources().getString(R.string.alert), getResources().getString(R.string.no_internet_connection), listener, getResources().getString(R.string.ok), null, null);
    }

}

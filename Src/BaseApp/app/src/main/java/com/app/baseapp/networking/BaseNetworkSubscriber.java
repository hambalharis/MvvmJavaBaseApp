package com.app.baseapp.networking;

import android.content.Context;
import android.util.Log;

import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;

/*Created by Vinod Kumar (Aug 2019)*/

/*This class is to distinguish network error type and to handle them... */
public class BaseNetworkSubscriber<T> extends DisposableObserver<T> {

    private Context mContext;

    public BaseNetworkSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof RetrofitException) {
            final RetrofitException error = (RetrofitException) e;

            if (error.getKind() == RetrofitException.Kind.NETWORK) {
                if (error.getCause() instanceof SocketTimeoutException) {
                    onSocketTimeoutException(e);
                } else {
                    onNetworkError();
                }
            } else if (error.getKind() == RetrofitException.Kind.HTTP) {
                try {

                    onCustomServerError();

                } catch (Exception re) {
                    onUnknownError(re);
                }
            } else {
                onUnknownError(e);
            }
        } else {
            onUnknownError(e);
        }
    }

    public void onNetworkError() {
//        Toast.makeText(mContext, mContext.getString(R.string.error_no_internet),
//                Toast.LENGTH_SHORT).show();
    }

    public void onUnknownError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
        }
//        Toast.makeText(mContext, mContext.getString(R.string.error_no_internet),
//                Toast.LENGTH_SHORT)
//                .show();
    }

    private static final String TAG = "Tag";

    private static final String DIRECTORY_SEPARATOR = System.getProperty("file.separator");

    public void onSocketTimeoutException(Throwable e) {

    }

    public void onCustomServerError() {
//        Toast.makeText(mContext, mContext.getString(R.string.fatal_error),
//                Toast.LENGTH_SHORT).show();
    }


    //@DebugLog
    @Override
    public void onNext(T t) {
        Log.d(getClass().getSimpleName(), "onNext");
    }
}

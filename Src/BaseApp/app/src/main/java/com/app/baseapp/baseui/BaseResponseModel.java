package com.app.baseapp.baseui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinod Kumar (Aug 2019)
 */
public class BaseResponseModel<T> {
    @SerializedName("status_code")
    @Expose
    public boolean mServerStatus;
    @SerializedName("message")
    @Expose
    public String mMessage;
    @SerializedName("data")
    @Expose
    public T mData = null;
}

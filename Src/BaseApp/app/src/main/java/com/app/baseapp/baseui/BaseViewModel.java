package com.app.baseapp.baseui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
/*Created by Vinod Kumar (Aug 2019)*/

/*Base view model class for all the view models used throughout application to share common methods...*/
public class BaseViewModel extends AndroidViewModel {


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onCleared() {
        super.onCleared();

    }
}

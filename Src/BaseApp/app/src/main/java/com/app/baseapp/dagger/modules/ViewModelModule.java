package com.app.baseapp.dagger.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.baseapp.baseui.BaseViewModelFactory;
import com.app.baseapp.dagger.annotations.ViewModelKey;
import com.app.baseapp.feature.login_module.login_screen.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*Created by Vinod Kumar (Aug 2019)*/

/*Module class that will provide view model class object to inject via Dagger...*/
@Module
public abstract class ViewModelModule {


    // add more ViewModels
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(BaseViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel loginViewModel(LoginViewModel viewModel);


}

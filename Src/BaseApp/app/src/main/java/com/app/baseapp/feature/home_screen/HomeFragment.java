package com.app.baseapp.feature.home_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.baseapp.BaseAppApplication;
import com.app.baseapp.R;
import com.app.baseapp.baseui.BaseFragment;
import com.app.baseapp.baseui.BaseViewModelFactory;
import com.app.baseapp.feature.landing_activity.HomeActivity;

import javax.inject.Inject;

/**
 * Created by Vinod Kumar (Aug 2019).
 */
public class HomeFragment extends BaseFragment {

    @Inject
    public BaseViewModelFactory mViewModelFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.toolbar_up_btn_fragment).setOnClickListener(v -> openDrawer());
        setUpToolbar(getString(R.string.app_name));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn((HomeActivity) getActivity());

    }


}

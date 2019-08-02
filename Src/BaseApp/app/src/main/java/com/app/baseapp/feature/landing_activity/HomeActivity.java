package com.app.baseapp.feature.landing_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.baseapp.R;
import com.app.baseapp.apputils.AppConstants;
import com.app.baseapp.baseui.BaseActivity;
import com.app.baseapp.feature.home_screen.HomeFragment;
import com.app.baseapp.feature.login_module.login_screen.LoginActivity;
import com.app.baseapp.preference.AppPreferences;
import com.google.android.material.navigation.NavigationView;

/**
 * Created by Vinod Kumar (Aug 2019).
 */
public class HomeActivity extends BaseActivity implements AppConstants, NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);


        navigationView.getHeaderView(0).findViewById(R.id.navigation_header_container).setOnClickListener(this);
        setNavigationDrawer();
        openLauncherFragment();
    }

    private void openLauncherFragment() {
        loadFragment(new HomeFragment());

        setNavigationDrawer();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        openLauncherFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        NavigationMenuForBuyer(menuItem);
        return true;
    }

    /**
     * This method will setup navigation menu for Buyer & control for all fragments.
     *
     * @param menuItem
     */
    private void NavigationMenuForBuyer(MenuItem menuItem) {

        Fragment fragment = fragmentManager.getFragments().get(0);
        switch (menuItem.getItemId()) {

            case R.id.buyer_dashboard:
                if (!(fragment instanceof HomeFragment))
                    fragment = new HomeFragment();
                break;


            case R.id.log_out:
                AppPreferences.getPreferenceInstance(this).clearPreferenceData();
                switchActivity(LoginActivity.class);
                finish();
                break;
        }

        if (fragment != null && fragmentManager.getFragments().get(0) != fragment) {
            loadFragment(fragment);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    /**
     * THis method opens the navigation menu drawer.
     */
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void loadFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.navigation_header_container:

                Fragment fragment = fragmentManager.getFragments().get(0);
                /*if (!(fragment instanceof UserProfileFragment))
                    fragment = new UserProfileFragment();*/

                if (fragmentManager.getFragments().get(0) != fragment) {
                    loadFragment(fragment);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }


    /**
     * THis method will load the given fragment with provided data
     *
     * @param bundle
     * @param fragment
     */
    public void openFragmentWithData(Bundle bundle, Fragment fragment) {

        fragment.setArguments(bundle);
        loadFragment(fragment);
    }


    private void setNavigationDrawer() {
        navigationView.getMenu().clear();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navigationView.inflateMenu(R.menu.menu_nav);
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.getFragments().get(0);
        if ((fragment instanceof HomeFragment))
            super.onBackPressed();
        else {
            fragment = new HomeFragment();
            loadFragment(fragment);
        }


    }
}
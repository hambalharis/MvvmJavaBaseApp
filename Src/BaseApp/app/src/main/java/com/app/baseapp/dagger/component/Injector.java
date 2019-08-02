package com.app.baseapp.dagger.component;

import com.app.baseapp.feature.landing_activity.HomeActivity;
import com.app.baseapp.feature.login_module.login_screen.LoginActivity;

/*Created by Vinod Kumar (Aug 2019)*/

/*Interface here Dagger find out the component type in that dagger have to inject dependencies... */

interface Injector {

    void provideIn(HomeActivity activity);

    void provideIn(LoginActivity activity);


}

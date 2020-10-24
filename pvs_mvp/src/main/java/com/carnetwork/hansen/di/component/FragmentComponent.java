package com.carnetwork.hansen.di.component;

import android.app.Activity;

import com.carnetwork.hansen.di.module.FragmentModule;
import com.carnetwork.hansen.di.scope.FragmentScope;
import com.carnetwork.hansen.ui.main.activity.SplashActivity;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;


import dagger.Component;

/**

 */

@FragmentScope
@Component(dependencies = com.carnetwork.hansen.di.component.AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MapFragment mapFragment);

}

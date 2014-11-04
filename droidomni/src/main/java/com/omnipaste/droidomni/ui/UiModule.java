package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;

import dagger.Module;

@Module(
    injects = {
        LauncherActivity_.class,
        LoginActivity_.class
    },
    complete = false
)
public class UiModule {
}

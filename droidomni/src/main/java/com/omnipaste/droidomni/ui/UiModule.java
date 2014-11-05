package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.activity.ErrorActivity_;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;

import dagger.Module;

@Module(
    injects = {
        LauncherActivity_.class,
        LoginActivity_.class,
        ErrorActivity_.class
    },
    complete = false,
    library = true
)
public class UiModule {
}

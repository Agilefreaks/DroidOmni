package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.activity.ConnectingActivity_;
import com.omnipaste.droidomni.ui.activity.ErrorActivity_;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;

import dagger.Module;

@Module(
    injects = {
        LauncherActivity_.class,
        ConnectingActivity_.class,
        LoginActivity_.class,
        ErrorActivity_.class,
        OmniActivity_.class
    },
    complete = false,
    library = true
)
public class UiModule {
}

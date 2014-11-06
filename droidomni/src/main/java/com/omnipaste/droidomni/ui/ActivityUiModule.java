package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.activity.AboutActivity_;
import com.omnipaste.droidomni.ui.activity.ConnectingActivity_;
import com.omnipaste.droidomni.ui.activity.ErrorActivity_;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;
import com.omnipaste.droidomni.ui.activity.SettingsActivity_;

import dagger.Module;

@Module(
    injects = {
        LauncherActivity_.class,
        ConnectingActivity_.class,
        LoginActivity_.class,
        ErrorActivity_.class,
        OmniActivity_.class,
        AboutActivity_.class,
        SettingsActivity_.class
    },
    complete = false,
    library = true
)
public class ActivityUiModule {
}

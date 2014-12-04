package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;
import com.omnipaste.droidomni.ui.fragment.AllAloneFragment_;
import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment_;
import com.omnipaste.droidomni.ui.fragment.TutorialClippingCloudFragment_;
import com.omnipaste.droidomni.ui.fragment.TutorialClippingLocalFragment_;

import dagger.Module;

@Module(
    injects = {
        NavigationDrawerFragment_.class,
        ActivityFragment_.class,
        AllAloneFragment_.class,
        TutorialClippingLocalFragment_.class,
        TutorialClippingCloudFragment_.class
    },
    complete = false,
    library = true
)
public class FragmentUiModule {
}

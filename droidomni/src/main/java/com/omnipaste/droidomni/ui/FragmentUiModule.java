package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment_;

import dagger.Module;

@Module(
    injects = {
        NavigationDrawerFragment_.class
    },
    complete = false,
    library = true
)
public class FragmentUiModule {
}

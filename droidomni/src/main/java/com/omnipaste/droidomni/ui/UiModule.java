package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.adapter.AdapterModule;

import dagger.Module;

@Module(
    includes = {
        AdapterModule.class,
        ActivityUiModule.class,
        FragmentUiModule.class,
        ViewUiModule.class
    },
    complete = false,
    library = true
)
public class UiModule {
}

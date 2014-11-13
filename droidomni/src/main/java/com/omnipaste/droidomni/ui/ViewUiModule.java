package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.view.ClippingView_;

import dagger.Module;

@Module(
    injects = {
        ClippingView_.class
    },
    complete = false,
    library = true
)
public class ViewUiModule {
}

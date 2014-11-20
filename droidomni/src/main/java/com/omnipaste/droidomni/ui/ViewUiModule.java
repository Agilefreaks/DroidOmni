package com.omnipaste.droidomni.ui;

import com.omnipaste.droidomni.ui.view.clipping.LocalClippingView_;
import com.omnipaste.droidomni.ui.view.clipping.OmniClippingView_;

import dagger.Module;

@Module(
    injects = {
        OmniClippingView_.class,
        LocalClippingView_.class
    },
    complete = false,
    library = true
)
public class ViewUiModule {
}

package com.omnipaste.droidomni.service;

import com.omnipaste.droidomni.service.smartaction.SmartActionRemove;

import dagger.Module;

@Module(
    injects = {
        OmniService_.class,
        GcmIntentService_.class,
        SmartActionRemove.class
    },
    complete = false,
    library = true
)
public class ServiceModule {
}

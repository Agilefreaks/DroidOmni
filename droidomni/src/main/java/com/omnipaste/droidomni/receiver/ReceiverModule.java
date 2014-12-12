package com.omnipaste.droidomni.receiver;

import dagger.Module;

@Module(
    injects = {
        StartOmniAtBootReceiver_.class,
        PackageReplaceReceiver_.class
    },
    complete = false,
    library = true
)

public class ReceiverModule {
}

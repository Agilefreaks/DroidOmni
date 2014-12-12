package com.omnipaste.droidomni.receiver;

import dagger.Module;

@Module(
    injects = {
        StartOmniAtBootReceiver_.class,
    },
    complete = false,
    library = true
)

public class ReceiverModule {
}

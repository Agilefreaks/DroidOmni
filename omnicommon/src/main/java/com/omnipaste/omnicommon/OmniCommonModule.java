package com.omnipaste.omnicommon;

import com.omnipaste.omnicommon.prefs.PrefsModule;

import dagger.Module;

@Module(
    includes = PrefsModule.class,
    complete = false,
    library = true)
public class OmniCommonModule {
}

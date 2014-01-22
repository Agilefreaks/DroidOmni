package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class ClipboardProviderModule {
  @Provides
  public IOmniClipboardManager providesOmniClipboardManager(OmniClipboardManager omniClipboardManager) {
    return omniClipboardManager;
  }

  @Singleton
  @Provides
  public IClipboardProvider providesClipboardProvider(ClipboardProvider clipboardProvider) {
    return clipboardProvider;
  }
}

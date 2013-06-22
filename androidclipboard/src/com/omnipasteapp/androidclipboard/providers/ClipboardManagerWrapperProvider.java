package com.omnipasteapp.androidclipboard.providers;

import android.text.ClipboardManager;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.omnipasteapp.androidclipboard.support.ClipboardManagerWrapper;
import com.omnipasteapp.androidclipboard.support.IClipboardManagerWrapper;

@SuppressWarnings("deprecation")
public class ClipboardManagerWrapperProvider implements Provider<IClipboardManagerWrapper> {

  @Inject
  private ClipboardManager _clipboardManager;

  @Override
  public IClipboardManagerWrapper get() {
    IClipboardManagerWrapper instance = new ClipboardManagerWrapper(_clipboardManager);
    instance.start();

    return instance;
  }
}

package com.omnipasteapp.androidclipboard;

import android.content.Context;
import android.os.Build;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.androidclipboard.support.CompatibilityAndroidClipboard;
import com.omnipasteapp.androidclipboard.support.IClipboardManagerWrapper;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import roboguice.inject.SystemServiceProvider;

@SuppressWarnings({"NewApi", "deprecation"})
public class AndroidClipboardModule extends AbstractModule {
  @Override
  protected void configure() {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      bind(ILocalClipboard.class).to(CompatibilityAndroidClipboard.class);
      bind(android.text.ClipboardManager.class).toProvider(new SystemServiceProvider<android.text.ClipboardManager>(Context.CLIPBOARD_SERVICE));
      bind(IClipboardManagerWrapper.class).to(ClipboardManagerWrapper.class).in(Singleton.class);
    } else {
      bind(ILocalClipboard.class).to(AndroidClipboard.class);
      bind(android.content.ClipboardManager.class).toProvider(new SystemServiceProvider<android.content.ClipboardManager>(Context.CLIPBOARD_SERVICE));
    }
  }
}

package com.omnipasteapp.androidclipboard;

import android.content.ClipboardManager;
import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import roboguice.inject.SystemServiceProvider;

public class AndroidClipboardModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ILocalClipboard.class).to(AndroidClipboard.class).in(Singleton.class);
    bind(ClipboardManager.class).toProvider(new SystemServiceProvider<ClipboardManager>(Context.CLIPBOARD_SERVICE));
  }
}

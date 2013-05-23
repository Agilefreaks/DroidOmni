package com.omnipasteapp.androidclipboard;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

public class AndroidClipboardModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ILocalClipboard.class).to(AndroidClipboard.class).in(Singleton.class);
  }
}

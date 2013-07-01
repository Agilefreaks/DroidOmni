package com.omnipasteapp.androidclipboard;

import android.content.ClipboardManager;
import android.content.Context;

import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class AndroidClipboardModule {

  @Provides
  ILocalClipboard provideLocalClipboard() {
    return new AndroidClipboard();
  }

  @Provides
  ClipboardManager provideClipboardManager(Context context) {
    return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
  }

//  protected void configure() {
//
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//      bind(ILocalClipboard.class).to(CompatibilityAndroidClipboard.class);
//      bind(android.text.ClipboardManager.class).toProvider(new SystemServiceProvider<android.text.ClipboardManager>(Context.CLIPBOARD_SERVICE));
//      bind(IClipboardManagerWrapper.class).to(ClipboardManagerWrapper.class);
//    } else {
////      bind(ILocalClipboard.class).to(com.omnipasteapp.androidclipboard.AndroidClipboard.class);
////      bind(android.content.ClipboardManager.class).toProvider();
//    }
}

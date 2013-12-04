package com.omnipasteapp.clipboardprovider;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import com.omnipasteapp.clipboardprovider.androidclipboard.AndroidClipboard;
import com.omnipasteapp.clipboardprovider.androidclipboard.v10.ClipboardManagerWrapper;
import com.omnipasteapp.clipboardprovider.androidclipboard.v10.IClipboardManagerWrapper;
import com.omnipasteapp.clipboardprovider.omniclipboard.OmniClipboard;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("deprecation")
@Module(complete = false, library = true)
public class ClipboardProviderModule {

  @Provides
  ILocalClipboard provideLocalClipboard(Context context) {
    ILocalClipboard result;

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
      android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      IClipboardManagerWrapper clipboardManagerWrapper = new ClipboardManagerWrapper(clipboardManager);
      result = new com.omnipasteapp.clipboardprovider.androidclipboard.v10.AndroidClipboard(clipboardManagerWrapper);
    } else {
      ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      result = new AndroidClipboard(clipboardManager);
    }

    return result;
  }

  @Provides
  IOmniClipboard provideOmniClipboard(OmniClipboard omniClipboard) {
    return omniClipboard;
  }

  @Provides
  IClipboardProvider provideClipboardProvider(ClipboardProvider clipboardProvider) {
    return clipboardProvider;
  }
}

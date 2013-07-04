package com.omnipasteapp.androidclipboard;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import com.omnipasteapp.androidclipboard.v10.ClipboardManagerWrapper;
import com.omnipasteapp.androidclipboard.v10.IClipboardManagerWrapper;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("deprecation")
@Module(complete = false, library = true)
public class AndroidClipboardModule {

  @Provides
  ILocalClipboard provideLocalClipboard(Context context) {
    ILocalClipboard result;

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      IClipboardManagerWrapper clipboardManagerWrapper = new ClipboardManagerWrapper(clipboardManager);
      result = new com.omnipasteapp.androidclipboard.v10.AndroidClipboard(clipboardManagerWrapper);
    } else {
      ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      result = new AndroidClipboard(clipboardManager);
    }

    return result;
  }
}

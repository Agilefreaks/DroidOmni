package com.omnipasteapp.clipboardprovider.androidclipboard.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.test.AndroidTestCase;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClippingBuilderTest extends AndroidTestCase {
  private ClipboardManager clipboardManager;
  private ClippingBuilder subject;

  protected void setUp() throws Exception {
    super.setUp();
    assert mContext != null;

    clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    subject = new ClippingBuilder(clipboardManager);
  }

  public void testBuildWillCreateAClippingWithTheClipboardContent() {
    clipboardManager.setPrimaryClip(new ClipData("", new String[]{""}, new ClipData.Item("42")));

    Clipping clipping = subject.build();

    assertEquals("42", clipping.getContent());
  }

  public void testBuildWillCreateAClippingWithLocalSender() {
    clipboardManager.setPrimaryClip(new ClipData("", new String[]{""}, new ClipData.Item("42")));

    Clipping clipping = subject.build();

    assertEquals(Sender.Local, clipping.getSender());
  }
}

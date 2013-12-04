package com.omnipasteapp.clipboardprovider.androidclipboard.v10;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.InstrumentationTestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ClipboardManagerWrapperTest extends InstrumentationTestCase {
  private ClipboardManagerWrapper _subject;

  @Mock
  private android.text.ClipboardManager _clipboardManager;

  @SuppressWarnings("ConstantConditions")
  public void setUp() {
    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    _subject = new ClipboardManagerWrapper(_clipboardManager);
  }

  public void testGetCurrentClipDoesNotCrushWhenGetTextReturnsNull() {
    when(_clipboardManager.getText()).thenReturn(null);

    assertNull(_subject.getCurrentClip());
  }
}

package com.omnipasteapp.androidclipboard.v10;

import android.annotation.TargetApi;
import android.os.Build;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ClipboardManagerWrapperTest extends TestCase {
  private ClipboardManagerWrapper _subject;

  @Mock
  private android.text.ClipboardManager _clipboardManager;

  public void setUp() {
    MockitoAnnotations.initMocks(this);

    _subject = new ClipboardManagerWrapper(_clipboardManager);
  }

  public void testGetCurrentClipDoesNotCrushWhenGetTextReturnsNull() {
    when(_clipboardManager.getText()).thenReturn(null);

    assertNull(_subject.getCurrentClip());
  }
}

package com.omnipasteapp.androidclipboard.v10;

import android.annotation.TargetApi;
import android.os.Build;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.models.Clipping;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AndroidClipboardTest extends TestCase {
  private AndroidClipboard _subject;

  @Mock
  private IClipboardManagerWrapper _clipboardManagerWrapper;

  public void setUp() {
    MockitoAnnotations.initMocks(this);

    _subject = new AndroidClipboard(_clipboardManagerWrapper);
  }

  public void testRunCallsClipboardManagerWrapperSetOnClipChangedListener() {
    _subject.run();

    verify(_clipboardManagerWrapper).setOnClipChangedListener(eq(_subject));
  }

  public void testPutDataCallsClipboardManagerWrapperSetClip() {
    _subject.putData("test");

    verify(_clipboardManagerWrapper).setClip(eq("test"));
  }

  public void testDisposeCallsClipboardManagerWrapperDispose() {
    _subject.dispose();

    verify(_clipboardManagerWrapper).dispose();
  }

  public void testOnPrimaryClipChangedCallsReceiverDataReceivedWhenHasClipping() {
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(Clipping clipping) {
        assertEquals("test", clipping.getContent());
      }
    };
    when(_clipboardManagerWrapper.hasClipping()).thenReturn(true);
    when(_clipboardManagerWrapper.getCurrentClip()).thenReturn("test");
    _subject.addDataReceiver(receiver);

    _subject.onPrimaryClipChanged();
  }

  public void testOnPrimaryClipChangedDoesNotCallReceiverDataReceivedWhenNotHasClipping() {
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(Clipping clipping) {
        assertFalse(true);
      }
    };
    when(_clipboardManagerWrapper.hasClipping()).thenReturn(false);
    _subject.addDataReceiver(receiver);

    _subject.onPrimaryClipChanged();
  }
}

package com.omnipasteapp.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidClipboardTest extends TestCase {

  @Mock
  private ClipboardManager mockClipboardManager;

  private AndroidClipboard subject;

  public void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new AndroidClipboard(mockClipboardManager);
  }

  public void testInitializeReturnsNewThread() {
    Thread result = subject.initialize();

    assertThat(result, is(notNullValue()));
  }

  public void testRunCallsAddPrimaryClipChangedListener() {
    subject.run();

    verify(mockClipboardManager).addPrimaryClipChangedListener(eq(subject));
  }

  public void testDisposeCallsRemovePrimaryClipChangedListener() {
    subject.dispose();

    verify(mockClipboardManager).removePrimaryClipChangedListener(eq(subject));
  }

  public void testOnPrimaryClipChangedCallsReceiverDataReceived() {
    ClipData mockClipData = mock(ClipData.class);
    ClipData.Item mockItem = mock(ClipData.Item.class);
    when(mockItem.getText()).thenReturn("asd");
    when(mockClipData.getItemCount()).thenReturn(1);
    when(mockClipData.getItemAt(anyInt())).thenReturn(mockItem);
    when(mockClipboardManager.hasPrimaryClip()).thenReturn(true);
    when(mockClipboardManager.getPrimaryClip()).thenReturn(mockClipData);
    ICanReceiveData mockDataReceiver = mock(ICanReceiveData.class);
    subject.addDataReceiver(mockDataReceiver);

    subject.onPrimaryClipChanged();

    verify(mockDataReceiver).dataReceived(any(IClipboardData.class));
  }

  public void testPutDataAlwaysCallsClipboardManagerSetPrimaryClip() {
    subject.putData("test");

    verify(mockClipboardManager).setPrimaryClip(any(ClipData.class));
  }
}

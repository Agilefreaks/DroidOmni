package com.omnipasteapp.clipboardprovider.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.test.InstrumentationTestCase;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.models.Clipping;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidClipboardTest extends InstrumentationTestCase {

  @Mock
  private ClipboardManager mockClipboardManager;

  private AndroidClipboard subject;

  @SuppressWarnings("ConstantConditions")
  public void setUp() {
    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    subject = new AndroidClipboard(mockClipboardManager);
  }

  public void testInitializeReturnsNewThread() {
    Thread result = subject.initialize();

    assertNotNull(result);
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

    verify(mockDataReceiver).dataReceived(any(Clipping.class));
  }

  public void testPutDataAlwaysCallsClipboardManagerSetPrimaryClip() {
    subject.putData("test");

    verify(mockClipboardManager).setPrimaryClip(any(ClipData.class));
  }
}

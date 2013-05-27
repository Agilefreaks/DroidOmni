package com.omnipasteapp.androidclipboard.test;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.androidclipboard.AndroidClipboard;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class AndroidClipboardTest {
  @Mock
  private Context mockContext;
  @Mock
  private ClipboardManager mockClipboardManager;

  private AndroidClipboard subject;

  private class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(Context.class).toInstance(mockContext);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));
    subject = new AndroidClipboard();
    RoboGuice.getInjector(Robolectric.application).injectMembers(subject);

    when(mockContext.getSystemService(Context.CLIPBOARD_SERVICE)).thenReturn(mockClipboardManager);
    subject.run();
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void initializeReturnsNewThread() {
    Thread result = subject.initialize();

    Assert.assertNotNull(result);
  }

  @Test
  public void runCallsContextGetClipboardService() {
    verify(mockContext).getSystemService(eq(Context.CLIPBOARD_SERVICE));
  }

  @Test
  public void runCallsAddPrimaryClipChangedListener() {
    verify(mockClipboardManager).addPrimaryClipChangedListener(eq(subject));
  }

  @Test
  public void disposeCallsRemovePrimaryClipChangedListener() {
    subject.dispose();

    verify(mockClipboardManager).removePrimaryClipChangedListener(eq(subject));
  }

  @Test
  public void onPrimaryClipChangedCallsReceiverDataReceived() {
    ClipData mockClipData = mock(ClipData.class);
    when(mockClipData.getItemCount()).thenReturn(1);
    when(mockClipData.getItemAt(eq(0))).thenReturn(new ClipData.Item("test"));
    when(mockClipboardManager.hasPrimaryClip()).thenReturn(true);
    when(mockClipboardManager.getPrimaryClip()).thenReturn(mockClipData);
    ICanReceiveData mockDataReceiver = mock(ICanReceiveData.class);
    subject.addDataReceiver(mockDataReceiver);

    subject.onPrimaryClipChanged();

    verify(mockDataReceiver).dataReceived(any(IClipboardData.class));
  }

  @Test
  public void putDataAlwaysCallsClipboardManagerSetPrimaryClip() {
    subject.putData("test");

    verify(mockClipboardManager).setPrimaryClip(any(ClipData.class));
  }
}

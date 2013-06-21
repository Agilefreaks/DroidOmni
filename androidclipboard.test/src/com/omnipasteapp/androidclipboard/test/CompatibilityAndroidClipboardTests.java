package com.omnipasteapp.androidclipboard.test;

import android.text.ClipboardManager;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.androidclipboard.ClipboardManagerWrapper;
import com.omnipasteapp.androidclipboard.CompatibilityAndroidClipboard;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CompatibilityAndroidClipboardTests {
  private CompatibilityAndroidClipboard subject;

  @Mock
  private ClipboardManagerWrapper clipboardManagerWrapper;

  @Mock
  private ClipboardManager systemClipboardManager;

  public class TestModule extends AbstractModule{

    @Override
    protected void configure() {
      bind(ClipboardManagerWrapper.class).toInstance(clipboardManagerWrapper);
      bind(ClipboardManager.class).toInstance(systemClipboardManager);
    }
  }

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    subject = new CompatibilityAndroidClipboard();
    RoboGuice.getInjector(Robolectric.application).injectMembers(subject);

    subject.run();
  }

  @After
  public void destroy(){
    subject.dispose();
  }

  @Test
  public void runAlwaysCallsClipboardManagerWrapperSetOnClipChangedListener(){
    verify(clipboardManagerWrapper).setOnClipChangedListener(eq(subject));
  }

  @Test
  public void runAlwaysCallsClipboardManagerWrapperStart(){
    verify(clipboardManagerWrapper).start();
  }

  @Test
  public void putDataAlwaysCallsClip0boardManagerWrapperSetClip(){
    subject.putData("test");

    verify(clipboardManagerWrapper).setClip(eq("test"));
  }

  @Test
  public void disposeCallsClipboardManagerWrapperDispose(){
    subject.dispose();

    verify(clipboardManagerWrapper).dispose();
  }

  @Test
  public void onPrimaryClipChangedWhenHasClippingCallsReceiverDataReceived(){
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(IClipboardData clipboardData) {
        assertEquals("test", clipboardData.getData());
      }
    };
    when(clipboardManagerWrapper.hasClipping()).thenReturn(true);
    when(clipboardManagerWrapper.getCurrentClip()).thenReturn("test");
    subject.addDataReceiver(receiver);

    subject.onPrimaryClipChanged();
  }

  @Test
  public void onPrimaryClipChangedWhenNotHasClippingDoesNotCallReceiverDataReceived(){
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(IClipboardData clipboardData) {
        Assert.fail();
      }
    };
    when(clipboardManagerWrapper.hasClipping()).thenReturn(false);
    subject.addDataReceiver(receiver);

    subject.onPrimaryClipChanged();
  }
}

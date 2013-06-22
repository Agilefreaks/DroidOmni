package com.omnipasteapp.androidclipboard.test;

import android.text.ClipboardManager;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.androidclipboard.support.ClipboardManagerWrapper;
import com.omnipasteapp.androidclipboard.support.CompatibilityAndroidClipboard;
import com.omnipasteapp.androidclipboard.support.IClipboardManagerWrapper;
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

@SuppressWarnings("deprecation")
@RunWith(RobolectricTestRunner.class)
public class CompatibilityAndroidClipboardTests {
  private CompatibilityAndroidClipboard _subject;

  @Mock
  private ClipboardManagerWrapper _clipboardManagerWrapper;

  @Mock
  private ClipboardManager _systemClipboardManager;

  public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(ClipboardManager.class).toInstance(_systemClipboardManager);
      bind(IClipboardManagerWrapper.class).toInstance(_clipboardManagerWrapper);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    _subject = new CompatibilityAndroidClipboard();
    RoboGuice.getInjector(Robolectric.application).injectMembers(_subject);

    _subject.run();
  }

  @After
  public void destroy() {
    _subject.dispose();
  }

  @Test
  public void runAlwaysCallsClipboardManagerWrapperSetOnClipChangedListener() {
    verify(_clipboardManagerWrapper).setOnClipChangedListener(eq(_subject));
  }

  @Test
  public void putDataAlwaysCallsClip0boardManagerWrapperSetClip() {
    _subject.putData("test");

    verify(_clipboardManagerWrapper).setClip(eq("test"));
  }

  @Test
  public void disposeCallsClipboardManagerWrapperDispose() {
    _subject.dispose();

    verify(_clipboardManagerWrapper).dispose();
  }

  @Test
  public void onPrimaryClipChangedWhenHasClippingCallsReceiverDataReceived() {
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(IClipboardData clipboardData) {
        assertEquals("test", clipboardData.getData());
      }
    };
    when(_clipboardManagerWrapper.hasClipping()).thenReturn(true);
    when(_clipboardManagerWrapper.getCurrentClip()).thenReturn("test");
    _subject.addDataReceiver(receiver);

    _subject.onPrimaryClipChanged();
  }

  @Test
  public void onPrimaryClipChangedWhenNotHasClippingDoesNotCallReceiverDataReceived() {
    ICanReceiveData receiver = new ICanReceiveData() {
      @Override
      public void dataReceived(IClipboardData clipboardData) {
        Assert.fail();
      }
    };
    when(_clipboardManagerWrapper.hasClipping()).thenReturn(false);
    _subject.addDataReceiver(receiver);

    _subject.onPrimaryClipChanged();
  }
}

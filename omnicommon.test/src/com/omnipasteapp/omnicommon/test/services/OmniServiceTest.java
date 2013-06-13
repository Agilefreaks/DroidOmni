package com.omnipasteapp.omnicommon.test.services;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.services.OmniService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class OmniServiceTest {

  private OmniService subject;

  @Mock
  private ILocalClipboard localClipboard;

  @Mock
  private IOmniClipboard omniClipboard;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private CommunicationSettings communicationSettings;

  public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(ILocalClipboard.class).toInstance(localClipboard);
      bind(IOmniClipboard.class).toInstance(omniClipboard);
      bind(IConfigurationService.class).toInstance(configurationService);
    }
  }

  @Before
  public void Setup() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    subject = RoboGuice.getInjector(Robolectric.application).getInstance(OmniService.class);
    when(configurationService.get_communicationSettings()).thenReturn(communicationSettings);
  }

  @Test
  public void getLocalClipboardWillReturnTheLocalClipboard() {
    assertThat(subject.getLocalClipboard(), is(ILocalClipboard.class));
  }

  @Test
  public void getOmniClipboardWillReturnTheOmniClipboard() {
    assertThat(subject.getOmniClipboard(), is(IOmniClipboard.class));
  }

  @Test
  public void whenSenderIsLocalClipboardItWillCallSendDataOnOmniClipboard() {
    subject.dataReceived(new ClipboardData(localClipboard, "42"));
    verify(omniClipboard).putData("42");
  }

  @Test
  public void whenSenderIsOmniClipboardItWillCallSendDataOnLocalClipboard() {
    subject.dataReceived(new ClipboardData(omniClipboard, "43"));
    verify(localClipboard).putData("43");
  }

  @Test
  public void whenDataReceivedIsTheSameAsOldDataReceivedDoNotCallPutData() {
    subject.dataReceived(new ClipboardData(omniClipboard, "42"));
    subject.dataReceived(new ClipboardData(localClipboard, "42"));
    verify(localClipboard).putData("42");
    verify(omniClipboard, never()).putData("42");
  }

  @Test
  public void whenDataReceivedIsEmptyDoNotPutData() {
    subject.dataReceived(new ClipboardData(omniClipboard, ""));
    verify(localClipboard, never()).putData("");
  }

  @Test
  public void stopShouldRemoveDataReceiver() {
    subject.stop();
    verify(localClipboard).removeDataReceiver(subject);
    verify(omniClipboard).removeDataReceiver(subject);
  }

  @Test
  public void stopCallsDisposeOnClipboards() {
    subject.stop();
    verify(localClipboard).dispose();
    verify(omniClipboard).dispose();
  }

  @Test
  public void isConfiguredWhenCommunicationSettingsHasChannelReturnsFalseAlsoReturnsFalse(){
    when(communicationSettings.hasChannel()).thenReturn(false);

    assertFalse(subject.isConfigured());
  }

  @Test
  public void isConfiguredWhenCommunicationSettingsHasChannelReturnsTrueAlsoReturnsTrue(){
    when(communicationSettings.hasChannel()).thenReturn(true);

    assertTrue(subject.isConfigured());
  }
}

package com.omnipasteapp.omniclipboard;

import com.omnipasteapp.omniclipboard.OmniClipboard;
import com.omnipasteapp.omniclipboard.api.IOmniApi;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class OmniClipboardTest extends TestCase {
  @Mock
  private IConfigurationService mockConfigurationService;

  @Mock
  private IOmniApi mockOmniApi;

  @Mock
  private CommunicationSettings mockCommunicationSettings;

  @Mock
  private IMessagingService mockMessagingService;

  private OmniClipboard subject;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new OmniClipboard(mockConfigurationService, mockOmniApi, mockMessagingService);

    when(mockConfigurationService.getCommunicationSettings()).thenReturn(mockCommunicationSettings);

  }

  public void testInitializeReturnsNewThread() {
    Thread result = subject.initialize();

    assertNotNull(result);
  }

  public void testSaveClippingSucceded() {
    subject.run();

    subject.saveClippingSucceeded();

    verify(mockMessagingService).sendAsync(anyString(), eq("NewMessage"), eq(subject));
  }

  public void testRunAlwaysCallsConfigurationServiceGetCommunicationSettings() {
    subject.run();

    verify(mockConfigurationService).getCommunicationSettings();
  }

  public void testRunAlwaysCallsMessagingServiceConnect() {
    subject.run();

    verify(mockMessagingService).connect(anyString(), eq(subject));
  }

  public void testGetChannelAlwaysCallsCommunicationSettingsGetChannel() {
    subject.run();
    when(mockCommunicationSettings.getChannel()).thenReturn("test-channel");

    String channel = subject.getChannel();

    assertEquals("test-channel", channel);
  }

  public void testSuccessCallbackAlwaysCallsOmniApiGetLastClipping() {
    subject.messageReceived("NewMessage");

    verify(mockOmniApi).getLastClippingAsync(eq(subject));
  }

  public void testHandleClippingCallsDataReceivedOnReceivers() {
    ICanReceiveData dataReceiver = mock(ICanReceiveData.class);
    subject.addDataReceiver(dataReceiver);

    subject.handleClipping("test");

    verify(dataReceiver).dataReceived(any(IClipboardData.class));
  }
}

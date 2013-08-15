package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.api.IOmniApi;
import com.omnipasteapp.messaging.IMessagingService;
import com.omnipasteapp.messaging.IPubNubClientFactory;
import com.omnipasteapp.messaging.IPubNubMessageBuilder;
import com.omnipasteapp.messaging.IPubnub;
import com.omnipasteapp.messaging.MessageSentCallback;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.pubnub.api.PubnubException;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Hashtable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class PubNubClipboardTest extends TestCase {
  @Mock
  private IConfigurationService mockConfigurationService;

  @Mock
  private IOmniApi mockOmniApi;

  @Mock
  private CommunicationSettings mockCommunicationSettings;

  @Mock
  private IMessagingService mockMessagingService;

  private PubNubClipboard subject;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new PubNubClipboard(mockConfigurationService, mockOmniApi, mockMessagingService);

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

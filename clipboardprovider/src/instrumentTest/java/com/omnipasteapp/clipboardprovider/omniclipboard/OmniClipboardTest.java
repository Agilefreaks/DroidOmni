package com.omnipasteapp.clipboardprovider.omniclipboard;

import com.omnipasteapp.clipboardprovider.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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

  public void testSaveClippingSucceeded() {
    subject.run();

    subject.saveClippingSucceeded();

    verify(mockMessagingService).sendAsync(anyString(), anyString(), eq(subject));
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

  public void testMessageReceivedWhenOtherMessageUuidWillCallGetLastAsync() {
    IClippings mockClippings = mock(Clippings.class);
    when(mockOmniApi.clippings()).thenReturn(mockClippings);

    subject.setMessageUuid("same");
    subject.messageReceived("other");

    verify(mockClippings).getLastAsync(eq(subject));
  }

  public void testMessageReceivedWhenSameMessageUuidWillNotCallGetLastAsync() {
    IClippings mockClippings = mock(Clippings.class);
    when(mockOmniApi.clippings()).thenReturn(mockClippings);

    subject.setMessageUuid("same");
    subject.messageReceived("same");

    verifyZeroInteractions(mockClippings);
  }

  public void testHandleClippingCallsDataReceivedOnReceivers() {
    ICanReceiveData dataReceiver = mock(ICanReceiveData.class);
    subject.addDataReceiver(dataReceiver);

    subject.handleClipping(new Clipping("token", "content"));

    verify(dataReceiver).dataReceived(any(Clipping.class));
  }
}

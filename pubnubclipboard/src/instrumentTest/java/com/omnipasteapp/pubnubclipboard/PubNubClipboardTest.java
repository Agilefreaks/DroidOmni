package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.pubnub.api.PubnubException;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Hashtable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class PubNubClipboardTest extends TestCase {
  @Mock
  private IConfigurationService mockConfigurationService;

  @Mock
  private IPubNubClientFactory mockPubNubClientFactory;

  @Mock
  private IPubNubMessageBuilder mockPubNubMessageBuilder;

  @Mock
  private CommunicationSettings mockCommunicationSettings;

  @Mock
  private IPubnub mockPubnub;

  private PubNubClipboard subject;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new PubNubClipboard(mockConfigurationService, mockPubNubClientFactory, mockPubNubMessageBuilder);

    when(mockPubNubClientFactory.create()).thenReturn(mockPubnub);
    when(mockConfigurationService.getCommunicationSettings()).thenReturn(mockCommunicationSettings);
    when(mockPubNubMessageBuilder.setChannel(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.addValue(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.build()).thenReturn(new Hashtable<String, String>());
  }

  public void testInitializeReturnsNewThread() {
    Thread result = subject.initialize();

    assertThat(result, is(notNullValue()));
  }

  public void testPutDataAlwaysCallsPubnubPublish() {
    subject.run();

    subject.putData("data");

    verify(mockPubnub).publish(any(Hashtable.class), any(MessageSentCallback.class));
  }

  public void testRunAlwaysCallsConfigurationServiceGetCommunicationSettings() {
    subject.run();

    verify(mockConfigurationService).getCommunicationSettings();
  }

  public void testRunAlwaysCallsPubNubClientFactoryCreate() {
    subject.run();

    verify(mockPubNubClientFactory).create();
  }

  public void testRunAlwaysCallsPubnubSubscribe() {
    subject.run();

    try {
      verify(mockPubnub).subscribe(any(Hashtable.class), any(MessageSentCallback.class));
    } catch (PubnubException e) {
      e.printStackTrace();
    }
  }

  public void testDisposeAlwaysCallsPubnubShutdown() {
    subject.run();

    subject.dispose();

    verify(mockPubnub).shutdown();
  }

  public void testGetChannelAlwaysCallsCommunicationSettingsGetChannel() {
    subject.run();
    when(mockCommunicationSettings.getChannel()).thenReturn("test-channel");

    String channel = subject.getChannel();

    assertEquals("test-channel", channel);
  }
}

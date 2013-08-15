package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.messaging.IMessageHandler;
import com.omnipasteapp.messaging.IPubNubClientFactory;
import com.omnipasteapp.messaging.IPubNubMessageBuilder;
import com.omnipasteapp.messaging.IPubnub;
import com.omnipasteapp.messaging.MessageReceivedCallback;
import com.omnipasteapp.messaging.PubNubMessagingService;
import com.pubnub.api.PubnubException;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Hashtable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PubNubMessagingServiceTests extends TestCase {
  private PubNubMessagingService subject;

  @Mock
  private IPubNubClientFactory mockPubNubClientFactory;

  @Mock
  private IPubNubMessageBuilder mockPubNubMessageBuilder;

  @Mock
  private IPubnub mockPubnub;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    when(mockPubNubMessageBuilder.setChannel(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.addValue(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.build()).thenReturn(new Hashtable<String, String>());
    when(mockPubNubClientFactory.create()).thenReturn(mockPubnub);

    subject = new PubNubMessagingService(mockPubNubClientFactory, mockPubNubMessageBuilder);
  }

  public void testConnectAlwaysCallsPubNubClientFactoryCreate() {
    IMessageHandler mockMessageHandler = mock(IMessageHandler.class);

    subject.connect("channel", mockMessageHandler);

    verify(mockPubNubClientFactory).create();
  }

  public void testConnectAlwaysCallsSubscribe() {
    IMessageHandler mockMessageHandler = mock(IMessageHandler.class);

    subject.connect("channel", mockMessageHandler);

    try {
      verify(mockPubnub).subscribe(any(Hashtable.class), any(MessageReceivedCallback.class));
    } catch (PubnubException e) {
      assertFalse(true);
    }
  }

  public void testDisconnectAlwaysCallsPubnubShutdown() {
    IMessageHandler mockMessageHandler = mock(IMessageHandler.class);
    subject.connect("test", mockMessageHandler);

    subject.disconnect("test");

    verify(mockPubnub).unsubscribe("test");
  }
}

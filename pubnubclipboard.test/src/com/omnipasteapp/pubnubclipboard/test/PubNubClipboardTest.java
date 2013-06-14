package com.omnipasteapp.pubnubclipboard.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.pubnubclipboard.*;
import com.pubnub.api.PubnubException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class PubNubClipboardTest {
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

  private class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IConfigurationService.class).toInstance(mockConfigurationService);
      bind(IPubNubClientFactory.class).toInstance(mockPubNubClientFactory);
      bind(IPubNubMessageBuilder.class).toInstance(mockPubNubMessageBuilder);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    when(mockPubNubClientFactory.create()).thenReturn(mockPubnub);
    when(mockConfigurationService.getCommunicationSettings()).thenReturn(mockCommunicationSettings);
    when(mockPubNubMessageBuilder.setChannel(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.addValue(anyString())).thenReturn(mockPubNubMessageBuilder);
    when(mockPubNubMessageBuilder.build()).thenReturn(new Hashtable<String, String>());

    subject = RoboGuice.getInjector(Robolectric.application).getInstance(PubNubClipboard.class);
  }

  @Test
  public void initializeReturnsNewThread(){
    Thread result = subject.initialize();

    Assert.assertNotNull(result);
  }

  @Test
  public void putDataAlwaysCallsPubnubPublish(){
    subject.run();

    subject.putData("data");

    verify(mockPubnub).publish(any(Hashtable.class), any(MessageSentCallback.class));
  }

  @Test
  public void runAlwaysCallsConfigurationServiceGetCommunicationSettings(){
    subject.run();

    verify(mockConfigurationService).getCommunicationSettings();
  }

  @Test
  public void runAlwaysCallsPubNubClientFactoryCreate(){
    subject.run();

    verify(mockPubNubClientFactory).create();
  }

  @Test
  public void runAlwaysCallsPubnubSubscribe(){
    subject.run();

    try {
      verify(mockPubnub).subscribe(any(Hashtable.class), any(MessageSentCallback.class));
    } catch (PubnubException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void disposeAlwaysCallsPubnubShutdown(){
    subject.run();

    subject.dispose();

    verify(mockPubnub).shutdown();
  }

  @Test
  public void getChannelAlwaysCallsCommunicationSettingsGetChannel(){
    subject.run();
    when(mockCommunicationSettings.getChannel()).thenReturn("test-channel");

    String channel = subject.getChannel();

    assertEquals("test-channel", channel);
  }
}

package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniServiceTest extends TestCase {

  private OmniService subject;

  @Mock
  private ILocalClipboard localClipboard;

  @Mock
  private IOmniClipboard omniClipboard;

  @Mock
  private IConfigurationService configurationService;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new OmniService(localClipboard, omniClipboard);
    subject.configurationService = configurationService;
  }

  public void testGetLocalClipboardReturnsTheLocalClipboard() {
    assertThat(subject.getLocalClipboard(), instanceOf(ILocalClipboard.class));
  }

  public void testGetOmniClipboardReturnsTheOmniClipboard() {
    assertThat(subject.getOmniClipboard(), instanceOf(IOmniClipboard.class));
  }

  public void testDataReceivedCallSendDataOnOmniClipboardWhenSenderIsLocalClipboard() {
    subject.dataReceived(new ClipboardData(localClipboard, "42"));

    verify(omniClipboard).putData("42");
  }

  public void testDataReceivedCallsSendDataOnLocalClipboardWhenSenderIsOmniClipboard() {
    subject.dataReceived(new ClipboardData(omniClipboard, "43"));

    verify(localClipboard).putData("43");
  }

  public void testDataReceivedDoesNotCallPutDataWhenDataReceivedIsTheSameAsOldDataReceived() {
    subject.dataReceived(new ClipboardData(omniClipboard, "42"));
    subject.dataReceived(new ClipboardData(localClipboard, "42"));

    verify(localClipboard).putData("42");
    verify(omniClipboard, never()).putData("42");
  }

  public void testDataReceivedDoesNotCallPutDataWhenDataReceivedIsEmpty() {
    subject.dataReceived(new ClipboardData(omniClipboard, ""));

    verify(localClipboard, never()).putData("");
  }

  public void testStopRemovesDataReceiver() {
    subject.stop();

    verify(localClipboard).removeDataReceiver(subject);
    verify(omniClipboard).removeDataReceiver(subject);
  }

  public void testStopCallsDisposeOnClipboards() {
    subject.stop();

    verify(localClipboard).dispose();
    verify(omniClipboard).dispose();
  }

  public void testIsConfiguredReturnsFalseWhenCommunicationSettingsHasChannelReturnsFalse() {
    CommunicationSettings communicationSettings = mock(CommunicationSettings.class);

    when(communicationSettings.hasChannel()).thenReturn(false);
    when(configurationService.getCommunicationSettings()).thenReturn(communicationSettings);

    assertFalse(subject.isConfigured());
  }

  public void testIsConfiguredReturnsTrueWhenCommunicationSettingsHasChannelReturnsTrue() {
    CommunicationSettings communicationSettings = mock(CommunicationSettings.class);

    when(communicationSettings.hasChannel()).thenReturn(true);
    when(configurationService.getCommunicationSettings()).thenReturn(communicationSettings);

    assertTrue(subject.isConfigured());
  }

  public void testAddListenerWillNotFail() {
    subject.addListener(new ICanReceiveData() {
      @Override
      public void dataReceived(IClipboardData clipboardData) {
      }
    });
  }

  public void testWhenSenderIsOmniCallsCanReceiveDataOnlyOnce() {
    ICanReceiveData dataReceiver = mock(ICanReceiveData.class);
    subject.addListener(dataReceiver);
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        omniClipboard.putData("test");
        return null;
      }
    }).when(localClipboard).putData(anyString());

    subject.dataReceived(new ClipboardData(omniClipboard, "test"));

    verify(dataReceiver, times(1)).dataReceived(any(IClipboardData.class));
  }
}
package com.omnipasteapp.clipboardprovider;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClipboardProviderTest extends TestCase {

  private ClipboardProvider subject;

  @Mock
  private ILocalClipboard localClipboard;

  @Mock
  private IOmniClipboard omniClipboard;

  @Mock
  private IConfigurationService configurationService;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new ClipboardProvider(localClipboard, omniClipboard);
    subject.configurationService = configurationService;
  }

  public void testGetLocalClipboardReturnsTheLocalClipboard() {
    assertTrue(subject.getLocalClipboard() != null);
  }

  public void testGetOmniClipboardReturnsTheOmniClipboard() {
    assertTrue(subject.getOmniClipboard() != null);
  }

  public void testDataReceivedCallPutDataOnOmniClipboardWhenSenderIsLocalClipboard() {
    subject.dataReceived(new Clipping("", "42", Sender.Local));

    verify(omniClipboard).putData("42");
  }

  public void testDataReceivedCallsPutDataOnLocalClipboardWhenSenderIsOmniClipboard() {
    subject.dataReceived(new Clipping("", "43", Sender.Omni));

    verify(localClipboard).putData("43");
  }

  public void testDataReceivedDoesNotCallPutDataWhenDataReceivedIsTheSameAsOldDataReceived() {
    subject.dataReceived(new Clipping("", "42", Sender.Local));
    subject.dataReceived(new Clipping("", "42", Sender.Local));

    verify(omniClipboard, times(1)).putData("42");
  }

  public void testDataReceivedDoesNotCallPutDataWhenDataReceivedIsEmpty() {
    subject.dataReceived(new Clipping("", "", Sender.Local));

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

  public void testAddListenerWillNotFail() {
    subject.addListener(new ICanReceiveData() {
      @Override
      public void dataReceived(Clipping clipping) {
      }
    });
  }

  public void testDataReceivedCallsMethodOnRegisteredDataReceiver() {
    ICanReceiveData dataReceiver = mock(ICanReceiveData.class);
    subject.addListener(dataReceiver);
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        omniClipboard.putData("test");
        return null;
      }
    }).when(localClipboard).putData(anyString());

    subject.dataReceived(new Clipping("", "some content", Sender.Omni));

    verify(dataReceiver, times(1)).dataReceived(any(Clipping.class));
  }

  public void testHandleWithFromAndToDifferentWillCallOmniClipboardFetch() {
    subject.handle("123", "1234s");

    verify(omniClipboard).fetchClipping();
  }

  public void testHandleWithFromAndToEqualWillNotCallOmniClipboardFetch() {
    subject.handle("123", "123");

    verify(omniClipboard, never()).fetchClipping();
  }
}

package com.omnipasteapp.omnicommon.test.services;

import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.services.OmniService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class OmniServiceTest {
  private OmniService subject;
  private ILocalClipboard localClipboard;
  private IOmniClipboard omniClipboard;

  @Before
  public void Setup() {
    localClipboard = mock(ILocalClipboard.class);
    omniClipboard = mock(IOmniClipboard.class);
    subject = new OmniService(localClipboard, omniClipboard);
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
}

package com.omnipasteapp.omnimessaging;

import android.os.Bundle;

import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class GoogleMessagingServiceTest extends TestCase {
  @Mock
  private IClipboardProvider mockClipboardProvider;

  @Mock
  private IConfigurationService mockConfigurationService;

  @Mock
  private IOmniApi mockOmniApi;

  private GoogleMessagingService subject;

  protected void setUp() {
    MockitoAnnotations.initMocks(this);

    subject = new GoogleMessagingService(mockConfigurationService, null, null);
    subject.clipboardProvider = mockClipboardProvider;
  }

  public void testHandleMessageWillCallMessageReceive() {
    Bundle bundle = new Bundle();
    bundle.putString("registration_id", "123");
    subject.handleMessage(bundle);

    verify(mockClipboardProvider).messageReceived("123", null);
  }
}

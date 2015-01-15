package com.omnipaste.phoneprovider.actions;

import android.telephony.SmsManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.dto.SmsMessageDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SmsMessageInitiateTest extends InstrumentationTestCase {
  private SmsMessageInitiate subject;
  private SmsManager mockSmsManager;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockSmsManager = mock(SmsManager.class);
    subject = (SmsMessageInitiate) SmsMessageInitiate.build(SmsMessageInitiate.class, null)
      .setSmsManager(mockSmsManager);
  }

  public void testSend() throws Exception {
    subject.execute(new SmsMessageDto().setContent("content").setPhoneNumber("+123"));

    verify(mockSmsManager).sendTextMessage("+123", null, "content", null, null);
  }
}
package com.omnipaste.phoneprovider.actions;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SmsMessageTest extends InstrumentationTestCase {
  private SmsMessage subject;
  private SmsMessages mockSmsMessages;
  private SmsManager mockSmsManager;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockSmsMessages = mock(SmsMessages.class);
    mockSmsManager = mock(SmsManager.class);
    subject = (SmsMessage) SmsMessage.build(SmsMessage.class, null)
      .setSmsMessages(mockSmsMessages)
      .setSmsManager(mockSmsManager);
  }

  public void testSend() throws Exception {
    PublishSubject<SmsMessageDto> smsMessageDtoPublishSubject = PublishSubject.create();
    when(mockSmsMessages.get("42")).thenReturn(smsMessageDtoPublishSubject);

    Bundle extras = new Bundle();
    extras.putString("id", "42");
    subject.execute(extras);
    smsMessageDtoPublishSubject.onNext(new SmsMessageDto().setContent("content").setPhoneNumber("+123"));

    verify(mockSmsManager).sendTextMessage("+123", null, "content", null, null);
  }
}
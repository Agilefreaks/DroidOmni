package com.omnipaste.phoneprovider.actions;

import android.telephony.SmsManager;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendSmsMessageRequestedTest {
  private SendSmsMessageRequested sendSmsMessageRequested;

  @Mock NotificationProvider mockNotificationProvider;
  @Mock SmsManager mockSmsManager;
  @Mock SmsMessages mockSmsMessages;

  @Before
  public void context() {
    sendSmsMessageRequested = new SendSmsMessageRequested(mockNotificationProvider);

    when(mockNotificationProvider.getObservable()).thenReturn(Observable.<NotificationDto>empty());
    sendSmsMessageRequested.init("deviceId");

    sendSmsMessageRequested.setSmsManager(mockSmsManager);
    sendSmsMessageRequested.setSmsMessages(mockSmsMessages);

  }

  @Test
  public void executeWillSendMessageAndPatchApi() {
    PublishSubject<SmsMessageDto> smsMessagesObserver = PublishSubject.create();
    when(mockSmsMessages.get("42")).thenReturn(smsMessagesObserver);

    sendSmsMessageRequested.execute(new NotificationDto(NotificationDto.Type.SEND_SMS_MESSAGE_REQUESTED, "42"));
    smsMessagesObserver.onNext(new SmsMessageDto().setId("42").setPhoneNumber("+123").setContent("test"));

    verify(mockSmsManager).sendMultipartTextMessage("+123", null, new ArrayList<String>(), null, null);
    verify(mockSmsMessages).markAsSent("deviceId", "42");
  }
}

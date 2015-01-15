package com.omnipaste.phoneprovider.actions;

import android.telephony.SmsManager;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.Utils;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class SendSmsMessageRequested {
  private final NotificationProvider notificationProvider;
  private final SmsMessages smsMessages;
  private final SmsManager smsManager;
  private Subscription subscription;

  @Inject
  public SendSmsMessageRequested(
    NotificationProvider notificationProvider,
    SmsMessages smsMessages,
    SmsManager smsManager) {
    this.notificationProvider = notificationProvider;
    this.smsMessages = smsMessages;
    this.smsManager = smsManager;
  }

  public void init() {
    if (subscription != null) {
      return;
    }

    subscription = notificationProvider
      .getObservable()
      .filter(new Func1<NotificationDto, Boolean>() {
        @Override
        public Boolean call(NotificationDto notificationDto) {
          return notificationDto.getType() == NotificationDto.Type.SEND_SMS_MESSAGE_REQUESTED;
        }
      }).subscribe(
        new Action1<NotificationDto>() {
          @Override
          public void call(NotificationDto notificationDto) {
            smsMessages.get(notificationDto.getId()).subscribe(new Action1<SmsMessageDto>() {
              @Override
              public void call(SmsMessageDto smsMessageDto) {
                sendSmsMessage(smsMessageDto);
              }
            });
          }
        }
      );
  }

  public void destroy() {
    subscription.unsubscribe();
    subscription = null;
  }

  private void sendSmsMessage(SmsMessageDto smsMessageDto) {
    String content = Utils.firstNotNuLL(smsMessageDto.getContent(), "");
    List<String> contentList = Utils.firstNotNuLL(smsMessageDto.getContentList(), new ArrayList<String>());
    String phoneNumber = Utils.firstNotNuLL(smsMessageDto.getPhoneNumber(), "");
    List<String> phoneNumberList = Utils.firstNotNuLL(smsMessageDto.getPhoneNumberList(), new ArrayList<String>());

    if (!content.isEmpty() && !phoneNumber.isEmpty()) {
      send(content, phoneNumber);
    } else if (!content.isEmpty() && !phoneNumberList.isEmpty()) {
      sendToMany(content, phoneNumberList);
    } else if (!contentList.isEmpty() && !phoneNumber.isEmpty()) {
      sendMany(contentList, phoneNumber);
    } else if (!contentList.isEmpty() && !phoneNumberList.isEmpty()) {
      sendManyToMany(contentList, phoneNumberList);
    }
  }

  private void sendManyToMany(List<String> contentList, List<String> phoneNumberList) {
    for (int i = 0; i < contentList.size(); i++) {
      send(contentList.get(i), phoneNumberList.get(i));
    }
  }

  private void sendMany(List<String> contentList, String phoneNumber) {
    for (String content : contentList) {
      send(content, phoneNumber);
    }
  }

  private void sendToMany(String content, List<String> phoneNumberList) {
    for (String phoneNumber : phoneNumberList) {
      send(content, phoneNumber);
    }
  }

  private void send(String content, String phoneNumber) {
    ArrayList<String> msgTexts = smsManager.divideMessage(content);
    smsManager.sendMultipartTextMessage(phoneNumber, null, msgTexts, null, null);
  }
}

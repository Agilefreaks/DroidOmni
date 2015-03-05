package com.omnipaste.phoneprovider.actions;

import android.telephony.SmsManager;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.Utils;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.NotificationFilter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class SendSmsMessageRequested extends NotificationFilter {
  private final SmsMessages smsMessages;
  private final SmsManager smsManager;

  @Inject
  public SendSmsMessageRequested(NotificationProvider notificationProvider,
                                 SmsMessages smsMessages,
                                 SmsManager smsManager) {
    super(notificationProvider);
    this.smsMessages = smsMessages;
    this.smsManager = smsManager;
  }

  @Override
  protected NotificationDto.Type getType() {
    return NotificationDto.Type.SEND_SMS_MESSAGE_REQUESTED;
  }

  @Override
  protected void execute(NotificationDto notificationDto) {
    smsMessages.get(notificationDto.getId()).subscribe(new Action1<SmsMessageDto>() {
      @Override
      public void call(SmsMessageDto smsMessageDto) {
        sendSmsMessage(smsMessageDto);
      }
    });
  }

  private void sendSmsMessage(SmsMessageDto smsMessageDto) {
    String id = smsMessageDto.getId();
    String content = Utils.firstNotNuLL(smsMessageDto.getContent(), "");
    List<String> contentList = Utils.firstNotNuLL(smsMessageDto.getContentList(), new ArrayList<String>());
    String phoneNumber = Utils.firstNotNuLL(smsMessageDto.getPhoneNumber(), "");
    List<String> phoneNumberList = Utils.firstNotNuLL(smsMessageDto.getPhoneNumberList(), new ArrayList<String>());

    if (!content.isEmpty() && !phoneNumber.isEmpty()) {
      send(id, content, phoneNumber);
    } else if (!content.isEmpty() && !phoneNumberList.isEmpty()) {
      sendToMany(id, content, phoneNumberList);
    } else if (!contentList.isEmpty() && !phoneNumber.isEmpty()) {
      sendMany(id, contentList, phoneNumber);
    } else if (!contentList.isEmpty() && !phoneNumberList.isEmpty()) {
      sendManyToMany(id, contentList, phoneNumberList);
    }
  }

  private void sendManyToMany(String id, List<String> contentList, List<String> phoneNumberList) {
    for (int i = 0; i < contentList.size(); i++) {
      send(id, contentList.get(i), phoneNumberList.get(i));
    }
  }

  private void sendMany(String id, List<String> contentList, String phoneNumber) {
    for (String content : contentList) {
      send(id, content, phoneNumber);
    }
  }

  private void sendToMany(String id, String content, List<String> phoneNumberList) {
    for (String phoneNumber : phoneNumberList) {
      send(id, content, phoneNumber);
    }
  }

  private void send(String id, String content, String phoneNumber) {
    ArrayList<String> msgTexts = smsManager.divideMessage(content);
    smsManager.sendMultipartTextMessage(phoneNumber, null, msgTexts, null, null);
    smsMessages.markAsSent(deviceId, id).subscribe();
  }
}

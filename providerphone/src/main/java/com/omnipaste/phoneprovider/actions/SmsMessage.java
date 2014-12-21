package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.os.Bundle;

import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.util.List;

import rx.functions.Action1;

public class SmsMessage extends Action {
  public SmsMessage(Context context) {
    super(context);
  }

  @Override
  public void execute(Bundle extras) {
    String id = extras.getString("id");

    smsMessages
      .get(id)
      .subscribe(
        new Action1<SmsMessageDto>() {
          @Override
          public void call(SmsMessageDto smsMessageDto) {
            String content = smsMessageDto.getContent();
            List<String> contentList = smsMessageDto.getContentList();
            String phoneNumber = smsMessageDto.getPhoneNumber();
            List<String> phoneNumberList = smsMessageDto.getPhoneNumberList();

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
        }
      );
  }

  private void sendManyToMany(List<String> contentList, List<String> phoneNumberList) {
    for(int i = 0; i < contentList.size(); i++) {
      send(contentList.get(i), phoneNumberList.get(i));
    }
  }

  private void sendMany(List<String> contentList, String phoneNumber) {
    for(String content : contentList) {
      send(content, phoneNumber);
    }
  }

  private void sendToMany(String content, List<String> phoneNumberList) {
    for(String phoneNumber : phoneNumberList) {
      send(content, phoneNumber);
    }
  }

  private void send(String content, String phoneNumber) {
    smsManager.sendTextMessage(phoneNumber, null, content, null, null);
  }
}

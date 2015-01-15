package com.omnipaste.phoneprovider.actions;

import android.content.Context;

import com.omnipaste.omnicommon.Utils;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.util.ArrayList;
import java.util.List;

public class SmsMessageInitiate extends SmsMessageAction {
  public SmsMessageInitiate(Context context) {
    super(context);
  }

  @Override
  public void execute(SmsMessageDto smsMessageDto) {
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

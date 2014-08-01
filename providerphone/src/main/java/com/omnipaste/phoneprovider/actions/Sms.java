package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Sms extends Action {
  private static final String PHONE_NUMBER_KEY = "phone_number";
  private static final String CONTENT_KEY = "sms_content";

  public Sms(Context context) {
    super(context);
  }

  @Override
  public void execute(Bundle extras) {
    String phoneNumber = extras.getString(PHONE_NUMBER_KEY);
    String content = extras.getString(CONTENT_KEY);

    smsManager.sendTextMessage(phoneNumber, null, content, null, null);
  }
}

package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Call extends Action {
  private static final String PHONE_NUMBER_KEY = "phone_number";

  public Call(Context context) {
    super(context);
  }

  @Override
  public void execute(Bundle extras) {
      Intent intent = new Intent(Intent.ACTION_CALL);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setData(Uri.parse("tel:" + extras.get(PHONE_NUMBER_KEY)));
      context.startActivity(intent);
  }
}

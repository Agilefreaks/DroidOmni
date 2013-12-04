package com.omnipasteapp.phoneprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipasteapp.omnicommon.interfaces.IPhoneProvider;

import javax.inject.Inject;

public class PhoneProvider implements IPhoneProvider {

  private Context context;

  @Inject
  public PhoneProvider(Context context) {
    this.context = context;
  }

  @Override
  public void handle(String fromRegistrationId, String toRegistrationId) {
  }

  @Override
  public void call(String number) {
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setData(Uri.parse("tel:" + number));
    context.startActivity(intent);
  }
}

package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omnicommon.dto.PhoneCallDto;

public class PhoneCallInitiate extends PhoneCallAction {
  public PhoneCallInitiate(Context context) {
    super(context);
  }

  @Override
  public void execute(PhoneCallDto phoneCallDto) {
      Intent intent = new Intent(Intent.ACTION_CALL);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setData(Uri.parse("tel:" + phoneCallDto.getNumber()));
      context.startActivity(intent);
  }
}

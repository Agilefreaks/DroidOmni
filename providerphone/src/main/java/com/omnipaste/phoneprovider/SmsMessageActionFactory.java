package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.SmsManager;

import com.omnipaste.phoneprovider.actions.SmsMessageAction;
import com.omnipaste.phoneprovider.actions.SmsMessageInitiate;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SmsMessageActionFactory {
  private final Context context;
  private final SmsManager smsManager;

  @Inject
  public SmsMessageActionFactory(Context context, SmsManager smsManager) {
    this.context = context;
    this.smsManager = smsManager;
  }

  public SmsMessageAction create(SmsMessageState smsMessageState) {
    SmsMessageAction result = null;

    switch (smsMessageState) {
      case INITIATE:
        result = SmsMessageAction.build(SmsMessageInitiate.class, context).setSmsManager(smsManager);
        break;
      case UNKNOWN:
        break;
    }

    return result;
  }
}

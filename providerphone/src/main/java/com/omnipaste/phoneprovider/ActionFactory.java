package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.phoneprovider.actions.Action;
import com.omnipaste.phoneprovider.actions.Call;
import com.omnipaste.phoneprovider.actions.EndCall;
import com.omnipaste.phoneprovider.actions.Sms;
import com.omnipaste.phoneprovider.actions.SmsMessage;
import com.omnipaste.phoneprovider.actions.Unknown;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActionFactory {
  private final Context context;
  private final TelephonyManager telephonyManager;
  private final SmsManager smsManager;
  private final SmsMessages smsMessages;

  @Inject
  public ActionFactory(Context context, TelephonyManager telephonyManager, SmsManager smsManager, SmsMessages smsMessages) {
    this.context = context;
    this.telephonyManager = telephonyManager;
    this.smsManager = smsManager;
    this.smsMessages = smsMessages;
  }

  public Action create(PhoneAction phoneAction) {
    Action result = null;

    switch (phoneAction) {
      case CALL:
        result = Action.build(Call.class, context);
        break;
      case END_CALL:
        result = Action.build(EndCall.class, context).setTelephonyManager(telephonyManager);
        break;
      case SMS:
        result = Action.build(Sms.class, context).setSmsManager(smsManager);
        break;
      case SMS_MESSAGE:
        result = Action.build(SmsMessage.class, context).setSmsManager(smsManager).setSmsMessages(smsMessages);
        break;
      case UNKNOWN:
        result = Action.build(Unknown.class, context);
        break;
    }

    return result;
  }
}

package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.omnipaste.phoneprovider.actions.Action;
import com.omnipaste.phoneprovider.actions.Call;
import com.omnipaste.phoneprovider.actions.EndCall;
import com.omnipaste.phoneprovider.actions.Factory;
import com.omnipaste.phoneprovider.actions.Sms;
import com.omnipaste.phoneprovider.actions.Unknown;

import javax.inject.Inject;

public class ActionFactory implements Factory {
  private Context context;
  private TelephonyManager telephonyManager;
  private SmsManager smsManager;

  @Inject
  public ActionFactory(Context context, TelephonyManager telephonyManager, SmsManager smsManager) {
    this.context = context;
    this.telephonyManager = telephonyManager;
    this.smsManager = smsManager;
  }

  @Override
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
      case UNKNOWN:
        result = Action.build(Unknown.class, context);
        break;
    }

    return result;
  }
}

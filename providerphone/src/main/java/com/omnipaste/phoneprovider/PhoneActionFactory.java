package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.omnipaste.phoneprovider.actions.PhoneCallAction;
import com.omnipaste.phoneprovider.actions.PhoneCallEndCall;
import com.omnipaste.phoneprovider.actions.PhoneCallInitiate;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhoneActionFactory {
  private final Context context;
  private final TelephonyManager telephonyManager;

  @Inject
  public PhoneActionFactory(Context context, TelephonyManager telephonyManager) {
    this.context = context;
    this.telephonyManager = telephonyManager;
  }

  public com.omnipaste.phoneprovider.actions.PhoneCallAction create(PhoneCallState phoneCallState) {
    PhoneCallAction result = null;

    switch (phoneCallState) {
      case INITIATE:
        result = PhoneCallAction.build(PhoneCallInitiate.class, context);
        break;
      case END_CALL:
        result = PhoneCallAction.build(PhoneCallEndCall.class, context).setTelephonyManager(telephonyManager);
        break;
      case UNKNOWN:
        break;
    }

    return result;
  }
}

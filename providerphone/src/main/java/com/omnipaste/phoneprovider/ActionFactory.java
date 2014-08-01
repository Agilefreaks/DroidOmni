package com.omnipaste.phoneprovider;

import android.content.Context;

import com.omnipaste.omnicommon.domain.PhoneAction;
import com.omnipaste.phoneprovider.actions.Action;
import com.omnipaste.phoneprovider.actions.Call;
import com.omnipaste.phoneprovider.actions.EndCall;
import com.omnipaste.phoneprovider.actions.Factory;
import com.omnipaste.phoneprovider.actions.Unknown;

import javax.inject.Inject;

public class ActionFactory implements Factory {
  private Context context;

  @Inject
  public ActionFactory(Context context) {
    this.context = context;
  }

  @Override
  public Action create(PhoneAction phoneAction) {
    Action result = null;

    switch (phoneAction) {
      case CALL:
        result = Action.build(Call.class, context);
        break;
      case END_CALL:
        result = Action.build(EndCall.class, context);
        break;
      case UNKNOWN:
        result = Action.build(Unknown.class, context);
        break;
    }

    return result;
  }
}

package com.omnipaste.droidomni.factory;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.service.smartaction.SmartAction;
import com.omnipaste.droidomni.service.smartaction.SmartActionAddress;
import com.omnipaste.droidomni.service.smartaction.SmartActionPhoneNumber;
import com.omnipaste.droidomni.service.smartaction.SmartActionRemove;
import com.omnipaste.droidomni.service.smartaction.SmartActionWebSite;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SmartActionFactory {
  public static final HashMap<ClippingDto.ClippingType, SmartAction> SMART_ACTIONS = new HashMap<ClippingDto.ClippingType, SmartAction>() {{
    put(ClippingDto.ClippingType.PHONE_NUMBER, new SmartActionPhoneNumber());
    put(ClippingDto.ClippingType.URL, new SmartActionWebSite());
    put(ClippingDto.ClippingType.ADDRESS, new SmartActionAddress());
  }};

  private Context context;

  @Inject
  public SmartActionFactory(Context context) {
    this.context = context;
  }

  public void run(ClippingDto clippingDto) {
    try {
      context.startActivity(buildIntent(clippingDto));
    } catch (ActivityNotFoundException _exception) {
      Toast.makeText(context, R.string.smart_action_not_supported, Toast.LENGTH_LONG).show();
    }
  }

  public CharSequence getTitle(ClippingDto clippingDto) {
    return context.getText(getSmartAction(clippingDto).getTitle());
  }

  public int getIcon(ClippingDto clippingDto) {
    return getSmartAction(clippingDto).getIcon()[0];
  }

  public PendingIntent buildPendingIntent(ClippingDto clippingDto) {
    return PendingIntent.getActivity(context, 0, buildIntent(clippingDto), 0);
  }

  private Intent buildIntent(ClippingDto clippingDto) {
    return getSmartAction(clippingDto).buildIntent(clippingDto);
  }

  private SmartAction getSmartAction(ClippingDto clippingDto) {
    return SMART_ACTIONS.get(clippingDto.getType());
  }

  public NotificationCompat.Action getAction(ClippingDto clippingDto) {
    return new NotificationCompat.Action.Builder(
        getIcon(clippingDto),
        getTitle(clippingDto),
        buildPendingIntent(clippingDto)
    ).build();
  }

  public NotificationCompat.Action getRemoveAction() {
    SmartActionRemove smartActionRemove = new SmartActionRemove();

    return new NotificationCompat.Action.Builder(
        smartActionRemove.getIcon()[0],
        context.getText(smartActionRemove.getTitle()),
        smartActionRemove.buildIntent(context)
    ).build();
  }
}

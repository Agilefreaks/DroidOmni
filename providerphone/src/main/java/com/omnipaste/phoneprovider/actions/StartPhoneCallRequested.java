package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.NotificationFilter;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class StartPhoneCallRequested extends NotificationFilter {
  private final Context context;
  private final PhoneCalls phoneCalls;

  @Inject
  public StartPhoneCallRequested(NotificationProvider notificationProvider,
                                 PhoneCalls phoneCalls,
                                 Context context) {
    super(notificationProvider);
    this.phoneCalls = phoneCalls;
    this.context = context;
  }

  @Override
  protected NotificationDto.Type getType() {
    return NotificationDto.Type.START_PHONE_CALL_REQUESTED;
  }

  @Override
  protected void execute(NotificationDto notificationDto) {
    phoneCalls.get(notificationDto.getId()).subscribe(new Action1<PhoneCallDto>() {
      @Override
      public void call(PhoneCallDto phoneCallDto) {
        startPhoneCall(phoneCallDto);
      }
    });
  }

  private void startPhoneCall(PhoneCallDto phoneCallDto) {
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setData(Uri.parse("tel:" + phoneCallDto.getNumber()));
    context.startActivity(intent);

    phoneCalls.markAsStarted(deviceId, phoneCallDto.getId()).subscribe();
  }
}

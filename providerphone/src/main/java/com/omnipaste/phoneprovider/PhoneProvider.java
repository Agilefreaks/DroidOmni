package com.omnipaste.phoneprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

public class PhoneProvider implements IPhoneProvider {
  private static final String PHONE_NUMBER_KEY = "phone_number";

  private NotificationProvider notificationProvider;

  @Inject
  public PhoneProvider(NotificationProvider notificationProvider) {
    this.notificationProvider = notificationProvider;
  }

  @Override
  public Observable getObservable(final Context context) {
    notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            return notificationDto.getTarget() == Target.phone;
          }
        })
        .subscribe(new Action1<NotificationDto>() {
          @Override
          public void call(NotificationDto notificationDto) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + notificationDto.getExtra().get(PHONE_NUMBER_KEY)));
            context.startActivity(intent);
          }
        });

    return Observable.empty();
  }
}

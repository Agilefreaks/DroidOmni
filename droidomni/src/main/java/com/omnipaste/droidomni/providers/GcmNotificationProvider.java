package com.omnipaste.droidomni.providers;

import com.omnipaste.droidomni.events.GcmNotificationReceived;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.subjects.PublishSubject;

public class GcmNotificationProvider implements NotificationProvider {
  private PublishSubject<NotificationDto> notificationSubject;

  public GcmNotificationProvider() {
    EventBus eventBus = EventBus.getDefault();
    eventBus.register(this);

    notificationSubject = PublishSubject.create();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventBackgroundThread(GcmNotificationReceived gcmNotificationReceived) {
    notificationSubject.onNext(new NotificationDto(
        gcmNotificationReceived.getProvider(),
        gcmNotificationReceived.getRegistrationId(),
        gcmNotificationReceived.getExtras()));
  }

  @Override
  public Observable<NotificationDto> getObservable() {
    return notificationSubject;
  }
}

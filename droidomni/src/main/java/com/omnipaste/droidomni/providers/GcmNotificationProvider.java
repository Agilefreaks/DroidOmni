package com.omnipaste.droidomni.providers;

import com.omnipaste.droidomni.events.GcmEvent;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class GcmNotificationProvider implements NotificationProvider {
  private PublishSubject<NotificationDto> notificationSubject;

  public GcmNotificationProvider() {
    EventBus eventBus = EventBus.getDefault();
    eventBus.register(this);

    notificationSubject = PublishSubject.create();
    notificationSubject.subscribeOn(Schedulers.io());
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventBackgroundThread(GcmEvent gcmEvent) {
    notificationSubject.onNext(new NotificationDto(gcmEvent.getProvider(), gcmEvent.getRegistrationId()));
  }

  @Override
  public Observable<NotificationDto> getObservable() {
    return notificationSubject;
  }
}

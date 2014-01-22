package com.omnipaste.droidomni.providers;

import com.omnipaste.droidomni.events.GcmEvent;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.concurrency.Schedulers;
import rx.subjects.BehaviorSubject;

public class GcmNotificationProvider implements NotificationProvider {
  private BehaviorSubject<NotificationDto> notificationSubject;

  public GcmNotificationProvider() {
    EventBus eventBus = EventBus.getDefault();
    eventBus.register(this);

    notificationSubject = BehaviorSubject.create(new NotificationDto());
    notificationSubject.subscribeOn(Schedulers.threadPoolForIO());
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

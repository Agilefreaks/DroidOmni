package com.omnipaste.eventsprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EventDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class EventsProvider implements Provider<EventDto> {

  private PublishSubject<EventDto> eventsProviderSubject;
  private NotificationProvider notificationProvider;

  @Inject
  public EventsProvider(NotificationProvider notificationProvider) {
    this.notificationProvider = notificationProvider;

    eventsProviderSubject = PublishSubject.create();
  }


  @Override
  public Observable<EventDto> init(String identifier) {
    notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override public Boolean call(NotificationDto notificationDto) {
            return notificationDto.getTarget() == NotificationDto.Target.NOTIFICATION;
          }
        })
        .subscribe(
            // onNext
            new Action1<NotificationDto>() {
              @Override public void call(NotificationDto notificationDto) {
              }
            },
            // onError
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                // ignore
              }
            }
        );


    return eventsProviderSubject;
  }

  @Override
  public void destroy() {

  }
}

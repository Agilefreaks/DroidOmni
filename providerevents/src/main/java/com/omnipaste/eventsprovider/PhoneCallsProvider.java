package com.omnipaste.eventsprovider;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class PhoneCallsProvider implements Provider<PhoneCallDto> {

  private final NotificationProvider notificationProvider;
  private final PhoneCalls phoneCalls;
  private final PublishSubject<PhoneCallDto> subject;
  private Subscription subscription;

  @Inject
  public PhoneCallsProvider(NotificationProvider notificationProvider, PhoneCalls phoneCalls) {
    this.notificationProvider = notificationProvider;
    this.phoneCalls = phoneCalls;

    subject = PublishSubject.create();
  }

  @Override
  public Observable<PhoneCallDto> init(String identifier) {
    if (subscription == null) {
      subscription = notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            String type = notificationDto.getExtra().getString("type");
            String state = notificationDto.getExtra().getString("state");
            return type != null && type.equals("phone_call") &&
              state != null && state.equals("incoming");
          }
        })
        .subscribe(
          // onNext
          new Action1<NotificationDto>() {
            @Override
            public void call(NotificationDto notificationDto) {
              String id = notificationDto.getExtra().getString("id");
              phoneCalls.get(id).subscribe(
                new Action1<PhoneCallDto>() {
                  @Override
                  public void call(PhoneCallDto phoneCallDto) {
                    subject.onNext(phoneCallDto);
                  }
                }
              );
            }
          },
          // onError
          new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
              // ignore
            }
          }
        );
    }

    return subject;
  }

  @Override
  public void destroy() {
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
  }

  public Observable<PhoneCallDto> getObservable() {
    return subject;
  }
}

package com.omnipaste.phoneprovider.listeners;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.PhoneActionFactory;
import com.omnipaste.phoneprovider.PhoneCallState;
import com.omnipaste.phoneprovider.actions.PhoneCallAction;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class OmniPhoneStateListener implements Listener {
  private final NotificationProvider notificationProvider;
  private final PhoneCalls phoneCalls;
  private final PhoneActionFactory phoneActionFactory;
  private final PublishSubject<PhoneCallDto> subject;
  private Subscription subscription;

  @Inject
  public OmniPhoneStateListener(
    NotificationProvider notificationProvider,
    PhoneCalls phoneCalls,
    PhoneActionFactory phoneActionFactory) {
    this.notificationProvider = notificationProvider;
    this.phoneCalls = phoneCalls;
    this.phoneActionFactory = phoneActionFactory;

    subject = PublishSubject.create();
  }

  @Override
  public void start(String deviceId) {
    if (subscription != null) {
      return;
    }

    subscription = notificationProvider
      .getObservable()
      .filter(new Func1<NotificationDto, Boolean>() {
        @Override
        public Boolean call(NotificationDto notificationDto) {
          String type = notificationDto.getExtra().getString("type");
          return type != null && type.equals("phone_call");
        }
      })
      .subscribe(
        // onNext
        new Action1<NotificationDto>() {
          @Override
          public void call(NotificationDto notificationDto) {
            final String id = notificationDto.getExtra().getString("id");
            final PhoneCallState phoneCallState = PhoneCallState.parse(notificationDto.getExtra().getString("state"));

            phoneCalls.get(id).subscribe(
              new Action1<PhoneCallDto>() {
                @Override
                public void call(PhoneCallDto phoneCallDto) {
                  if (phoneCallState == PhoneCallState.INCOMING) {
                    subject.onNext(phoneCallDto);
                  } else {
                    PhoneCallAction action = phoneActionFactory.create(phoneCallState);
                    action.execute(phoneCallDto);
                  }
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

  @Override
  public void stop() {
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
  }

  public Observable<PhoneCallDto> getObservable() {
    return subject;
  }
}

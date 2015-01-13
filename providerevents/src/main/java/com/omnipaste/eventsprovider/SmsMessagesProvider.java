package com.omnipaste.eventsprovider;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class SmsMessagesProvider implements Provider<SmsMessageDto> {
  private final NotificationProvider notificationProvider;
  private final SmsMessages smsMessages;
  private final PublishSubject<SmsMessageDto> subject;
  private Subscription subscription;

  @Inject
  public SmsMessagesProvider(NotificationProvider notificationProvider, SmsMessages smsMessages) {
    this.notificationProvider = notificationProvider;
    this.smsMessages = smsMessages;

    subject = PublishSubject.create();
  }

  @Override
  public Observable<SmsMessageDto> init(String identifier) {
    if (subscription == null) {
      subscription = notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            String type = notificationDto.getExtra().getString("type");
            String state = notificationDto.getExtra().getString("state");
            return type != null && type.equals("sms_message") &&
              state != null && state.equals("incoming");
          }
        })
        .subscribe(
          // onNext
          new Action1<NotificationDto>() {
            @Override
            public void call(NotificationDto notificationDto) {
              String id = notificationDto.getExtra().getString("id");
              smsMessages.get(id).subscribe(
                new Action1<SmsMessageDto>() {
                  @Override
                  public void call(SmsMessageDto smsMessageDto) {
                    subject.onNext(smsMessageDto);
                  }
                },
                new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {

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

  public Observable<SmsMessageDto> getObservable() {
    return subject;
  }
}

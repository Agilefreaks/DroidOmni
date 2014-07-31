package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.actions.Action;
import com.omnipaste.phoneprovider.actions.Factory;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class AndroidPhoneProvider implements PhoneProvider {
  private static final String PHONE_ACTION_KEY = "phone_action";

  private NotificationProvider notificationProvider;
  private Factory actionFactory;
  private Boolean subscribed = false;
  private Subscription subscription;

  @Inject
  public AndroidPhoneProvider(NotificationProvider notificationProvider, Factory actionFactory) {
    this.notificationProvider = notificationProvider;
    this.actionFactory = actionFactory;
  }

  @Override
  public Observable<EmptyDto> init(final String identifier) {
    if (!subscribed) {
      subscription = notificationProvider
          .getObservable()
          .filter(new Func1<NotificationDto, Boolean>() {
            @Override
            public Boolean call(NotificationDto notificationDto) {
              return notificationDto.getTarget() == NotificationDto.Target.PHONE;
            }
          })
          .subscribe(new Action1<NotificationDto>() {
                       @Override
                       public void call(NotificationDto notificationDto) {
                         PhoneAction phoneAction = PhoneAction.parse(getPhoneAction(notificationDto));
                         Action action = actionFactory.create(phoneAction);
                         action.execute(notificationDto.getExtra());
                       }
                     },
              new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                  // ignore
                }
              });

      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    subscribed = false;
    subscription.unsubscribe();
  }

  private String getPhoneAction(NotificationDto notificationDto) {
    Object result = notificationDto.getExtra().get(PHONE_ACTION_KEY);
    return result != null ? result.toString() : "";
  }
}

package com.omnipaste.eventsprovider;

import com.omnipaste.eventsprovider.listeners.EventsReceiver;
import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.omniapi.resource.v1.Events;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TelephonyNotificationsProvider implements Provider<NotificationDto>, EventsReceiver {

  public static TelephonyNotificationsProvider instance;

  private String identifier;
  private OmniPhoneStateListener omniPhoneStateListener;
  private boolean subscribed = false;
  private Events events;

  public static TelephonyNotificationsProvider getInstance() {
    return instance;
  }

  @Inject
  public TelephonyNotificationsProvider(Events events,
                                        OmniPhoneStateListener omniPhoneStateListener) {
    this.events = events;
    this.omniPhoneStateListener = omniPhoneStateListener;

    instance = this;
  }

  @Override
  public Observable<NotificationDto> init(String identifier) {
    if (!subscribed) {
      this.identifier = identifier;
      omniPhoneStateListener.start(this);
      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void post(TelephonyEventDto telephonyEventDto) {
    telephonyEventDto.setIdentifier(identifier);
    events.create(telephonyEventDto).subscribe();
  }

  @Override
  public void destroy() {
    omniPhoneStateListener.stop();
    subscribed = false;
  }
}

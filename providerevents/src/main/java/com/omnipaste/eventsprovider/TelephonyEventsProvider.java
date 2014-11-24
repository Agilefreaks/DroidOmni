package com.omnipaste.eventsprovider;

import com.omnipaste.eventsprovider.listeners.EventsReceiver;
import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.eventsprovider.listeners.OmniSmsListener;
import com.omnipaste.omniapi.resource.v1.Events;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TelephonyEventsProvider implements Provider<NotificationDto>, EventsReceiver {

  public static TelephonyEventsProvider instance;

  private String identifier;
  private OmniPhoneStateListener omniPhoneStateListener;
  private OmniSmsListener smsListener;
  private boolean subscribed = false;
  private Events events;

  public static TelephonyEventsProvider getInstance() {
    return instance;
  }

  @Inject
  public TelephonyEventsProvider(Events events,
                                 OmniPhoneStateListener omniPhoneStateListener,
                                 OmniSmsListener smsListener) {
    this.events = events;
    this.omniPhoneStateListener = omniPhoneStateListener;
    this.smsListener = smsListener;

    instance = this;
  }

  @Override
  public Observable<NotificationDto> init(String identifier) {
    if (!subscribed) {
      this.identifier = identifier;
      omniPhoneStateListener.start(this);
      smsListener.start(this);
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
    smsListener.stop();
    subscribed = false;
  }
}

package com.omnipaste.eventsprovider;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.eventsprovider.events.TelephonyEvent;
import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;

public class TelephonyNotificationsProviderImpl implements TelephonyNotificationsProvider {

  private TelephonyManager telephonyManager;
  private OmniPhoneStateListener phoneStateListener;
  private String identifier;
  private boolean subscribe = false;

  @Inject
  public OmniApi omniApi;

  @Inject
  public TelephonyNotificationsProviderImpl(TelephonyManager telephonyManager) {
    EventBus eventBus = EventBus.getDefault();
    eventBus.register(this);

    this.telephonyManager = telephonyManager;
    this.phoneStateListener = new OmniPhoneStateListener();
  }

  @Override
  public Observable<NotificationDto> init(String identifier) {
    if (!subscribe) {
      this.identifier = identifier;
      telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

      subscribe = true;
    }

    return Observable.empty();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(TelephonyEvent event) {
    TelephonyEventDto telephonyEventDto = event.getTelephonyEventDto();
    telephonyEventDto.setIdentifier(identifier);
    omniApi.events().create(telephonyEventDto).subscribe();
  }

  @Override
  public void destroy() {
    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);

    subscribe = false;
  }
}

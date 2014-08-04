package com.omnipaste.eventsprovider;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class TelephonyNotificationsProviderImpl implements TelephonyNotificationsProvider {
  private TelephonyManager telephonyManager;
  private OmniPhoneStateListener phoneStateListener;
  private Subscription phoneStateSubscriber;
  private boolean subscribe = false;

  @Inject
  public OmniApi omniApi;

  @Inject
  public TelephonyNotificationsProviderImpl(TelephonyManager telephonyManager) {
    this.telephonyManager = telephonyManager;
    this.phoneStateListener = new OmniPhoneStateListener();
  }

  @Override
  public Observable<NotificationDto> init(final String identifier) {
    if (!subscribe) {
      telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
      phoneStateSubscriber = phoneStateListener.getObservable().subscribe(new Action1<TelephonyEventDto>() {
        @Override
        public void call(TelephonyEventDto telephonyEventDto) {
          telephonyEventDto.setIdentifier(identifier);
          omniApi.events().create(telephonyEventDto).subscribe();
        }
      });

      subscribe = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    phoneStateSubscriber.unsubscribe();

    subscribe = false;
  }
}

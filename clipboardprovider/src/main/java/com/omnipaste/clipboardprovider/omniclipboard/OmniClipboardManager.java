package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

public class OmniClipboardManager implements IOmniClipboardManager {
  private BehaviorSubject<String> omniClipboardSubject;

  @Inject
  public IOmniApi omniApi;

  @Inject
  public OmniClipboardManager(NotificationProvider notificationProvider) {
    omniClipboardSubject = BehaviorSubject.create("");

    notificationProvider
        .getObservable()
        .where(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            return notificationDto.getProvider() == Provider.clipboard;
          }
        })
        .subscribe(
            new Action1<NotificationDto>() {
              @Override
              public void call(NotificationDto notificationDto) {
                omniClipboardSubject.onNext(notificationDto.getRegistrationId());
              }
            });
  }

  public Observable<String> getObservable() {
    return omniClipboardSubject;
  }

  public Observable<ClippingDto> getPrimaryClip(String channel) {
    return omniApi.clippings().last(channel);
  }

  public void setPrimaryClip(String channel, ClippingDto clippingDto) {
    omniApi.clippings().create(channel, clippingDto).subscribe();
  }
}

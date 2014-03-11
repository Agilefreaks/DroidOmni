package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class OmniClipboardManager implements IOmniClipboardManager {
  private PublishSubject<String> omniClipboardSubject;

  @Inject
  public IOmniApi omniApi;

  @Inject
  public OmniClipboardManager(NotificationProvider notificationProvider) {
    omniClipboardSubject = PublishSubject.create();

    notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            return notificationDto.getTarget() == Target.clipboard;
          }
        })
        .subscribe(
            new Action1<NotificationDto>() {
              @Override
              public void call(NotificationDto notificationDto) {
                omniClipboardSubject.onNext(notificationDto.getRegistrationId());
              }
            }
        );
  }

  public Observable<String> getObservable() {
    return omniClipboardSubject;
  }

  public Observable<ClippingDto> getPrimaryClip(String channel) {
    return omniApi.clippings().last(channel);
  }

  public ClippingDto setPrimaryClip(String channel, ClippingDto clippingDto) {
    omniApi.clippings().create(channel, clippingDto).subscribe();

    return new ClippingDto(clippingDto).setClippingProvider(ClippingDto.ClippingProvider.local);
  }
}

package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

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
            });
  }

  public Observable<String> getObservable() {
    return omniClipboardSubject.subscribeOn(Schedulers.computation());
  }

  public Observable<ClippingDto> getPrimaryClip(String channel) {
    return omniApi.clippings().last(channel);
  }

  public void setPrimaryClip(String channel, ClippingDto clippingDto) {
    omniApi.clippings().create(channel, clippingDto).subscribe();
  }
}

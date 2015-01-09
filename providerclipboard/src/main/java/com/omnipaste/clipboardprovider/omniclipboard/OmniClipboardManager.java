package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omniapi.resource.v1.Clippings;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class OmniClipboardManager {
  private PublishSubject<ClippingDto> omniClipboardSubject;

  @Inject
  public Clippings clippings;

  public Observable<ClippingDto> getObservable() {
    return omniClipboardSubject;
  }

  @Inject
  public OmniClipboardManager(NotificationProvider notificationProvider) {
    omniClipboardSubject = PublishSubject.create();

    notificationProvider
        .getObservable()
        .filter(new Func1<NotificationDto, Boolean>() {
          @Override
          public Boolean call(NotificationDto notificationDto) {
            return notificationDto.getTarget() == NotificationDto.Target.CLIPBOARD;
          }
        })
        .subscribe(
            new Action1<NotificationDto>() {
              @Override
              public void call(NotificationDto notificationDto) {
                String id = notificationDto.getExtra().getString("id");
                onPrimaryClipChanged(id);
              }
            }
        );
  }

  public ClippingDto setPrimaryClip(ClippingDto clippingDto) {
    clippings.create(clippingDto).subscribe();

    return clippingDto;
  }

  public void onPrimaryClipChanged(String id) {
    getPrimaryClip(id).subscribe(new Action1<ClippingDto>() {
      @Override
      public void call(ClippingDto clippingDto) {
        omniClipboardSubject.onNext(clippingDto.setClippingProvider(ClippingDto.ClippingProvider.CLOUD));
      }
    });
  }

  public Observable<ClippingDto> getPrimaryClip(String id) {
    return clippings.get(id);
  }
}

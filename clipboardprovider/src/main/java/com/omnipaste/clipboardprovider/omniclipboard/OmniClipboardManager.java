package com.omnipaste.clipboardprovider.omniclipboard;

import android.content.ClipData;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omnicommon.Provider;
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

  public ClipData getPrimaryClip() {
    return null;
  }

  public void setPrimaryClip(ClipData clipData) {
  }
}

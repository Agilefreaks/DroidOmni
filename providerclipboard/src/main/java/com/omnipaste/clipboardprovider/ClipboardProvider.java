package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.LocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class ClipboardProvider implements Provider<ClippingDto> {
  private PublishSubject<ClippingDto> clipboardProviderSubject;
  private Subscription subscription;
  private OmniClipboardManager currentOmniClipboardManager;
  private LocalClipboardManager currentLocalClipboardManager;

  @Inject
  public Lazy<OmniClipboardManager> omniClipboardManager;

  @Inject
  public Lazy<LocalClipboardManager> localClipboardManager;

  @Inject
  public ClipboardProvider() {
    clipboardProviderSubject = PublishSubject.create();
  }

  public Observable<ClippingDto> init(final String deviceId) {
    if (subscription != null) {
      return clipboardProviderSubject;
    }

    currentOmniClipboardManager = omniClipboardManager.get();
    currentLocalClipboardManager = localClipboardManager.get();

    Func1<ClippingDto, String> keySelector = new Func1<ClippingDto, String>() {
      @Override
      public String call(ClippingDto clippingDto) {
        return clippingDto.getContent();
      }
    };

    subscription = currentLocalClipboardManager
        .getObservable()
        .mergeWith(currentOmniClipboardManager.getObservable())
        .distinctUntilChanged(keySelector)
        .subscribe(
            new Action1<ClippingDto>() {
              @Override public void call(ClippingDto clippingDto) {
                if (clippingDto.getClippingProvider() == ClippingDto.ClippingProvider.CLOUD) {
                  currentLocalClipboardManager.setPrimaryClip(clippingDto);
                } else {
                  clippingDto.setDeviceId(deviceId);
                  currentOmniClipboardManager.setPrimaryClip(clippingDto);
                }

                clipboardProviderSubject.onNext(clippingDto);
              }
            },
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
              }
            }
        );

    return clipboardProviderSubject;
  }

  public Observable<ClippingDto> getObservable() {
    return clipboardProviderSubject;
  }

  public void refreshLocal() {
    currentLocalClipboardManager.onPrimaryClipChanged();
  }

  public void refreshOmni() {
    currentOmniClipboardManager.onPrimaryClipChanged("");
  }

  public void setLocalPrimaryClip(ClippingDto clippingDto) {
    currentLocalClipboardManager.setPrimaryClip(clippingDto, false);
  }

  public void destroy() {
    subscription.unsubscribe();
    subscription = null;
  }
}

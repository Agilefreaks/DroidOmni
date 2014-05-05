package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ClipboardProvider implements IClipboardProvider {
  private PublishSubject<ClippingDto> clipboardProviderSubject;
  private Boolean subscribed = false;
  private Subscription localSubscription;
  private Subscription omniSubscription;

  @Inject
  public Lazy<IOmniClipboardManager> omniClipboardManager;

  @Inject
  public Lazy<ILocalClipboardManager> localClipboardManager;

  public ClipboardProvider() {
    clipboardProviderSubject = PublishSubject.create();
  }

  public Observable<ClippingDto> init(final String identifier) {
    if (!subscribed)
    {
      final IOmniClipboardManager currentOmniClipboardManager = omniClipboardManager.get();
      final ILocalClipboardManager currentLocalClipboardManager = localClipboardManager.get();

      omniSubscription = currentOmniClipboardManager
          .getObservable()
          .subscribe(new Action1<String>() {
            @Override
            public void call(String registrationId) {
              currentOmniClipboardManager
                  .getPrimaryClip()
                  .subscribe(new Action1<ClippingDto>() {
                    @Override
                    public void call(ClippingDto clippingDto) {
                      clippingDto = currentLocalClipboardManager.setPrimaryClip(clippingDto);

                      clipboardProviderSubject.onNext(clippingDto);
                    }
                  });
            }
          });

      localSubscription = currentLocalClipboardManager
          .getObservable()
          .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
              currentLocalClipboardManager
                  .getPrimaryClip()
                  .subscribe(new Action1<ClippingDto>() {
                    @Override
                    public void call(ClippingDto clippingDto) {
                      clippingDto.setIdentifier(identifier);
                      clippingDto = currentOmniClipboardManager.setPrimaryClip(clippingDto);

                      clipboardProviderSubject.onNext(clippingDto);
                    }
                  });
            }
          });

      subscribed = true;
    }

    return clipboardProviderSubject;
  }

  @Override
  public void destroy() {
    subscribed = false;
    localSubscription.unsubscribe();
    omniSubscription.unsubscribe();
  }
}

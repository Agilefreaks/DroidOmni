package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.util.functions.Action1;

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

  public Observable<ClippingDto> subscribe(final String channel, final String identifier) {
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
                  .getPrimaryClip(channel)
                  .subscribe(new Action1<ClippingDto>() {
                    @Override
                    public void call(ClippingDto clippingDto) {
                      currentLocalClipboardManager.setPrimaryClip(channel, clippingDto);

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
                  .getPrimaryClip(channel)
                  .subscribe(new Action1<ClippingDto>() {
                    @Override
                    public void call(ClippingDto clippingDto) {
                      clippingDto.setIdentifier(identifier);
                      currentOmniClipboardManager.setPrimaryClip(channel, clippingDto);

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
  public void unsubscribe() {
    subscribed = false;
    localSubscription.unsubscribe();
    omniSubscription.unsubscribe();
  }
}

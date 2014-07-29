package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ClipboardProvider implements IClipboardProvider {
  private PublishSubject<ClippingDto> clipboardProviderSubject;
  private Boolean subscribed = false;
  private Subscription localSubscription;
  private Subscription omniSubscription;
  private IOmniClipboardManager currentOmniClipboardManager;
  private ILocalClipboardManager currentLocalClipboardManager;
  private OmniClipboardObserver omniClipboardObserver;
  private LocalClipboardObserver localClipboardObserver;

  private class OmniClipboardObserver implements Observer<String> {
    @Override
    public void onNext(String s) {
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

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }
  }

  private class LocalClipboardObserver implements Observer<String> {
    private String identifier;

    private LocalClipboardObserver(String identifier) {
      this.identifier = identifier;
    }

    @Override
    public void onNext(String s) {
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

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

  }

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
      currentOmniClipboardManager = omniClipboardManager.get();
      currentLocalClipboardManager = localClipboardManager.get();

      omniClipboardObserver = new OmniClipboardObserver();
      localClipboardObserver = new LocalClipboardObserver(identifier);

      omniSubscription = currentOmniClipboardManager
          .getObservable()
          .subscribe(omniClipboardObserver);

      localSubscription = currentLocalClipboardManager
          .getObservable()
          .throttleFirst(256, TimeUnit.MILLISECONDS)
          .subscribe(localClipboardObserver);

      subscribed = true;
    }

    return clipboardProviderSubject;
  }

  @Override
  public void refreshLocal() {
    localClipboardObserver.onNext("");
  }

  @Override
  public void refreshOmni() {
    omniClipboardObserver.onNext("");
  }

  @Override
  public void destroy() {
    subscribed = false;
    localSubscription.unsubscribe();
    omniSubscription.unsubscribe();
  }
}

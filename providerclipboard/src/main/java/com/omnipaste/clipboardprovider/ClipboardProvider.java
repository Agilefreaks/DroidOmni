package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class ClipboardProvider implements IClipboardProvider {
  private PublishSubject<ClippingDto> clipboardProviderSubject;
  private Boolean subscribed = false;
  private Subscription localSubscription;
  private Subscription omniSubscription;
  private IOmniClipboardManager currentOmniClipboardManager;
  private ILocalClipboardManager currentLocalClipboardManager;

  private class OmniClipboardObserver implements Observer<ClippingDto> {
    @Override
    public void onNext(ClippingDto clippingDto) {
      currentLocalClipboardManager.setPrimaryClip(clippingDto);
      clipboardProviderSubject.onNext(clippingDto);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }
  }

  private class LocalClipboardObserver implements Observer<ClippingDto> {
    private String identifier;

    private LocalClipboardObserver(String identifier) {
      this.identifier = identifier;
    }

    @Override
    public void onNext(ClippingDto clippingDto) {
      clippingDto.setIdentifier(identifier);
      currentOmniClipboardManager.setPrimaryClip(clippingDto);
      clipboardProviderSubject.onNext(clippingDto);
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
    if (subscribed) {
      return clipboardProviderSubject;
    }

    currentOmniClipboardManager = omniClipboardManager.get();
    currentLocalClipboardManager = localClipboardManager.get();

    OmniClipboardObserver omniClipboardObserver = new OmniClipboardObserver();
    LocalClipboardObserver localClipboardObserver = new LocalClipboardObserver(identifier);

    Func1<ClippingDto, String> keySelector = new Func1<ClippingDto, String>() {
      @Override
      public String call(ClippingDto clippingDto) {
        return clippingDto.getContent();
      }
    };

    omniSubscription = currentOmniClipboardManager
        .getObservable()
        .distinctUntilChanged(keySelector)
        .subscribe(omniClipboardObserver);

    localSubscription = currentLocalClipboardManager
        .getObservable()
        .distinctUntilChanged(keySelector)
        .subscribe(localClipboardObserver);

    subscribed = true;

    return clipboardProviderSubject;
  }

  @Override
  public void refreshLocal() {
    currentLocalClipboardManager.onPrimaryClipChanged();
  }

  @Override
  public void refreshOmni() {
    currentOmniClipboardManager.onPrimaryClipChanged();
  }

  @Override
  public void destroy() {
    subscribed = false;
    localSubscription.unsubscribe();
    omniSubscription.unsubscribe();
  }
}

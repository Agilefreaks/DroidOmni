package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.util.functions.Action1;

public class ClipboardProvider implements IClipboardProvider {
  private PublishSubject<ClippingDto> clipboardProviderSubject;

  @Inject
  public Lazy<IOmniClipboardManager> omniClipboardManager;

  @Inject
  public Lazy<ILocalClipboardManager> localClipboardManager;

  public ClipboardProvider() {
    clipboardProviderSubject = PublishSubject.create();
  }

  public Observable<ClippingDto> getObservable(final String channel, final String identifier) {
    final IOmniClipboardManager currentOmniClipboardManager = omniClipboardManager.get();
    final ILocalClipboardManager currentLocalClipboardManager = localClipboardManager.get();

    currentOmniClipboardManager
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
                  }
                });
          }
        });

    currentLocalClipboardManager
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

    return clipboardProviderSubject.subscribeOn(Schedulers.computation());
  }
}

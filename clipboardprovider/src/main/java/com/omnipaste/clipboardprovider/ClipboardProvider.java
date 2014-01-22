package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.util.functions.Action1;

public class ClipboardProvider implements IClipboardProvider {
  @Inject
  public Lazy<OmniClipboardManager> omniClipboardManager;

  public ClipboardProvider() {
  }

  public void enable() {
    omniClipboardManager.get()
        .getObservable()
        .skip(1)
        .subscribe(new Action1<String>() {
          @Override
          public void call(String registrationId) {
            omniClipboardManager.get()
                .getPrimaryClip()
                .subscribe(new Action1<ClippingDto>() {
                             @Override
                             public void call(ClippingDto clippingDto) {

                             }
                           }, new Action1<Throwable>() {
                             @Override
                             public void call(Throwable throwable) {

                             }
                           }
                );
          }
        });
  }

  public void disable() {
  }
}

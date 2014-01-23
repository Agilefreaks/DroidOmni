package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Subscription;
import rx.util.functions.Action1;

public class ClipboardProvider implements IClipboardProvider {
  private Subscription omniClipboardManagerSubscriber;

  @Inject
  public Lazy<OmniClipboardManager> omniClipboardManager;

  public ClipboardProvider() {
  }

  public void enable(final String channel, final String identifier) {
    omniClipboardManagerSubscriber = omniClipboardManager.get()
        .getObservable()
        .skip(1)
        .subscribe(new Action1<String>() {
          @Override
          public void call(String registrationId) {
            omniClipboardManager.get()
                .getPrimaryClip(channel)
                .subscribe(new Action1<ClippingDto>() {
                  @Override
                  public void call(ClippingDto clippingDto) {
                    // emit an event
                  }
                }
                );
          }
        });
  }

  public void disable() {
    omniClipboardManagerSubscriber.unsubscribe();
  }
}

package com.omnipaste.droidomni.services.subscribers;

import com.omnipaste.clipboardprovider.IClipboardProvider;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ClipboardSubscriberImpl implements ClipboardSubscriber {
  private Subscription clipboardSubscriber;
  private IClipboardProvider clipboardProvider;
  private EventBus eventBus = EventBus.getDefault();


  @Inject
  public ClipboardSubscriberImpl(IClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    clipboardSubscriber = clipboardProvider
        .init(deviceIdentifier)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ClippingDto>() {
          @Override
          public void call(ClippingDto clippingDto) {
            eventBus.post(new ClippingAdded(clippingDto));
          }
        });
  }

  @Override
  public void stop() {
    clipboardSubscriber.unsubscribe();
    clipboardProvider.destroy();
  }
}

package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@Singleton
public class ClipboardSubscriber implements Subscriber {
  private Subscription clipboardSubscriber;
  private ClipboardProvider clipboardProvider;
  private EventBus eventBus = EventBus.getDefault();

  @Inject
  public ClipboardSubscriber(ClipboardProvider clipboardProvider) {
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
    if (clipboardSubscriber != null) {
      clipboardSubscriber.unsubscribe();
      clipboardProvider.destroy();
    }
  }

  public void refreshOmni() {
    clipboardProvider.refreshOmni();
  }
}

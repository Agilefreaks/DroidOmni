package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.eventsprovider.EventsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

@Singleton
public class EventsSubscriber implements Subscriber {
  private EventsProvider eventsProvider;
  private Subscription eventsSubscriber;

  @Inject
  public EventsSubscriber(EventsProvider eventsProvider) {
    this.eventsProvider = eventsProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    eventsSubscriber = eventsProvider
        .init(deviceIdentifier)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }

  @Override
  public void stop() {
    if (eventsSubscriber != null) {
      eventsSubscriber.unsubscribe();
      eventsProvider.destroy();
    }
  }
}

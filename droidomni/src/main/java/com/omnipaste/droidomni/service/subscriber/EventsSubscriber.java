package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.eventsprovider.EventsProvider;
import com.omnipaste.omnicommon.dto.EventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;

@Singleton
public class EventsSubscriber extends ObservableSubscriber<EventDto> {
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
        .subscribe(new Action1<EventDto>() {
          @Override public void call(EventDto eventDto) {
            subject.onNext(eventDto);
          }
        });
  }

  @Override
  public void stop() {
    if (eventsSubscriber != null) {
      eventsSubscriber.unsubscribe();
      eventsProvider.destroy();
    }
  }
}

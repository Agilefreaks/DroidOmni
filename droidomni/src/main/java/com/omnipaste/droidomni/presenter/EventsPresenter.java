package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.Event;
import com.omnipaste.droidomni.service.subscriber.EventsSubscriber;
import com.omnipaste.omnicommon.dto.EventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

@Singleton
public class EventsPresenter extends Presenter<EventsPresenter.View> implements Observer<EventDto> {
  private final ReplaySubject<Event> eventsSubject = ReplaySubject.create();
  private EventsSubscriber eventsSubscriber;
  private Subscription eventSubscription;

  public interface View {
  }

  @Inject
  public EventsPresenter(EventsSubscriber eventsSubscriber) {
    this.eventsSubscriber = eventsSubscriber;
  }

  @Override
  public void initialize() {
    if (eventSubscription != null) {
      return;
    }

    eventSubscription = eventsSubscriber.subscribe(this);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    eventSubscription.unsubscribe();
    eventSubscription = null;
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(EventDto eventDto) {
    eventsSubject.onNext(Event.add(eventDto));
  }

  public Observable<Event> getObservable() {
    return eventsSubject;
  }

  public void remove(EventDto event) {
    eventsSubject.onNext(Event.remove(event));
  }
}

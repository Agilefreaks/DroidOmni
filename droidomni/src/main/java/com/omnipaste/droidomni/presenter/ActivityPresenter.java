package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ActivityAdapter;
import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.domain.Event;
import com.omnipaste.droidomni.interaction.Refresh;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;

@Singleton
public class ActivityPresenter extends FragmentPresenter<ActivityPresenter.View> {
  private final ClippingsPresenter clippingsPresenter;
  private final EventsPresenter eventsPresenter;
  private final ContactsPresenter contactsPresenter;
  private final Refresh refresh;
  private ActivityAdapter activityAdapter;
  private Subscription clippingsSubscription;
  private Subscription eventsSubscription;
  private Subscription contactsSubscription;

  public interface View {
    public void setRefreshing(boolean refreshing);

    public void scrollToTop();
  }

  @Inject
  public ActivityPresenter(
    ClippingsPresenter clippingsPresenter,
    EventsPresenter eventsPresenter,
    ContactsPresenter contactsPresenter,
    Refresh refresh) {
    this.clippingsPresenter = clippingsPresenter;
    this.eventsPresenter = eventsPresenter;
    this.contactsPresenter = contactsPresenter;
    this.refresh = refresh;
  }

  @Override
  public void initialize() {
    if (clippingsSubscription != null ||
      eventsSubscription != null ||
      contactsSubscription != null) {
      return;
    }

    clippingsPresenter.initialize();
    eventsPresenter.initialize();
    contactsPresenter.initialize();
    activityAdapter = ActivityAdapter.build();

    clippingsSubscription = clippingsPresenter
      .getObservable()
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<Clipping>() {
        @Override
        public void call(Clipping clipping) {
          if (clipping.getAction() == Clipping.Action.ADD) {
            activityAdapter.add(clipping.getItem());
            getView().scrollToTop();
          } else {
            activityAdapter.remove(clipping.getItem());
          }
        }
      });

    eventsSubscription = eventsPresenter
      .getObservable()
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<Event>() {
        @Override
        public void call(Event event) {
          if (event.getAction() == Event.Action.ADD) {
            activityAdapter.add(event.getItem());
            getView().scrollToTop();
          } else {
            activityAdapter.remove(event.getItem());
          }
        }
      });

    contactsSubscription = contactsPresenter
      .getObservable()
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<ContactSyncNotification>() {
        @Override
        public void call(ContactSyncNotification contactSyncNotification) {
          activityAdapter.add(contactSyncNotification);
          getView().scrollToTop();
        }
      });

    showSamples();
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    clippingsPresenter.destroy();
    eventsPresenter.destroy();

    clippingsSubscription.unsubscribe();
    clippingsSubscription = null;

    eventsSubscription.unsubscribe();
    eventsSubscription = null;

    contactsSubscription.unsubscribe();
    contactsSubscription = null;
  }

  public ActivityAdapter getAdapter() {
    return activityAdapter;
  }

  public void refresh() {
    getView().setRefreshing(true);

    refresh.all();

    rx.Observable
      .timer(3, TimeUnit.SECONDS)
      .subscribeOn(scheduler)
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<Long>() {
        @Override
        public void call(Long aLong) {
          getView().setRefreshing(false);
        }
      });
  }

  private void showSamples() {
    clippingsPresenter.showSamples();
    eventsPresenter.showSamples();
  }
}

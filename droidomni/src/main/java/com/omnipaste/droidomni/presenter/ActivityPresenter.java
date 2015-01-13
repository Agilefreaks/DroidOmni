package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ActivityAdapter;
import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.domain.PhoneCall;
import com.omnipaste.droidomni.domain.SmsMessage;
import com.omnipaste.droidomni.interaction.Refresh;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;

@Singleton
public class ActivityPresenter extends FragmentPresenter<ActivityPresenter.View> {
  private final ClippingsPresenter clippingsPresenter;
  private final SmsMessagesPresenter smsMessagesPresenter;
  private final PhoneCallsPresenter phoneCallsPresenter;
  private final ContactsPresenter contactsPresenter;
  private final Refresh refresh;
  private ActivityAdapter activityAdapter;
  private Subscription clippingsSubscription;
  private Subscription smsMessagesSubscription;
  private Subscription phoneCallsSubscription;
  private Subscription contactsSubscription;

  public interface View {
    public void setRefreshing(boolean refreshing);

    public void scrollToTop();
  }

  @Inject
  public ActivityPresenter(
    ClippingsPresenter clippingsPresenter,
    SmsMessagesPresenter smsMessagesPresenter,
    PhoneCallsPresenter phoneCallsPresenter,
    ContactsPresenter contactsPresenter,
    Refresh refresh) {
    this.clippingsPresenter = clippingsPresenter;
    this.smsMessagesPresenter = smsMessagesPresenter;
    this.phoneCallsPresenter = phoneCallsPresenter;
    this.contactsPresenter = contactsPresenter;
    this.refresh = refresh;
  }

  @Override
  public void initialize() {
    if (clippingsSubscription != null ||
      smsMessagesSubscription != null ||
      phoneCallsSubscription != null ||
      contactsSubscription != null) {
      return;
    }

    clippingsPresenter.initialize();
    smsMessagesPresenter.initialize();
    phoneCallsPresenter.initialize();
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

    phoneCallsSubscription = phoneCallsPresenter
      .getObservable()
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<PhoneCall>() {
        @Override
        public void call(PhoneCall phoneCall) {
          if (phoneCall.getAction() == PhoneCall.Action.ADD) {
            activityAdapter.add(phoneCall.getItem());
            getView().scrollToTop();
          } else {
            activityAdapter.remove(phoneCall.getItem());
          }
        }
      });

    smsMessagesSubscription = smsMessagesPresenter
      .getObservable()
      .observeOn(observeOnScheduler)
      .subscribe(new Action1<SmsMessage>() {
        @Override
        public void call(SmsMessage smsMessage) {
          if (smsMessage.getAction() == SmsMessage.Action.ADD) {
            activityAdapter.add(smsMessage.getItem());
            getView().scrollToTop();
          } else {
            activityAdapter.remove(smsMessage.getItem());
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
    smsMessagesPresenter.destroy();
    phoneCallsPresenter.destroy();

    clippingsSubscription.unsubscribe();
    clippingsSubscription = null;

    phoneCallsSubscription.unsubscribe();
    phoneCallsSubscription = null;

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
  }
}

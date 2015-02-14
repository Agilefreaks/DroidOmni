package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ActivityAdapter;
import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.domain.PhoneCall;
import com.omnipaste.droidomni.domain.SmsMessage;

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
  private ActivityAdapter activityAdapter;
  private Subscription clippingsSubscription;
  private Subscription smsMessagesSubscription;
  private Subscription phoneCallsSubscription;
  private Subscription contactsSubscription;

  public interface View {
    public void scrollToTop();
  }

  @Inject
  public ActivityPresenter(
    ClippingsPresenter clippingsPresenter,
    SmsMessagesPresenter smsMessagesPresenter,
    PhoneCallsPresenter phoneCallsPresenter,
    ContactsPresenter contactsPresenter) {
    this.clippingsPresenter = clippingsPresenter;
    this.smsMessagesPresenter = smsMessagesPresenter;
    this.phoneCallsPresenter = phoneCallsPresenter;
    this.contactsPresenter = contactsPresenter;
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
      .observeOn(getObserveOnScheduler())
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
      .observeOn(getObserveOnScheduler())
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
      .observeOn(getObserveOnScheduler())
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
      .observeOn(getObserveOnScheduler())
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
    /* we need a way to persist the info
    clippingsPresenter.destroy();
    phoneCallsPresenter.destroy();
    smsMessagesPresenter.destroy();
    contactsPresenter.destroy();

    clippingsSubscription.unsubscribe();
    clippingsSubscription = null;

    phoneCallsSubscription.unsubscribe();
    phoneCallsSubscription = null;

    smsMessagesSubscription.unsubscribe();
    smsMessagesSubscription = null;

    contactsSubscription.unsubscribe();
    contactsSubscription = null; */
  }

  public ActivityAdapter getAdapter() {
    return activityAdapter;
  }

  private void showSamples() {
    clippingsPresenter.showSamples();
  }
}

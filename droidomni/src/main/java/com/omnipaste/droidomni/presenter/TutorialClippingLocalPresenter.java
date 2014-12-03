package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
import rx.Subscription;

@Singleton
public class TutorialClippingLocalPresenter extends FragmentPresenter<TutorialClippingLocalPresenter.View> implements Observer<ClippingDto> {
  private BooleanPreference tutorialClippingLocalWasPlayed;
  private ClipboardSubscriber clipboardSubscriber;
  private Subscription clipboardSubscription;

  public interface View {
    void close();
  }

  @Inject
  public TutorialClippingLocalPresenter(
      @TutorialClippingLocal BooleanPreference tutorialClippingLocalWasPlayed,
      ClipboardSubscriber clipboardSubscriber) {
    this.tutorialClippingLocalWasPlayed = tutorialClippingLocalWasPlayed;
    this.clipboardSubscriber = clipboardSubscriber;
  }

  @Override public void initialize() {
    if (clipboardSubscription != null) {
      return;
    }

    clipboardSubscription = clipboardSubscriber
        .getObservable()
        .observeOn(observeOnScheduler)
        .subscribe(this);
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    clipboardSubscription.unsubscribe();
    clipboardSubscription = null;
  }

  @Override public void onCompleted() {
  }

  @Override public void onError(Throwable e) {
  }

  @Override public void onNext(ClippingDto clippingDto) {
    tutorialClippingLocalWasPlayed.set(true);
    getView().close();
  }
}

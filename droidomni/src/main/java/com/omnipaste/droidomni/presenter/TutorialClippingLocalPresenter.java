package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class TutorialClippingLocalPresenter extends FragmentPresenter<TutorialClippingLocalPresenter.View> implements Observer<ClippingDto> {
  private BooleanPreference tutorialClippingLocalWasPlayed;
  private ClipboardSubscriber clipboardSubscriber;

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
    clipboardSubscriber
        .getObservable()
        .observeOn(observeOnScheduler)
        .take(1)
        .subscribe(this);
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
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

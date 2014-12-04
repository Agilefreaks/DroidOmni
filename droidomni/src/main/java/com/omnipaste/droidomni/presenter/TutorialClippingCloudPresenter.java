package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.TutorialClippingCloud;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class TutorialClippingCloudPresenter extends FragmentPresenter<TutorialClippingCloudPresenter.View> implements Observer<ClippingDto> {
  private BooleanPreference tutorialClippingCloudWasPlayed;
  private ClipboardSubscriber clipboardSubscriber;

  public interface View {
    void close();
  }

  @Inject
  public TutorialClippingCloudPresenter(
      @TutorialClippingCloud BooleanPreference tutorialClippingCloudWasPlayed,
      ClipboardSubscriber clipboardSubscriber) {
    this.tutorialClippingCloudWasPlayed = tutorialClippingCloudWasPlayed;
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
    tutorialClippingCloudWasPlayed.set(true);
    getView().close();
  }
}
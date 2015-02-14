package com.omnipaste.droidomni.presenter;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.droidomni.prefs.TutorialClippingCloud;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class TutorialClippingCloudPresenter extends FragmentPresenter<TutorialClippingCloudPresenter.View> implements Observer<ClippingDto> {
  private BooleanPreference tutorialClippingCloudWasPlayed;
  private ClipboardProvider clipboardProvider;

  public interface View {
    void close();
  }

  @Inject
  public TutorialClippingCloudPresenter(
      @TutorialClippingCloud BooleanPreference tutorialClippingCloudWasPlayed,
      ClipboardProvider clipboardProvider) {
    this.tutorialClippingCloudWasPlayed = tutorialClippingCloudWasPlayed;
    this.clipboardProvider = clipboardProvider;
  }

  @Override public void initialize() {
    clipboardProvider
        .getObservable()
        .observeOn(getObserveOnScheduler())
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

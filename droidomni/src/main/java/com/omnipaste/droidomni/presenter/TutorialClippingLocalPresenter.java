package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;

@Singleton
public class TutorialClippingLocalPresenter extends FragmentPresenter<TutorialClippingLocalPresenter.View> {

  private BooleanPreference tutorialClippingLocalWasPlayed;

  public interface View {
    void close();
  }

  @Inject
  public TutorialClippingLocalPresenter(@TutorialClippingLocal BooleanPreference tutorialClippingLocalWasPlayed) {
    this.tutorialClippingLocalWasPlayed = tutorialClippingLocalWasPlayed;
  }

  @Override public void initialize() {
    Observable.timer(10, TimeUnit.SECONDS)
        .observeOn(observeOnScheduler)
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            getView().close();
            tutorialClippingLocalWasPlayed.set(true);
          }
        });
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }
}

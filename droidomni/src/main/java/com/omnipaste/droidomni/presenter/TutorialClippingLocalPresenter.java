package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

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
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }
}

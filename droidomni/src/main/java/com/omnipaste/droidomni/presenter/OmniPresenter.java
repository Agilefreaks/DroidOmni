package com.omnipaste.droidomni.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.prefs.TutorialClippingCloud;
import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.prefs.WeAreAlone;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;
import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;
import com.omnipaste.droidomni.ui.fragment.AllAloneFragment_;
import com.omnipaste.droidomni.ui.fragment.TutorialClippingCloudFragment_;
import com.omnipaste.droidomni.ui.fragment.TutorialClippingLocalFragment_;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniPresenter extends Presenter<OmniPresenter.View> {
  private BooleanPreference weAreAlone;
  private BooleanPreference playedTutorialClippingLocal;
  private BooleanPreference playedTutorialClippingCloud;

  public interface View {
    void replaceFragment(Fragment activityFragment);

    void addFragment(Fragment fragment);
  }

  @Inject
  public OmniPresenter(
      @WeAreAlone BooleanPreference weAreAlone,
      @TutorialClippingLocal BooleanPreference playedTutorialClippingLocal,
      @TutorialClippingCloud BooleanPreference playedTutorialClippingCloud
  ) {
    this.weAreAlone = weAreAlone;
    this.playedTutorialClippingLocal = playedTutorialClippingLocal;
    this.playedTutorialClippingCloud = playedTutorialClippingCloud;
  }

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, OmniActivity_.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    return intent;
  }

  @Override
  public void initialize() {
    replaceFragment(ActivityFragment_.builder().build());

    if (!playedTutorialClippingLocal.get()) {
      addFragment(TutorialClippingLocalFragment_.builder().build());
    }

    if (!playedTutorialClippingCloud.get()) {
      addFragment(TutorialClippingCloudFragment_.builder().build());
    }

    if (weAreAlone.get()) {
      addFragment(AllAloneFragment_.builder().build());
    }
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
  }

  private void replaceFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.replaceFragment(fragment);
  }

  private void addFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.addFragment(fragment);
  }
}

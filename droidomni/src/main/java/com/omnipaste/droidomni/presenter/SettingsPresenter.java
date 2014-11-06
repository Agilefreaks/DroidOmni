package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.SharedPreferences;

import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.fragment.NotificationPreferenceFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsPresenter extends Presenter<SettingsPresenter.View> implements SharedPreferences.OnSharedPreferenceChangeListener {

  private Navigator navigator;
  private SessionService sessionService;
  private SharedPreferences sharedPreferences;

  public interface View {
    void setFragment(NotificationPreferenceFragment fragment);

    void finish();
  }

  @Inject
  public SettingsPresenter(
      SharedPreferences sharedPreferences,
      Navigator navigator,
      SessionService sessionService) {
    this.sharedPreferences = sharedPreferences;
    this.navigator = navigator;
    this.sessionService = sessionService;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachActivity((Activity) view);
    }
  }

  @Override
  public void initialize() {
    setNotificationPreferenceFragment();
  }

  @Override
  public void resume() {
    sharedPreferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void pause() {
    sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(PrefsModule.NOTIFICATIONS_CLIPBOARD_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_PHONE_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_TELEPHONY_KEY) ||
        key.equals(PrefsModule.GCM_WORKAROUND_KEY)) {
      // TODO: restart omniservice
      // OmniService.restart(DroidOmniApplication.getAppContext());
    }
  }

  public void logout() {
    sessionService.logout();
    navigator.openLauncherActivity();
    finishView();
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }

  private void setNotificationPreferenceFragment() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.setFragment(new NotificationPreferenceFragment(this));
  }
}

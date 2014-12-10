package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.SharedPreferences;

import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.fragment.NotificationPreferenceFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action0;

@Singleton
public class SettingsPresenter extends Presenter<SettingsPresenter.View> implements SharedPreferences.OnSharedPreferenceChangeListener {

  private final Navigator navigator;
  private final SessionService sessionService;
  private final OmniServiceConnection omniServiceConnection;
  private final SharedPreferences sharedPreferences;

  public interface View {
    void setFragment(NotificationPreferenceFragment fragment);

    void finish();
  }

  @Inject
  public SettingsPresenter(
      SharedPreferences sharedPreferences,
      Navigator navigator,
      SessionService sessionService,
      OmniServiceConnection omniServiceConnection) {
    this.sharedPreferences = sharedPreferences;
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.omniServiceConnection = omniServiceConnection;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.setContext((Activity) view);
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

  @Override public void destroy() {
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(PrefsModule.NOTIFICATIONS_CLIPBOARD_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_PHONE_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_TELEPHONY_KEY) ||
        key.equals(PrefsModule.GCM_WORKAROUND_KEY)) {
      omniServiceConnection.restartOmniService();
    }
  }

  public void logout() {
    omniServiceConnection
        .stopOmniService(new Action0() {
          @Override public void call() {
            cleanUp();
          }
        });
  }

  private void cleanUp() {
    sessionService.logout();
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

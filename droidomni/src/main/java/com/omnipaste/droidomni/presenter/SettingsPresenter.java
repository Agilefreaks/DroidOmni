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

import rx.functions.Action1;

@Singleton
public class SettingsPresenter extends Presenter<SettingsPresenter.View> implements SharedPreferences.OnSharedPreferenceChangeListener {

  private Navigator navigator;
  private SessionService sessionService;
  private OmniServiceConnection omniServiceConnection;
  private SharedPreferences sharedPreferences;

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
        .stopOmniService()
        .subscribe(
            new Action1<OmniServiceConnection.State>() {
              @Override public void call(OmniServiceConnection.State integer) {
                cleanUp();
              }
            }
        );
  }

  private void cleanUp() {
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

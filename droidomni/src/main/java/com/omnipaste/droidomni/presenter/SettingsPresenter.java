package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.receiver.StartOmniAtBootReceiver_;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.activity.SettingsActivity_;
import com.omnipaste.droidomni.ui.fragment.NotificationPreferenceFragment;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.functions.Action0;
import rx.functions.Action1;

@Singleton
public class SettingsPresenter extends Presenter<SettingsPresenter.View> implements SharedPreferences.OnSharedPreferenceChangeListener {

  private final SharedPreferences sharedPreferences;
  private final Navigator navigator;
  private final SessionService sessionService;
  private final OmniServiceConnection omniServiceConnection;
  private final Context context;

  public interface View {
    void setFragment(NotificationPreferenceFragment fragment);

    void logout();
  }

  @Inject
  public SettingsPresenter(
    SharedPreferences sharedPreferences,
    Navigator navigator,
    SessionService sessionService,
    OmniServiceConnection omniServiceConnection,
    Context context) {
    this.sharedPreferences = sharedPreferences;
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.omniServiceConnection = omniServiceConnection;
    this.context = context;
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, SettingsActivity_.class);
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

  @Override
  public void destroy() {
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(PrefsModule.NOTIFICATIONS_CLIPBOARD_KEY) ||
      key.equals(PrefsModule.NOTIFICATIONS_PHONE_KEY) ||
      key.equals(PrefsModule.NOTIFICATIONS_TELEPHONY_KEY)) {
      omniServiceConnection.restartOmniService();
    } else if (key.equals(PrefsModule.START_OMNI_AT_BOOT)) {
      toggleStartAtBootReceiver(sharedPreferences);
    }
  }

  private void toggleStartAtBootReceiver(SharedPreferences sharedPreferences) {
    BooleanPreference startAtBoot = new BooleanPreference(sharedPreferences, PrefsModule.START_OMNI_AT_BOOT);
    final int state = startAtBoot.get() ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

    ComponentName receiver = new ComponentName(context, StartOmniAtBootReceiver_.class);
    PackageManager pm = context.getPackageManager();

    pm.setComponentEnabledSetting(receiver,
      state,
      PackageManager.DONT_KILL_APP);
  }

  public void logout() {
    omniServiceConnection
      .stopOmniService(new Action0() {
        @Override
        public void call() {
          cleanUp();
        }
      });
  }

  private void cleanUp() {
    sessionService.logout().subscribe(
      new Action1<Unit>() {
        @Override
        public void call(Unit unit) {
          finishView();
        }
      }
    );
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.logout();
  }

  private void setNotificationPreferenceFragment() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.setFragment(new NotificationPreferenceFragment(this));
  }
}

package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.net.Uri;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.ui.Navigator;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class NavigationDrawerPresenter extends FragmentPresenter<NavigationDrawerPresenter.View> {
  private Navigator navigator;
  private NavigationDrawerAdapter navigationDrawerAdapter;
  private SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter;
  private OmniServiceConnection omniServiceConnection;

  public interface View {
  }

  @Inject
  public NavigationDrawerPresenter(Navigator navigator,
                                   NavigationDrawerAdapter navigationDrawerAdapter,
                                   SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter,
                                   OmniServiceConnection omniServiceConnection) {
    this.navigator = navigator;
    this.navigationDrawerAdapter = navigationDrawerAdapter;
    this.secondaryNavigationDrawerAdapter = secondaryNavigationDrawerAdapter;
    this.omniServiceConnection = omniServiceConnection;
  }

  @Override
  public void attachActivity(Activity activity) {
    super.attachActivity(activity);
    navigator.setContext(activity);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
  }

  public NavigationDrawerAdapter getNavigationDrawerAdapter() {
    return navigationDrawerAdapter;
  }

  public SecondaryNavigationDrawerAdapter getSecondaryNavigationDrawerAdapter() {
    return secondaryNavigationDrawerAdapter;
  }

  public void navigateTo(NavigationDrawerItem navigationDrawerItem) {
    switch (navigationDrawerItem.getNavigationMenu()) {
      case ACTIVITY:
        break;
      case SETTINGS:
        navigator.openSettings();
        break;
      case ABOUT:
        navigator.openAbout();
        break;
      case PRIVACY_POLICY:
        navigator.openUri(Uri.parse(BuildConfig.TOS_URL));
        break;
      case EXIT:
        omniServiceConnection
            .stopOmniService()
            .timeout(1, TimeUnit.SECONDS)
            .takeFirst(new Func1<OmniServiceConnection.State, Boolean>() {
              @Override public Boolean call(OmniServiceConnection.State state) {
                return state == OmniServiceConnection.State.stopped || state == OmniServiceConnection.State.error;
              }
            })
            .subscribe(
                new Action1<OmniServiceConnection.State>() {
                  @Override public void call(OmniServiceConnection.State state) {
                    finishActivity();
                  }
                }
            );
        break;
    }
  }

  private void finishActivity() {
    Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    activity.finish();
  }
}

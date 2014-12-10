package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.net.Uri;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.ui.Navigator;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action0;

@Singleton
public class NavigationDrawerPresenter extends FragmentPresenter<NavigationDrawerPresenter.View> {
  private final Navigator navigator;
  private final NavigationDrawerAdapter navigationDrawerAdapter;
  private final SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter;
  private final OmniServiceConnection omniServiceConnection;

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
            .stopOmniService(new Action0() {
              @Override public void call() {
                finishActivity();
              }
            });
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

package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.net.Uri;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.ui.Navigator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NavigationDrawerPresenter extends FragmentPresenter<NavigationDrawerPresenter.View> {
  private Navigator navigator;
  private NavigationDrawerAdapter navigationDrawerAdapter;
  private SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter;

  public interface View {
  }

  @Inject
  public NavigationDrawerPresenter(Navigator navigator,
      NavigationDrawerAdapter navigationDrawerAdapter,
      SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter) {
    this.navigator = navigator;
    this.navigationDrawerAdapter = navigationDrawerAdapter;
    this.secondaryNavigationDrawerAdapter = secondaryNavigationDrawerAdapter;
  }

  @Override
  public void attachActivity(Activity activity) {
    super.attachActivity(activity);
    navigator.attachActivity(activity);
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
    }
  }
}

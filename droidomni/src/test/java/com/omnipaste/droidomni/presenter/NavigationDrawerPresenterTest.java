package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.ui.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.functions.Action0;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NavigationDrawerPresenterTest {
  private NavigationDrawerPresenter subject;

  @Mock public Navigator mockNavigator;
  @Mock public OmniServiceConnection mockOmniServiceConnection;

  @Before
  public void context() {
    mockNavigator = mock(Navigator.class);
    NavigationDrawerAdapter mockNavigationDrawerAdapter = mock(NavigationDrawerAdapter.class);
    SecondaryNavigationDrawerAdapter mockSecondaryNavigationDrawerAdapter = mock(SecondaryNavigationDrawerAdapter.class);
    mockOmniServiceConnection = mock(OmniServiceConnection.class);
    subject = new NavigationDrawerPresenter(
        mockNavigator, mockNavigationDrawerAdapter,
        mockSecondaryNavigationDrawerAdapter,
        mockOmniServiceConnection);
  }

  @Test
  public void attachActivityWillSetTheActivityOnTheNavigator() throws Exception {
    Activity activity = mock(Activity.class);

    subject.attachActivity(activity);

    verify(mockNavigator).setContext(activity);
  }

  @Test
  public void exitWhenNotTimeoutWillCleanup() throws Exception {
    subject.navigateTo(new NavigationDrawerItem("Exit", NavigationMenu.EXIT));

    verify(mockOmniServiceConnection).stopOmniService(any(Action0.class));
  }
}

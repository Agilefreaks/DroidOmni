package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.ui.Navigator;

import rx.functions.Action0;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NavigationDrawerPresenterTest extends InstrumentationTestCase {
  private NavigationDrawerPresenter subject;
  private Navigator mockNavigator;
  private OmniServiceConnection mockOmniServiceConnection;

  public void setUp() throws Exception {
    super.setUp();

    mockNavigator = mock(Navigator.class);
    NavigationDrawerAdapter mockNavigationDrawerAdapter = mock(NavigationDrawerAdapter.class);
    SecondaryNavigationDrawerAdapter mockSecondaryNavigationDrawerAdapter = mock(SecondaryNavigationDrawerAdapter.class);
    mockOmniServiceConnection = mock(OmniServiceConnection.class);
    subject = new NavigationDrawerPresenter(
        mockNavigator, mockNavigationDrawerAdapter,
        mockSecondaryNavigationDrawerAdapter,
        mockOmniServiceConnection);
  }

  public void testAttachActivityWillSetTheActivityOnTheNavigator() throws Exception {
    Activity activity = mock(Activity.class);

    subject.attachActivity(activity);

    verify(mockNavigator).setContext(activity);
  }

  public void testExitWhenNotTimeoutWillCleanup() throws Exception {
    subject.navigateTo(new NavigationDrawerItem("Exit", NavigationMenu.EXIT));

    verify(mockOmniServiceConnection).stopOmniService(any(Action0.class));
  }
}
package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.ui.Navigator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NavigationDrawerPresenterTest extends InstrumentationTestCase {
  private NavigationDrawerPresenter subject;
  private Navigator mockNavigator;


  public void setUp() throws Exception {
    super.setUp();

    mockNavigator = mock(Navigator.class);
    NavigationDrawerAdapter mockNavigationDrawerAdapter = mock(NavigationDrawerAdapter.class);
    SecondaryNavigationDrawerAdapter mockSecondaryNavigationDrawerAdapter = mock(SecondaryNavigationDrawerAdapter.class);
    subject = new NavigationDrawerPresenter(mockNavigator, mockNavigationDrawerAdapter, mockSecondaryNavigationDrawerAdapter);
  }

  public void testAttachActivityWillSetTheActivityOnTheNavigator() throws Exception {
    Activity activity = mock(Activity.class);

    subject.attachActivity(activity);

    verify(mockNavigator).attachActivity(activity);
  }
}
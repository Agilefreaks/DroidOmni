package com.omnipaste.droidomni.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapters.NavigationDrawerItem;
import com.omnipaste.droidomni.adapters.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.events.NavigationItemClicked;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_navigation_drawer)
public class NavigationDrawerFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();
  private NavigationDrawerAdapter navigationDrawerAdapter;
  private SecondaryNavigationDrawerAdapter secondaryNavigationDrawerAdapter;
  private ActionBarDrawerToggle drawerToggle;

  @ViewById
  public ListView navigationDrawerList;

  @ViewById
  public ListView secondaryNavigationDrawerList;

  public NavigationDrawerFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setRetainInstance(true);

    navigationDrawerAdapter = NavigationDrawerAdapter.build(this.getResources());
    secondaryNavigationDrawerAdapter = SecondaryNavigationDrawerAdapter.build(this.getResources());
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @AfterViews
  public void afterViews() {
    if (navigationDrawerList.getAdapter() == null) {
      navigationDrawerList.setAdapter(navigationDrawerAdapter);
    }

    if (secondaryNavigationDrawerList.getAdapter() == null) {
      secondaryNavigationDrawerList.setAdapter(secondaryNavigationDrawerAdapter);
    }
  }

  public void setUp(ActionBarDrawerToggle drawerToggle) {
    this.drawerToggle = drawerToggle;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  @ItemClick
  public void navigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    eventBus.post(new NavigationItemClicked(navigationDrawerItem));
  }

  @ItemClick
  public void secondaryNavigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    eventBus.post(new NavigationItemClicked(navigationDrawerItem));
  }
}

package com.omnipaste.droidomni.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapters.NavigationDrawerItem;
import com.omnipaste.droidomni.events.NavigationItemClicked;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_navigation_drawer)
public class NavigationDrawerFragment extends Fragment {
  private ActionBarDrawerToggle drawerToggle;
  private EventBus eventBus = EventBus.getDefault();
  private NavigationDrawerAdapter navigationDrawerAdapter;

  @ViewById
  public ListView navigationDrawerList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setRetainInstance(true);

    navigationDrawerAdapter = NavigationDrawerAdapter.build(this.getResources());
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  /**
   * Users of this fragment must call this method to set up the navigation drawer interactions.
   *
   * @param drawerLayout The DrawerLayout containing this fragment's UI.
   */
  public void setUp(DrawerLayout drawerLayout) {
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    drawerToggle = new ActionBarDrawerToggle(
        getActivity(),
        drawerLayout,
        R.drawable.ic_drawer,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    ) {
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (!isAdded()) {
          return;
        }

        getActivity().supportInvalidateOptionsMenu();
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (!isAdded()) {
          return;
        }

        getActivity().supportInvalidateOptionsMenu();
      }
    };

    drawerLayout.post(new Runnable() {
      @Override
      public void run() {
        drawerToggle.syncState();
      }
    });

    drawerLayout.setDrawerListener(drawerToggle);

    setUpActionBar();
  }

  @AfterViews
  public void afterViews() {
    if (navigationDrawerList.getAdapter() == null) {
      navigationDrawerList.setAdapter(navigationDrawerAdapter);
    }
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

  private void setUpActionBar() {
    ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
  }
}

package com.omnipaste.droidomni.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapter.NavigationDrawerAdapter;
import com.omnipaste.droidomni.adapter.SecondaryNavigationDrawerAdapter;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
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

  @ViewById
  public ListView navigationDrawerList;

  @ViewById
  public ListView secondaryNavigationDrawerList;

  public NavigationDrawerFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

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

  @ItemClick
  public void navigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    eventBus.post(new NavigationItemClicked(navigationDrawerItem));
  }

  @ItemClick
  public void secondaryNavigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    eventBus.post(new NavigationItemClicked(navigationDrawerItem));
  }
}

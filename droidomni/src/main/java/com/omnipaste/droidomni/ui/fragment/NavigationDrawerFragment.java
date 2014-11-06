package com.omnipaste.droidomni.ui.fragment;

import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.presenter.NavigationDrawerPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_navigation_drawer)
public class NavigationDrawerFragment extends BaseFragment<NavigationDrawerPresenter> {
  @Inject
  public NavigationDrawerPresenter presenter;

  @ViewById
  public ListView navigationDrawerList;

  @ViewById
  public ListView secondaryNavigationDrawerList;

  public NavigationDrawerFragment() {
  }

  @Override
  protected NavigationDrawerPresenter getPresenter() {
    return presenter;
  }

  @AfterViews
  public void afterViews() {
    navigationDrawerList.setAdapter(presenter.getNavigationDrawerAdapter());
    secondaryNavigationDrawerList.setAdapter(presenter.getSecondaryNavigationDrawerAdapter());
  }

  @ItemClick
  public void navigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    presenter.navigateTo(navigationDrawerItem);
  }

  @ItemClick
  public void secondaryNavigationDrawerListItemClicked(NavigationDrawerItem navigationDrawerItem) {
    presenter.navigateTo(navigationDrawerItem);
  }
}

package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.OmniPresenter;
import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_omni)
public class OmniActivity extends BaseActivity<OmniPresenter> implements OmniPresenter.View {
  private ActionBarDrawerToggle drawerToggle;

  @Inject
  public OmniPresenter presenter;

  @ViewById
  public DrawerLayout drawerLayout;

  @FragmentById
  public NavigationDrawerFragment navigationDrawer;

  @Override protected OmniPresenter getPresenter() {
    return presenter;
  }

  @AfterViews
  public void afterView() {
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerLayout.setDrawerListener(drawerToggle);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override
  public void setFragment(Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.omni_container, fragment)
        .commit();
  }
}

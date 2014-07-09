package com.omnipaste.droidomni.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.OmniActivityController;
import com.omnipaste.droidomni.fragments.NavigationDrawerFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_omni)
public class OmniActivity extends ActionBarActivity {

  @ViewById
  public DrawerLayout drawerLayout;

  @FragmentById
  public NavigationDrawerFragment navigationDrawer;

  @Inject
  public OmniActivityController controller;

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, OmniActivity_.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    return intent;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    controller.run(this, savedInstanceState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    controller.stop();
  }

  @Override
  public void onResume() {
    super.onResume();

    controller.setUpNavigationDrawer(navigationDrawer);
  }
}

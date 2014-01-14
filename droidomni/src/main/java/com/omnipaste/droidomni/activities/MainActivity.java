package com.omnipaste.droidomni.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.factories.FragmentFactory;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
  @Inject
  public ConfigurationService configurationService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, FragmentFactory.create_from(configurationService.getConfiguration()))
          .commit();
    }
  }
}
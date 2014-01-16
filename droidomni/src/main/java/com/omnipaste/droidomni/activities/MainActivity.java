package com.omnipaste.droidomni.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.UpdateUI;
import com.omnipaste.droidomni.factories.FragmentFactory;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
  private EventBus eventBus = EventBus.getDefault();

  @Inject
  public ConfigurationService configurationService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    if (savedInstanceState == null) {
      setFragment(FragmentFactory.create_from(configurationService.getConfiguration()));
    }

    eventBus.register(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(UpdateUI event) {
    setFragment(FragmentFactory.create_from(configurationService.getConfiguration()));
  }

  private void setFragment(Fragment fragment) {
    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit();
  }
}
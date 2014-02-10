package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.LoginActivity;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.LoginFragment_;

import de.greenrobot.event.EventBus;

public class LoginController implements LoginActivityController {
  private LoginActivity activity;
  private EventBus eventBus = EventBus.getDefault();

  @Override
  public void start(LoginActivity loginActivity, Bundle savedInstance) {
    activity = loginActivity;

    eventBus.register(this);

    setFragment(LoginFragment_.builder().build());
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(LoginEvent event) {
    activity.startActivity(new Intent(activity, MainActivity_.class));
    activity.finish();
  }

  private void setFragment(Fragment fragment) {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .add(R.id.login_container, fragment)
        .commit();

    supportFragmentManager.executePendingTransactions();
  }
}

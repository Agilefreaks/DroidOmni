package com.omnipasteapp.omnipaste.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.services.IIntentService;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SherlockFragmentActivity implements LogoutDialog.LogoutDialogListener {

  @Inject
  public IConfigurationService configurationService;

  @Inject
  public IIntentService intentService;

  @StringRes
  public String startOmnipasteService;

  @StringRes
  public String stopOmnipasteService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);
  }

  @AfterViews
  public void startOmnipasteService() {
    if(configurationService.loadCommunicationSettings()) {
      intentService.sendBroadcast(startOmnipasteService);

      getSupportActionBar().setTitle(R.string.app_name);
      getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());
    }
    else {
      intentService.startNewActivity(LoginActivity_.class);
    }
  }

  //region logout

  @OptionsItem
  public void logoutSelected() {
    LogoutDialog.create().show(getSupportFragmentManager(), LogoutDialog.TAG);
  }

  @Override
  public void onDialogPositiveClick(DialogFragment dialog) {
    // kill service
    intentService.sendBroadcast(stopOmnipasteService);

    // log user out
    configurationService.clearChannel();

    // go to login activity
    finish();
    intentService.startNewActivity(LoginActivity_.class);
  }

  @Override
  public void onDialogNegativeClick(DialogFragment dialog) {
  }

  //endregion
}

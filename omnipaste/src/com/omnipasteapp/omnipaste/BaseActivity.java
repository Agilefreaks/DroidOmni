package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.services.IIntentService;

public class BaseActivity extends RoboSherlockFragmentActivity {

  @Inject
  protected IIntentService intentService;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        LogoutDialog.create().show(getSupportFragmentManager(), LogoutDialog.TAG);
        return true;
    }

    return (super.onOptionsItemSelected(item));
  }

  @Override
  public void onCreate(Bundle savedInstance){
    super.onCreate(savedInstance);

    setTheme(com.actionbarsherlock.R.style.Sherlock___Theme_DarkActionBar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getSupportMenuInflater();
    menuInflater.inflate(getMenu(), menu);

    return true;
  }

  public int getMenu() {
    return R.menu.main;
  }
}

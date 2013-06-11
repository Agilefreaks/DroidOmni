package com.omnipasteapp.omnipaste;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import roboguice.activity.RoboActivity;

public class BaseActivity extends RoboActivity {

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        LogoutDialog.create().show(getFragmentManager(), LogoutDialog.TAG);
        return true;
    }

    return (super.onOptionsItemSelected(item));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.main, menu);

    return true;
  }
}

package com.omnipasteapp.omnipaste;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.omnipasteapp.omnipaste.dialogs.ExitDialog;
import roboguice.activity.RoboActivity;

public class BaseActivity extends RoboActivity {
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.main, menu);

    return true;
  }
}

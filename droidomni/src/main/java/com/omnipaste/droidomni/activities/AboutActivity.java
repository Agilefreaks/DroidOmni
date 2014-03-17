package com.omnipaste.droidomni.activities;

import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.AboutAdapter;
import com.omnipaste.droidomni.adapters.AboutItem;

public class AboutActivity extends ListActivity {
  @SuppressWarnings("ConstantConditions")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Resources resources = getResources();

    AboutAdapter aboutAdapter = new AboutAdapter();
    aboutAdapter.addItem(new AboutItem(resources.getString(R.string.app_name), resources.getString(R.string.motto)));

    PackageInfo pInfo = new PackageInfo();
    try {
      pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    aboutAdapter.addItem(new AboutItem(resources.getString(R.string.about_version), pInfo.versionName));

    setListAdapter(aboutAdapter);

    getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        super.onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(menuItem);
  }
}

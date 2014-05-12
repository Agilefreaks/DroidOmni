package com.omnipaste.droidomni.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.AboutAdapter;
import com.omnipaste.droidomni.adapters.AboutItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_about)
public class AboutActivity extends ActionBarActivity {
  @ViewById
  public ListView aboutListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @SuppressWarnings("ConstantConditions")
  @AfterViews
  public void afterView() {
    if (aboutListView.getAdapter() == null) {
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

      aboutListView.setAdapter(aboutAdapter);
    }
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

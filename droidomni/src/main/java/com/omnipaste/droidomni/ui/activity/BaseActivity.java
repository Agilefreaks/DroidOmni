package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.di.ActivityModule;
import com.omnipaste.droidomni.ui.UiModule;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

import dagger.ObjectGraph;

@EActivity
public abstract class BaseActivity extends ActionBarActivity {

  private ObjectGraph activityScopeGraph;

  @ViewById
  protected Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();
  }

  @AfterViews
  public void setupToolbar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  protected void inject(Object object) {
    activityScopeGraph.inject(object);
  }

  protected List<Object> getModules() {
    LinkedList<Object> modules = new LinkedList<>();
    modules.add(new UiModule());
    return modules;
  }

  private void injectDependencies() {
    DroidOmniApplication droidOmniApplication = (DroidOmniApplication) getApplication();
    List<Object> activityScopeModules = getModules();
    activityScopeModules.add(new ActivityModule(this));
    activityScopeGraph = droidOmniApplication.plus(activityScopeModules);
    inject(this);
  }
}

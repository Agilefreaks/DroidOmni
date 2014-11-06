package com.omnipaste.droidomni.ui.activity;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.SettingsPresenter;
import com.omnipaste.droidomni.ui.fragment.NotificationPreferenceFragment;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_settings)
public class SettingsActivity extends BaseActivity<SettingsPresenter> implements SettingsPresenter.View {
  @Inject
  public SettingsPresenter presenter;

  @Override
  protected SettingsPresenter getPresenter() {
    return presenter;
  }


  @Override
  public void setFragment(NotificationPreferenceFragment fragment) {
    getFragmentManager().beginTransaction()
        .replace(R.id.settings_container, fragment)
        .commit();
  }
}

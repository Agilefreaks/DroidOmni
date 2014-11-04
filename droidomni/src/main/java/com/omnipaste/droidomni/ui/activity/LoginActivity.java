package com.omnipaste.droidomni.ui.activity;

import com.omnipaste.droidomni.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

import java.util.LinkedList;
import java.util.List;

@OptionsMenu(R.menu.activity_login_actions)
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
  @Override
  protected List<Object> getModules() {
    return new LinkedList<>();
  }
}
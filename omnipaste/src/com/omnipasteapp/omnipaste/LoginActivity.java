package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.omnipasteapp.omnipaste.dialogs.GoogleLoginDialog;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

  @InjectView(R.id.login_button)
  private Button loginButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (loginButton != null) {
      loginButton.setOnClickListener(this);
    }
  }

  @Override
  public void onClick(View view) {
    GoogleLoginDialog.create().show(getSupportFragmentManager(), GoogleLoginDialog.TAG);
  }

  @Override
  public int getMenu() {
    return R.menu.empty_menu;
  }
}
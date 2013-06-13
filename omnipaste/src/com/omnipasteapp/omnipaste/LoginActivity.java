package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.omnipasteapp.omnipaste.dialogs.GoogleLoginDialog;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity implements View.OnClickListener {

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
    GoogleLoginDialog.create().show(getFragmentManager(), GoogleLoginDialog.TAG);
  }
}
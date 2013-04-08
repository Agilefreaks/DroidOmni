package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.omnipasteapp.omni.core.ClipboardService;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @SuppressWarnings("UnusedParameters")
    public void loginClicked(View view) {
        EditText editText = (EditText) findViewById(R.id.login_email);
        String email = editText.getText().toString();

        // start the clipboard service
        Intent intent = new Intent(this, ClipboardServiceCommandReceiver.class);
        intent.putExtra(ClipboardService.CHANNEL_NAME, email);
        intent.setAction(ClipboardService.START);
        sendBroadcast(intent);

        // navigate to main activity
        Intent activity_intent = new Intent(this, MainActivity.class);
        activity_intent.putExtra(ClipboardService.CHANNEL_NAME, email);
        startActivity(activity_intent);
    }
}

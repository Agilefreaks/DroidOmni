package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.omnipasteapp.omni.core.ClipboardService;

public class MainActivity extends Activity {

    private SharedPreferences _clipboardServicePreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _clipboardServicePreferences = getSharedPreferences(ClipboardService.TAG, MODE_PRIVATE);

        // get email from intent
        Intent intent = getIntent();
        String email = intent.getStringExtra(ClipboardService.CHANNEL_NAME);

        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.status_text_view);
        textView.setText(String.format("Welcome %s", email));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            stopClipboardService();
            logOut();
            startLoginActivity();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void stopClipboardService(){
        Intent intent = new Intent(this, ClipboardServiceCommandReceiver.class);
        intent.setAction(ClipboardService.STOP);
        sendBroadcast(intent);
    }

    private void logOut(){
        SharedPreferences.Editor editor = _clipboardServicePreferences.edit();

        editor.remove(ClipboardService.CHANNEL_NAME);

        editor.commit();
    }

    private void startLoginActivity(){
        Intent activity_intent = new Intent(this, LoginActivity.class);
        startActivity(activity_intent);
    }
}
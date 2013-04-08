package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.omnipasteapp.omni.core.ClipboardService;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            Intent intent = new Intent(this, ClipboardServiceCommandReceiver.class);
            intent.setAction(ClipboardService.STOP);

            sendBroadcast(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

}
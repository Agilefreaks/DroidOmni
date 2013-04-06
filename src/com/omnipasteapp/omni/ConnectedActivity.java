package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.omnipasteapp.omni.core.ClipboardService;

public class ConnectedActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(this, CliboardServiceCommandReceiver.class);
            intent.setAction(ClipboardService.STOP);

            sendBroadcast(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

}
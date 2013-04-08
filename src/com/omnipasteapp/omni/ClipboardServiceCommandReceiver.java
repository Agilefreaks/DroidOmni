package com.omnipasteapp.omni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.omnipasteapp.omni.core.ClipboardService;

public class ClipboardServiceCommandReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        final String actionName = intent.getAction();

        if (actionName.equals(ClipboardService.START)) {
            startClipboardService(context, intent);
            return;
        }

        if (actionName.equals(ClipboardService.STOP)) {
            stopClipboardService(context, intent);
            return;
        }
    }

    public void stopClipboardService(Context context, Intent intent) {
        Intent stopIntent = new Intent(context, ClipboardService.class);
        context.stopService(stopIntent);
    }

    public void startClipboardService(Context context, Intent intent) {
        Intent startIntent = new Intent(context, ClipboardService.class);
        startIntent.putExtra(ClipboardService.CHANNEL_NAME, intent.getStringExtra(ClipboardService.CHANNEL_NAME));
        context.startService(startIntent);
    }
}

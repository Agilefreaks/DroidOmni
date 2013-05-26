package com.omnipasteapp.androidclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import java.util.ArrayList;

public class AndroidClipboard implements ILocalClipboard, Runnable, ClipboardManager.OnPrimaryClipChangedListener {

    private ClipboardManager clipboardManager;
    private ArrayList<ICanReceiveData> dataReceivers;

    @Inject
    Context context;

    public AndroidClipboard(){
        dataReceivers = new ArrayList<ICanReceiveData>();
    }

    @Override
    public void addDataReceiver(ICanReceiveData dataReceiver) {
        dataReceivers.add(dataReceiver);
    }

    @Override
    public void removeDataReceiver(ICanReceiveData dataReceiver) {
        dataReceivers.remove(dataReceiver);
    }

    @Override
    public void putData(String data) {
        ClipboardManager manager = getClipboardManager();

        manager.setPrimaryClip(ClipData.newPlainText("", data));
    }

    @Override
    public Thread initialize() {
        return new Thread(this);
    }

    @Override
    public void run() {
        Context context = getContext();

        clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(this);
    }

    @Override
    public void dispose() {
        ClipboardManager manager = getClipboardManager();
        manager.removePrimaryClipChangedListener(this);

        dataReceivers.clear();
    }

    @Override
    public void onPrimaryClipChanged() {
        ClipboardManager manager = getClipboardManager();

        if(!manager.hasPrimaryClip() || manager.getPrimaryClip().getItemCount() == 0) return;

        String clip = manager.getPrimaryClip().getItemAt(0).getText().toString();

        ClipboardData data = new ClipboardData(this, clip);
        for(ICanReceiveData receiver : dataReceivers){
            receiver.dataReceived(data);
        }
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public void setClipboardManager(ClipboardManager clipboardManager){
        this.clipboardManager = clipboardManager;
    }

    public ClipboardManager getClipboardManager() {
        return clipboardManager;
    }
}

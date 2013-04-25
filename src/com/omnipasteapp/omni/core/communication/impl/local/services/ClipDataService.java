package com.omnipasteapp.omni.core.communication.impl.local.services;

import android.content.ClipData;

public class ClipDataService {

    public ClipData create(String message){
        return ClipData.newPlainText("", message);
    }

}

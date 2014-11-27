package com.omnipaste.droidomni.ui.view.event;

import android.content.Context;
import android.widget.LinearLayout;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.omnicommon.dto.EventDto;

import org.androidannotations.annotations.EViewGroup;

@EViewGroup(R.layout.view_incoming_sms)
public class IncomingSmsView extends LinearLayout implements HasSetup<EventDto> {
  public IncomingSmsView(Context context) {
    super(context);
  }

  @Override
  public void setUp(EventDto item) {
  }
}

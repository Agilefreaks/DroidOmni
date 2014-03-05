package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.services.SmartActionService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import javax.inject.Inject;

@EViewGroup(R.layout.view_clipping)
public class ClippingView extends LinearLayout implements HasSetup<ClippingDto> {
  private HashMap<ClippingDto.ClippingProvider, Integer> icon = new HashMap<ClippingDto.ClippingProvider, Integer>() {{
    put(ClippingDto.ClippingProvider.local, R.drawable.ic_local);
    put(ClippingDto.ClippingProvider.cloud, R.drawable.ic_omni);
  }};

  private HashMap<ClippingDto.ClippingType, Integer> smartActionIcon = new HashMap<ClippingDto.ClippingType, Integer>() {{
    put(ClippingDto.ClippingType.phoneNumber, R.drawable.ic_smart_action_phone_number);
    put(ClippingDto.ClippingType.webSite, R.drawable.ic_smart_action_uri);
  }};

  @ViewById
  public TextView textContent;

  @ViewById
  public ImageButton smartActionButton;

  @Inject
  public SmartActionService smartActionService;

  public ClippingDto clippingDto;

  public ClippingView(Context context) {
    super(context);
    DroidOmniApplication.inject(this);
  }

  public void setUp(ClippingDto clippingDto) {
    this.clippingDto = clippingDto;

    textContent.setText(clippingDto.getContent());
    textContent.setCompoundDrawablesWithIntrinsicBounds(icon.get(clippingDto.getClippingProvider()), 0, 0, 0);

    if (clippingDto.getType() != ClippingDto.ClippingType.unknown) {
      smartActionButton.setVisibility(VISIBLE);
      smartActionButton.setImageResource(smartActionIcon.get(clippingDto.getType()));
    }
    else {
      smartActionButton.setVisibility(GONE);
    }
  }

  @Click
  public void smartActionButtonClicked() {
    smartActionService.run(clippingDto);
  }
}

package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

@EViewGroup(R.layout.view_clipping)
public class ClippingView extends LinearLayout implements HasSetup<ClippingDto> {
  private HashMap<ClippingDto.ClippingProvider, Integer> icon = new HashMap<ClippingDto.ClippingProvider, Integer>() {{
    put(ClippingDto.ClippingProvider.local, R.drawable.ic_local);
    put(ClippingDto.ClippingProvider.cloud, R.drawable.ic_omni);
  }};

  private HashMap<ClippingDto.ClippingType, Integer> smartActionIcon = new HashMap<ClippingDto.ClippingType, Integer>() {{
    put(ClippingDto.ClippingType.phoneNumber, R.drawable.ic_smart_action_phone_number);
    put(ClippingDto.ClippingType.uri, R.drawable.ic_smart_action_uri);
  }};

  @ViewById
  public TextView textContent;

  @ViewById
  public ImageButton smartActionButton;

  public ClippingView(Context context) {
    super(context);
  }

  public void setUp(ClippingDto clippingDto) {
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

  }
}

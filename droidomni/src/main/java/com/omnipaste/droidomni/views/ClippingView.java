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
  private HashMap<ClippingDto.ClippingProvider, Integer> icon = new HashMap<ClippingDto.ClippingProvider, Integer>() {
    {
      put(ClippingDto.ClippingProvider.LOCAL, R.drawable.ic_local);
      put(ClippingDto.ClippingProvider.CLOUD, R.drawable.ic_omni);
    }
  };

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

    if (clippingDto.getType() != ClippingDto.ClippingType.UNKNOWN) {
      smartActionButton.setVisibility(VISIBLE);
      smartActionButton.setImageResource(SmartActionService.SMART_ACTIONS.get(clippingDto.getType()).getIcon()[1]);
    } else {
      smartActionButton.setVisibility(GONE);
    }
  }

  @Click
  public void smartActionButtonClicked() {
    smartActionService.run(clippingDto);
  }
}

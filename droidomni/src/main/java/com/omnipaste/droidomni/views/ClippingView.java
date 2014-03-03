package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

@EViewGroup(R.layout.view_clipping)
public class ClippingView extends LinearLayout implements HasSetup<ClippingDto> {
  private HashMap<ClippingDto.ClippingProvider, Integer> icon = new HashMap<ClippingDto.ClippingProvider, Integer>() {{
    put(ClippingDto.ClippingProvider.local, R.drawable.ic_local);
    put(ClippingDto.ClippingProvider.cloud, R.drawable.ic_omni);
  }};

  @ViewById
  public TextView textContent;

  public ClippingView(Context context) {
    super(context);
  }

  public void setUp(ClippingDto clippingDto) {
    textContent.setText(clippingDto.getContent());
    textContent.setCompoundDrawablesWithIntrinsicBounds(icon.get(clippingDto.getClippingProvider()), 0, 0, 0);
  }
}

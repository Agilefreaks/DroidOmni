package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
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
  private HashMap<ClippingDto.ClippingProvider, Integer> icon = new HashMap<ClippingDto.ClippingProvider, Integer>() {
    {
      put(ClippingDto.ClippingProvider.LOCAL, R.drawable.ic_local);
      put(ClippingDto.ClippingProvider.CLOUD, R.drawable.ic_omni);
    }
  };

  @ViewById
  public TextView textContent;

  @ViewById
  public TextView textSource;

  @ViewById
  public Button smartAction;

  @ViewById
  public ImageView sourceImage;

  public ClippingView(Context context) {
    super(context);
  }

  @Override
  public void setUp(ClippingDto item) {
    textContent.setText(item.getContent());
    textSource.setText(item.getClippingProvider().toString().toLowerCase());
    sourceImage.setImageResource(icon.get(item.getClippingProvider()));
  }

  @Click
  public void deleteClicked() {
  }

  @Click
  public void smartActionClicked() {
  }
}

package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.ClippingPresenter;
import com.omnipaste.droidomni.service.SmartActionService;
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

  private ClippingDto item;

  @ViewById
  public TextView textContent;

  @ViewById
  public TextView textSource;

  @ViewById
  public Button smartAction;

  @ViewById
  public ImageView sourceImage;

  @Inject
  public ClippingPresenter clippingPresenter;

  public ClippingView(Context context) {
    super(context);

    DroidOmniApplication.inject(this);
  }

  @Override
  public void setUp(ClippingDto item) {
    this.item = item;
    textContent.setText(item.getContent());
    textSource.setText(item.getClippingProvider().toString().toLowerCase());
    sourceImage.setImageResource(icon.get(item.getClippingProvider()));

    if (item.getType() != ClippingDto.ClippingType.UNKNOWN) {
      smartAction.setVisibility(VISIBLE);
      smartAction.setText(SmartActionService.SMART_ACTIONS.get(item.getType()).getTitle());
      smartAction.setCompoundDrawablesWithIntrinsicBounds(SmartActionService.SMART_ACTIONS.get(item.getType()).getIcon()[1], 0, 0, 0);
    } else {
      smartAction.setVisibility(GONE);
    }
  }

  @Click
  public void deleteClicked() {
    clippingPresenter.remove(item);
  }

  @Click
  public void smartActionClicked() {
    clippingPresenter.smartAction(item);
  }
}

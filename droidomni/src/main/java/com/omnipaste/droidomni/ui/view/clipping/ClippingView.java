package com.omnipaste.droidomni.ui.view.clipping;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.presenter.ClippingPresenter;
import com.omnipaste.droidomni.service.SmartActionService;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EViewGroup
public abstract class ClippingView extends LinearLayout implements HasSetup<ClippingDto> {
  private ClippingDto item;

  @ViewById
  public TextView textContent;

  @ViewById
  public Button smartAction;

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

  @Click
  public void textContent() {
    if (textContent.getMaxLines() == 3) {
      textContent.setMaxLines(Integer.MAX_VALUE);
    }
    else {
      textContent.setMaxLines(3);
    }
  }
}

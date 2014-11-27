package com.omnipaste.droidomni.ui.view.clipping;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.presenter.ClippingPresenter;
import com.omnipaste.droidomni.presenter.ClippingsPresenter;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EViewGroup
public abstract class ClippingView extends LinearLayout implements HasSetup<ClippingDto>, ClippingPresenter.View {
  private ClippingPresenter clippingPresenter;

  @ViewById
  public TextView textContent;

  @ViewById
  public Button smartAction;

  @Inject
  public ClippingsPresenter clippingsPresenter;

  public ClippingView(Context context) {
    super(context);

    DroidOmniApplication.inject(this);
  }

  @Override
  public void setUp(ClippingDto item) {
    clippingPresenter = new ClippingPresenter(item);

    DroidOmniApplication.inject(clippingPresenter);
    clippingPresenter.attachView(this);
    clippingPresenter.initialize();
  }

  @Override
  public void setClippingContent(String content) {
    textContent.setText(content);
  }

  @Override
  public void showSmartAction(int title, int icon) {
    smartAction.setVisibility(VISIBLE);
    smartAction.setText(title);
    smartAction.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
  }

  @Click
  public void deleteClicked() {
    clippingsPresenter.remove(clippingPresenter.getClipping());
  }

  @Click
  public void smartActionClicked() {
    clippingPresenter.smartAction();
  }

  @Click
  public void textContent() {
    if (textContent.getMaxLines() == 3) {
      textContent.setMaxLines(Integer.MAX_VALUE);
    } else {
      textContent.setMaxLines(3);
    }
  }
}

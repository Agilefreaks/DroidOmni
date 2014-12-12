package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.factory.SmartActionFactory;
import com.omnipaste.droidomni.factory.ToastBuilder;
import com.omnipaste.droidomni.interaction.CopyClipping;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

public class ClippingPresenter extends Presenter<ClippingPresenter.View> {
  private ClippingDto clippingDto;

  @Inject
  public SmartActionFactory smartActionFactory;

  @Inject
  public CopyClipping copyClipping;

  @Inject
  public ToastBuilder toastBuilder;

  public interface View {
    void setClippingContent(String content);

    void showSmartAction(int title, int icon);
  }

  public ClippingPresenter(ClippingDto clippingDto) {
    this.clippingDto = clippingDto;
  }

  @Override public void initialize() {
    getView().setClippingContent(clippingDto.getContent());

    if (clippingDto.getType() != ClippingDto.ClippingType.UNKNOWN) {
      getView().showSmartAction(SmartActionFactory.SMART_ACTIONS.get(clippingDto.getType()).getTitle(),
          SmartActionFactory.SMART_ACTIONS.get(clippingDto.getType()).getIcon()[1]);
    }
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }

  public void smartAction() {
    smartActionFactory.run(clippingDto);
  }

  public ClippingDto getClipping() {
    return clippingDto;
  }

  public void copy() {
    copyClipping.run(getClipping());
    toastBuilder.buildShort(R.string.clipping_copy_toast);
  }
}

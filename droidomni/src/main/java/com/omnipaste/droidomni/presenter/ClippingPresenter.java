package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.service.SmartActionService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

public class ClippingPresenter extends Presenter<ClippingPresenter.View> {
  private ClippingDto clippingDto;

  @Inject
  public SmartActionService smartActionService;

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
      getView().showSmartAction(SmartActionService.SMART_ACTIONS.get(clippingDto.getType()).getTitle(),
          SmartActionService.SMART_ACTIONS.get(clippingDto.getType()).getIcon()[1]);
    }
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }

  public void smartAction() {
    smartActionService.run(clippingDto);
  }

  public ClippingDto getClipping() {
    return clippingDto;
  }
}

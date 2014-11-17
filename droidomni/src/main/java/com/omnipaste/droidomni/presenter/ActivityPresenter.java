package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ClippingAdapter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class ActivityPresenter extends FragmentPresenter<ActivityPresenter.View> {

  private final ClippingPresenter clippingPresenter;

  public interface View {
    public void setRefreshing(boolean refreshing);
  }

  @Inject
  public ActivityPresenter(ClippingPresenter clippingPresenter) {
    this.clippingPresenter = clippingPresenter;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  public ClippingAdapter getClippingAdapter() {
    return clippingPresenter.getClippingAdapter();
  }

  public void refresh() {
    getView().setRefreshing(true);

    clippingPresenter.refresh();

    rx.Observable
        .timer(3, TimeUnit.SECONDS)
        .subscribeOn(scheduler)
        .observeOn(observeOnScheduler)
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            getView().setRefreshing(false);
          }
        });
  }
}

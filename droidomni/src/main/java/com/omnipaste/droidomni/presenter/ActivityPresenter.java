package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ClippingAdapter;
import com.omnipaste.droidomni.interaction.Refresh;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class ActivityPresenter extends FragmentPresenter<ActivityPresenter.View> {

  private final ClippingPresenter clippingPresenter;
  private final Refresh refresh;

  public interface View {
    public void setRefreshing(boolean refreshing);
  }

  @Inject
  public ActivityPresenter(ClippingPresenter clippingPresenter, Refresh refresh) {
    this.clippingPresenter = clippingPresenter;
    this.refresh = refresh;
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

    refresh.all();

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

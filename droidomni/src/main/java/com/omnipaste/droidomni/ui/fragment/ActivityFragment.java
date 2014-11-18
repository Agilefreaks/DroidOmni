package com.omnipaste.droidomni.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.ActivityPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_activity)
public class ActivityFragment extends BaseFragment<ActivityPresenter> {
  @Inject
  public ActivityPresenter presenter;

  @ViewById
  public RecyclerView list;

  @ViewById
  public SwipeRefreshLayout swipeRefreshLayout;

  @Override
  protected ActivityPresenter getPresenter() {
    return presenter;
  }

  @AfterViews
  public void afterView() {
    list.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    list.setItemAnimator(new DefaultItemAnimator());
    list.setAdapter(presenter.getClippingAdapter());

    swipeRefreshLayout.setColorSchemeResources(
        R.color.refresh_progress_1,
        R.color.refresh_progress_2,
        R.color.refresh_progress_3);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        Observable
            .timer(10, TimeUnit.SECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                // onNext
                new Action1<Long>() {
                  @Override public void call(Long aLong) {
                    swipeRefreshLayout.setRefreshing(false);
                  }
                });
      }
    });

  }
}

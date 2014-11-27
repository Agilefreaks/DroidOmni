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

import javax.inject.Inject;

@EFragment(R.layout.fragment_activity)
public class ActivityFragment extends BaseFragment<ActivityPresenter> implements ActivityPresenter.View {
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
    list.setAdapter(presenter.getAdapter());

    swipeRefreshLayout.setColorSchemeResources(
        R.color.refresh_progress_1,
        R.color.refresh_progress_2,
        R.color.refresh_progress_3);

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        presenter.refresh();
      }
    });
  }

  @Override
  public void setRefreshing(boolean refreshing) {
    swipeRefreshLayout.setRefreshing(refreshing);
  }
}

package com.omnipaste.droidomni.ui.fragment;

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
public class ActivityFragment extends BaseFragment<ActivityPresenter> {
  @Inject
  public ActivityPresenter presenter;

  @ViewById
  public RecyclerView list;

  @Override
  protected ActivityPresenter getPresenter() {
    return presenter;
  }

  @AfterViews
  public void afterView() {
    list.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    list.setItemAnimator(new DefaultItemAnimator());

    list.setAdapter(presenter.getClippingAdapter());
  }
}

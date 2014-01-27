package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.droidomni.events.ClippingAdded;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();
  private ClippingAdapter clippingAdapter;

  @ViewById
  public ListView clippings;

  @AfterViews
  public void afterView() {
    eventBus.register(this);

    clippingAdapter = new ClippingAdapter();
    clippings.setAdapter(clippingAdapter);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    clippingAdapter.add(event.getClipping());
  }
}

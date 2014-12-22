package com.omnipaste.droidomni.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.ui.view.ContactsSyncView_;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.droidomni.ui.view.clipping.LocalClippingView_;
import com.omnipaste.droidomni.ui.view.clipping.OmniClippingView_;
import com.omnipaste.droidomni.ui.view.event.IncomingCallView_;
import com.omnipaste.droidomni.ui.view.event.IncomingSmsView_;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.EventDto;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
  public final int LIST_SIZE = 42;

  public static ActivityAdapter build() {
    return new ActivityAdapter(new ArrayList<Parcelable>());
  }

  private ArrayList<Parcelable> items;

  private ActivityAdapter(ArrayList<Parcelable> items) {
    this.items = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    return ViewBuilder.getViewHolder(viewGroup.getContext(), viewType);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int index) {
    Parcelable parcelable = items.get(index);
    viewHolder.setUp(parcelable);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public int getItemViewType(int position) {
    Parcelable parcelable = items.get(position);
    return ViewBuilder.getViewType(parcelable);
  }

  public void add(Parcelable parcelable) {
    items.add(0, parcelable);

    if (items.size() >= LIST_SIZE + 10) {
      items.subList(LIST_SIZE, items.size()).clear();
    }

    notifyItemInserted(0);
  }

  public void remove(Parcelable parcelable) {
    int index = items.indexOf(parcelable);
    items.remove(parcelable);

    notifyItemRemoved(index);
  }

  public int getCount() {
    return items.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }

    @SuppressWarnings("unchecked")
    public void setUp(Parcelable parcelable) {
      ((HasSetup) itemView).setUp(parcelable);
    }
  }

  public static class ViewBuilder {
    public static final int LOCAL_CLIPPING = 0;
    public static final int OMNI_CLIPPING = 1;
    public static final int INCOMING_CALL = 2;
    public static final int INCOMING_SMS = 3;
    public static final int CONTACTS_SYNC = 4;

    public static int getViewType(Object item) {
      int viewType = 0;

      if (item instanceof ClippingDto) {
        viewType = ((ClippingDto) item).getClippingProvider() == ClippingDto.ClippingProvider.LOCAL ? 0 : 1;
      } else if (item instanceof EventDto) {
        viewType = ((EventDto) item).getType() == EventDto.EventType.INCOMING_CALL_EVENT ? 2 : 3;
      } else if (item instanceof ContactSyncNotification) {
        viewType = CONTACTS_SYNC;
      }

      return viewType;
    }

    public static ViewHolder getViewHolder(Context context, int viewType) {
      View view = null;

      switch (viewType) {
        case LOCAL_CLIPPING:
          view = LocalClippingView_.build(context);
          break;
        case OMNI_CLIPPING:
          view = OmniClippingView_.build(context);
          break;
        case INCOMING_CALL:
          view = IncomingCallView_.build(context);
          break;
        case INCOMING_SMS:
          view = IncomingSmsView_.build(context);
          break;
        case CONTACTS_SYNC:
          view = ContactsSyncView_.build(context);
        default:
          break;
      }

      return new ViewHolder(view);
    }
  }
}

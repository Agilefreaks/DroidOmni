package com.omnipasteapp.omnipaste.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.ClippingType;
import com.omnipasteapp.omnicommon.models.Sender;
import com.omnipasteapp.omnipaste.R;

import java.util.HashMap;
import java.util.Map;

public class ArrayAdapter2 extends ArrayAdapter<Clipping> {
  private static final Map<ClippingType, Integer> MAP = new HashMap<ClippingType, Integer>() {
    {
      put(ClippingType.PhoneNumber, R.attr.ic_action_call);
      put(ClippingType.URI, null);
      put(ClippingType.Unknown, null);
    }
  };


  public ArrayAdapter2(Context context, int textViewResourceId) {
    super(context, textViewResourceId);
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row;

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.clipping_list_item, null);
    } else {
      row = convertView;
    }

    Clipping clipping = getItem(position);

    // content
    ((TextView) row.findViewById(R.id.content)).setText(clipping.getContent());

    // sender
    ((ImageView) row.findViewById(R.id.sender_image_view)).setImageDrawable(getDrawable(clipping.getSender() == Sender.Local ? R.attr.ic_local : R.attr.ic_omni));

    // action
    Integer actionActionId = MAP.get(clipping.getType());
    if (actionActionId != null) {
      ImageButton imageButton = (ImageButton) row.findViewById(R.id.action_button);

      imageButton.setImageDrawable(getDrawable(actionActionId));
      imageButton.setVisibility(View.VISIBLE);
    }
    else {
      row.findViewById(R.id.action_button).setVisibility(View.GONE);
    }

    return row;
  }

  @SuppressWarnings("ConstantConditions")
  private Drawable getDrawable(int attrId) {
    TypedArray styleAttributes = getContext().getTheme().obtainStyledAttributes(R.style.AppTheme, new int[]{attrId});
    int sourceId = styleAttributes.getResourceId(0, 0);
    styleAttributes.recycle();

    return getContext().getResources().getDrawable(sourceId);
  }
}

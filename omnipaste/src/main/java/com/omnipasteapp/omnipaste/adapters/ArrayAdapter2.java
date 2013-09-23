package com.omnipasteapp.omnipaste.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;
import com.omnipasteapp.omnipaste.R;

public class ArrayAdapter2 extends ArrayAdapter<Clipping> {
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

    TypedArray styleAttributes = getContext().getTheme().obtainStyledAttributes(
        R.style.AppTheme,
        new int[]{clipping.getSender() == Sender.Local ? R.attr.ic_local : R.attr.ic_omni});
    int sourceId = styleAttributes.getResourceId(0, 0);

    ((ImageView) row.findViewById(R.id.senderImageView)).setImageDrawable(getContext().getResources().getDrawable(sourceId));
    styleAttributes.recycle();
    ((TextView) row.findViewById(R.id.content)).setText(clipping.getContent());

    return row;
  }
}

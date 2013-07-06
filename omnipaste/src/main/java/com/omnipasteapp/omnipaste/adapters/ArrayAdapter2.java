package com.omnipasteapp.omnipaste.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class ArrayAdapter2 extends ArrayAdapter<HashMap<String, String>> {
  public ArrayAdapter2(Context context, int textViewResourceId) {
    super(context, textViewResourceId);
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row;

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(android.R.layout.simple_list_item_2, null);
    } else {
      row = convertView;
    }

    HashMap<String, String> data = getItem(position);

    ((TextView) row.findViewById(android.R.id.text1)).setText(data.get("title"));
    ((TextView) row.findViewById(android.R.id.text2)).setText(data.get("subtitle"));

    return row;
  }
}

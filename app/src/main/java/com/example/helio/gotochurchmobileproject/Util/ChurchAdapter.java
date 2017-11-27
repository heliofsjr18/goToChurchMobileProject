package com.example.helio.gotochurchmobileproject.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.helio.gotochurchmobileproject.Basic.Church;
import com.example.helio.gotochurchmobileproject.R;

import java.util.List;

/**
 * Created by dayvson on 21/11/17.
 */

public class ChurchAdapter extends ArrayAdapter<Church> {
    private List<Church> items;

    public ChurchAdapter(Context context, int textViewResourceId, List<Church> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            Context ctx = getContext();
            LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.content_gtc_listview, null);
        }
        Church church = items.get(position);
        if (church != null) {
            ((TextView) v.findViewById(R.id.lbltx1)).setText(church.getName());
            ((TextView) v.findViewById(R.id.lbltx2)).setText(church.getResponsible());

        }
        return v;
    }
}

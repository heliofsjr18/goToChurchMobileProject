package com.example.helio.gotochurchmobileproject.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.helio.gotochurchmobileproject.Basic.Area;
import com.example.helio.gotochurchmobileproject.R;

import java.util.List;


/**
 * Created by gabriel on 02/12/17.
 */

public class AreaAdapter extends ArrayAdapter<Area> {
    private List<Area> items;

    public AreaAdapter(Context context, int textViewResourceId, List<Area> items) {
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
        Area area = items.get(position);
        if (area != null) {
            ((TextView) v.findViewById(R.id.lbltx1)).setText("Area "+String.valueOf(area.getNum()));
            ((TextView) v.findViewById(R.id.lbltx2)).setText("Coordenador: "+area.getCoordenador().getName());

        }
        return v;
    }

}

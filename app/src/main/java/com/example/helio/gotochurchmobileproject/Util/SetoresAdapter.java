package com.example.helio.gotochurchmobileproject.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.helio.gotochurchmobileproject.Basic.Setor;
import com.example.helio.gotochurchmobileproject.R;

import java.util.List;

/**
 * Created by dayvson on 25/11/17.
 */

public class SetoresAdapter extends ArrayAdapter<Setor> {
    private List<Setor> items;

    public SetoresAdapter(Context context, int textViewResourceId, List<Setor> items) {
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
        Setor setor = items.get(position);
        if (setor != null) {
            ((TextView) v.findViewById(R.id.lbltx1)).setText("Setor "+String.valueOf(setor.getNumero()));
            ((TextView) v.findViewById(R.id.lbltx2)).setText("Coordenador: "+setor.getCoordenador().getName());

        }
        return v;
    }
}

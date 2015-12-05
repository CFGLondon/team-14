package com.jpmorgan.team14.addcontrol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Vlad on 05/12/2015.
 */
public class DpoAdapter extends BaseAdapter {

    public String[] dpos;

    public DpoAdapter() {
        dpos = new String[8];
        dpos[0] = "Bangladesh: Dhaka";
        dpos[1] = "Bangladesh: Pabna";
        dpos[2] = "Bangladesh: Barkhada";
        dpos[3] = "Cambodia: Krasang";
        dpos[4] = "Cambodia: Krong";
        dpos[5] = "Cambodia: Rovieng";
        dpos[6] = "Sudan: Ruwaba";
        dpos[7] = "Uganda: Lira";
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public String getItem(int position) {
        return dpos[position];
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView = (LayoutInflater.from(parent.getContext())).inflate(R.layout.row_dpo, parent, false);
        ((TextView) (convertView.findViewById(R.id.dpo_name_list))).setText(dpos[position]);//setText

        return convertView;
    }
}


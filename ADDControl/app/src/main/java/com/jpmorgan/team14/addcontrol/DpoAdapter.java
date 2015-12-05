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
        dpos = new String[4];
        dpos[0] = "Bangladesh: Dhaka";
        dpos[1] = "Cambodia: Krasang";
        dpos[2] = "Sudan: Ruwaba";
        dpos[3] = "Uganda: Lira";
    }

    @Override
    public int getCount() {
        return 4;
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


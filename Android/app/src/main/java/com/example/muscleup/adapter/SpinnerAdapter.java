package com.example.muscleup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muscleup.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    private Context context;
    private List<String> data;

    public SpinnerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = layoutInflater.inflate(R.layout.spinner_custom, viewGroup, false);

        String text = data.get(i);
        ((TextView)view.findViewById(R.id.spinner_custom_tv)).setText(text);

        return view;
    }

    @Override
    public int getCount() {
        if (!data.isEmpty()) return data.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
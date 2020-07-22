package com.salehni.salehni.view.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.salehni.salehni.R;
import com.salehni.salehni.data.model.CarPartsModel;

import java.util.ArrayList;


public class CarPartsGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    ArrayList<CarPartsModel> fields;

    public CarPartsGridViewAdapter(Context context, ArrayList<CarPartsModel> fields) {
        mContext = context;
        this.fields = fields;
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;
        if (vi == null) {

            vi = LayoutInflater.from(mContext).inflate(
                    R.layout.row_grid_view, parent, false);

            holder = new ViewHolder();

            holder.title_Tv = vi
                    .findViewById(R.id.title_Tv);

            holder.pic_Iv = vi
                    .findViewById(R.id.pic_Iv);

            holder.check_radio_Rb = vi
                    .findViewById(R.id.check_radio_Rb);


            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.position = position;

        holder.title_Tv.setText(fields.get(holder.position).getTitle_en());


        return vi;
    }

    class ViewHolder {

        TextView title_Tv;
        ImageView pic_Iv;
        RadioButton check_radio_Rb;
        int position;
    }

}
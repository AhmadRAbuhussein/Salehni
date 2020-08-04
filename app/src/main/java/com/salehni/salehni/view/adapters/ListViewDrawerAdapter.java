package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.DrawerItemModel;

import java.util.ArrayList;


public class ListViewDrawerAdapter extends BaseAdapter {
    private final Context mContext;
    ArrayList<DrawerItemModel> fields;

    public ListViewDrawerAdapter(Context context, ArrayList<DrawerItemModel> fields) {
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
                    R.layout.row_drawer_list, parent, false);

            holder = new ViewHolder();

            holder.title_Tv = vi
                    .findViewById(R.id.title_Tv);

            holder.pic_Iv = vi
                    .findViewById(R.id.pic_Iv);


            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.position = position;

        holder.title_Tv.setText(fields.get(holder.position).getDrawerText());
        holder.pic_Iv.setBackgroundResource(fields.get(holder.position).getDrawerIcon());


        return vi;
    }

    class ViewHolder {

        TextView title_Tv;
        ImageView pic_Iv;
        int position;
    }

}
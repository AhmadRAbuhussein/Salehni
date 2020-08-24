package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.WinchesListModel;

import java.util.ArrayList;

public class WinchesListAdapter extends
        RecyclerView.Adapter<WinchesListAdapter.MyViewHolder> {
    ArrayList<WinchesListModel> winchesListModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView driver_name_Tv;
        public TextView phone_number_Tv;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            driver_name_Tv = (TextView) view.findViewById(R.id.driver_name_Tv);
            phone_number_Tv = (TextView) view.findViewById(R.id.phone_number_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public WinchesListAdapter(Context context,
                              ArrayList<WinchesListModel> winchesListModels,
                              AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.winchesListModels = winchesListModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(WinchesListAdapter.MyViewHolder holder, int position) {

        WinchesListModel winchesListModel = winchesListModels.get(position);

        holder.driver_name_Tv.setText(winchesListModel.getDriver_name());
        holder.phone_number_Tv.setText(winchesListModel.getPhone_number());

    }

    @Override
    public int getItemCount() {
        return winchesListModels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public WinchesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_winches_list_recy_view, parent, false);
        return new WinchesListAdapter.MyViewHolder(v);
    }
}
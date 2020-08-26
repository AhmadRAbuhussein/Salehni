package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import java.text.ParseException;
import java.util.ArrayList;

public class MechanicNotificationAdapter extends
        RecyclerView.Adapter<MechanicNotificationAdapter.MyViewHolder> {
    ArrayList<MechanicNotificationModel> mechanicNotificationModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView go_Iv;
        public TextView customer_name_Tv;
        public TextView description_Tv;
        public TextView time_Tv;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            go_Iv = (ImageView) view.findViewById(R.id.go_Iv);
            customer_name_Tv = (TextView) view.findViewById(R.id.customer_name_Tv);
            description_Tv = (TextView) view.findViewById(R.id.description_Tv);
            time_Tv = (TextView) view.findViewById(R.id.time_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public MechanicNotificationAdapter(Context context,
                                       ArrayList<MechanicNotificationModel> mechanicNotificationModels,
                                       AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mechanicNotificationModels = mechanicNotificationModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MechanicNotificationAdapter.MyViewHolder holder, int position) {

        MechanicNotificationModel mechanicNotificationModel = mechanicNotificationModels.get(position);

        holder.customer_name_Tv.setText(mechanicNotificationModel.getCustomer_name());
        holder.description_Tv.setText(context.getResources().getString(R.string.send_custom_request) + " " + mechanicNotificationModel.getRequest_id());
        try {
            holder.time_Tv.setText(Global.formatDateFromDateString(Constants.DD_MM_YYYY_HH_MM_A, Constants.HH_MM_A, Constants.currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mechanicNotificationModels.size();
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
    public MechanicNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mechanic_notification, parent, false);
        return new MechanicNotificationAdapter.MyViewHolder(v);
    }
}

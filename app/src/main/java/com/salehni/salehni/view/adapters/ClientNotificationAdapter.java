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
import com.salehni.salehni.data.model.ClientNotificationModel;

import java.util.ArrayList;

public class ClientNotificationAdapter extends
        RecyclerView.Adapter<ClientNotificationAdapter.MyViewHolder> {
    ArrayList<ClientNotificationModel> clientNotificationModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView request_img;
        public ImageView go_Iv;
        public TextView request;
        public TextView request_type;
        public TextView clock;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            request_img = (ImageView) view.findViewById(R.id.notfication_Iv);
            go_Iv = (ImageView) view.findViewById(R.id.go_Iv);
            request = (TextView) view.findViewById(R.id.request_Tv);
            request_type = (TextView) view.findViewById(R.id.request_type_Tv);
            clock = (TextView) view.findViewById(R.id.time_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public ClientNotificationAdapter(Context context,
                                     ArrayList<ClientNotificationModel> clientNotificationModels,
                                     AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.clientNotificationModels = clientNotificationModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(ClientNotificationAdapter.MyViewHolder holder, int position) {

        ClientNotificationModel clientNotificationModel = clientNotificationModels.get(position);

    }

    @Override
    public int getItemCount() {
        return clientNotificationModels.size();
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
    public ClientNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_client_notification, parent, false);
        return new ClientNotificationAdapter.MyViewHolder(v);
    }
}

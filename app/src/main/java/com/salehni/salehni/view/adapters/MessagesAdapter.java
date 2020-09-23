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
import com.salehni.salehni.data.model.MessagesModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import java.text.ParseException;
import java.util.ArrayList;

public class MessagesAdapter extends
        RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    ArrayList<MessagesModel> messagesModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sender_name_Tv;
        public TextView date_Tv;
        public TextView message_Tv;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            sender_name_Tv = (TextView) view.findViewById(R.id.sender_name_Tv);
            date_Tv = (TextView) view.findViewById(R.id.date_Tv);
            message_Tv = (TextView) view.findViewById(R.id.message_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public MessagesAdapter(Context context,
                           ArrayList<MessagesModel> messagesModels,
                           AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.messagesModels = messagesModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MessagesAdapter.MyViewHolder holder, int position) {

        MessagesModel messagesModel = messagesModels.get(position);

//        holder.mechanicName_Tv.setText(messagesModel.getMechanic_name());
//        holder.description_Tv.setText(context.getResources().getString(R.string.send_offer) + " " + messagesModel.getRequest_id());
//        try {
//            holder.time_Tv.setText(Global.formatDateFromDateString(Constants.DD_MM_YYYY_HH_MM_A, Constants.HH_MM_A, Constants.currentDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
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
    public MessagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_messages, parent, false);
        return new MessagesAdapter.MyViewHolder(v);
    }
}

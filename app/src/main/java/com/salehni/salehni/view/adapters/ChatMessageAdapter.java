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
import com.salehni.salehni.data.model.ChatMessageModel;
import com.salehni.salehni.data.model.ClientNotificationModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import java.text.ParseException;
import java.util.ArrayList;

public class ChatMessageAdapter extends
        RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder> {
    ArrayList<ChatMessageModel> chatMessageModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout their_message_Ll;
        public LinearLayout my_message_Ll;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);

            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            their_message_Ll = (LinearLayout) view.findViewById(R.id.their_message_Ll);
            my_message_Ll = (LinearLayout) view.findViewById(R.id.my_message_Ll);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public ChatMessageAdapter(Context context,
                              ArrayList<ChatMessageModel> chatMessageModels,
                              AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.chatMessageModels = chatMessageModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(ChatMessageAdapter.MyViewHolder holder, int position) {

        ChatMessageModel chatMessageModel = chatMessageModels.get(position);

        if (chatMessageModel.getId() == 2) {
            holder.their_message_Ll.setVisibility(View.GONE);
        } else {
            holder.my_message_Ll.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessageModels.size();
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
    public ChatMessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_message, parent, false);
        return new ChatMessageAdapter.MyViewHolder(v);
    }
}

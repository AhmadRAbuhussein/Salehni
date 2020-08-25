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
import com.salehni.salehni.data.model.MyRequestModel;

import java.util.ArrayList;

public class MyRequestAdapter extends
        RecyclerView.Adapter<MyRequestAdapter.MyViewHolder> {
    ArrayList<MyRequestModel> myRequestModels;
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
        public TextView time;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            request_img = (ImageView) view.findViewById(R.id.request_img);
            go_Iv = (ImageView) view.findViewById(R.id.go_Iv);
            request = (TextView) view.findViewById(R.id.mechanicName_Tv);
            request_type = (TextView) view.findViewById(R.id.description_Tv);
            time = (TextView) view.findViewById(R.id.time_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public MyRequestAdapter(Context context,
                            ArrayList<MyRequestModel> myRequestModels,
                            AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.myRequestModels = myRequestModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MyRequestAdapter.MyViewHolder holder, int position) {

        MyRequestModel myRequestModel = myRequestModels.get(position);

        holder.request.setText(context.getResources().getString(R.string.request) + " " + myRequestModel.getId());
        holder.time.setText(myRequestModel.getTime());

        //TODO remove this when live data
        if (position == 0) {
            holder.container_Ll.setBackground(context.getResources().getDrawable(R.drawable.shape_my_request_green));
            holder.time.setTextColor(context.getResources().getColor(R.color.green_cards));
        } else {
            holder.container_Ll.setBackground(context.getResources().getDrawable(R.drawable.shape_my_request));
            holder.time.setTextColor(context.getResources().getColor(R.color.clock_color));

        }

    }

    @Override
    public int getItemCount() {
        return myRequestModels.size();
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
    public MyRequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_request, parent, false);
        return new MyRequestAdapter.MyViewHolder(v);
    }
}

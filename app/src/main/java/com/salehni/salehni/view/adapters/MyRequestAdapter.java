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
import com.salehni.salehni.data.model.CarPartsOffersModel;
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
        public TextView request;
        public TextView request_type;
        public TextView clock;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            request_img = (ImageView) view.findViewById(R.id.notfication_Iv);
            request = (TextView) view.findViewById(R.id.request_Tv);
            request_type = (TextView) view.findViewById(R.id.request_type_Tv);
            clock = (TextView) view.findViewById(R.id.clock_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public MyRequestAdapter(Context context,
                            ArrayList<MyRequestModel> carPartsOffersModels,
                            AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.myRequestModels = carPartsOffersModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MyRequestAdapter.MyViewHolder holder, int position) {

        MyRequestModel myRequestModel = myRequestModels.get(position);

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

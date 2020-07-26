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
;
import com.salehni.salehni.data.model.ReuqestOffersModel;

import java.util.ArrayList;

public class RequestOffersRecyViewAdapter extends
        RecyclerView.Adapter<RequestOffersRecyViewAdapter.MyViewHolder> {
    ArrayList<ReuqestOffersModel> reuqestOffersModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView provider_Tv;
        public TextView price_Tv;
        public TextView working_days_Tv;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            provider_Tv = (TextView) view.findViewById(R.id.provider_Tv);
            price_Tv = (TextView) view.findViewById(R.id.price_Tv);
            working_days_Tv = (TextView) view.findViewById(R.id.working_days_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public RequestOffersRecyViewAdapter(Context context,
                                        ArrayList<ReuqestOffersModel> reuqestOffersModels,
                                        AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.reuqestOffersModels = reuqestOffersModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(RequestOffersRecyViewAdapter.MyViewHolder holder, int position) {

        ReuqestOffersModel reuqestOffersModel = reuqestOffersModels.get(position);


    }

    @Override
    public int getItemCount() {
        return reuqestOffersModels.size();
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
    public RequestOffersRecyViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_request_offers_recy_view, parent, false);
        return new RequestOffersRecyViewAdapter.MyViewHolder(v);
    }
}
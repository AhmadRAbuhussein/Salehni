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
import com.salehni.salehni.data.model.RequestOffersDetailsModel;

import java.util.ArrayList;

public class RequestOffersDetailsAdapter extends
        RecyclerView.Adapter<RequestOffersDetailsAdapter.MyViewHolder> {
    ArrayList<RequestOffersDetailsModel> reuqestOffersDetailsModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView item_name_Tv;
        public TextView item_price_Tv;
        public LinearLayout container_Ll;

        public MyViewHolder(View view) {
            super(view);


            container_Ll = (LinearLayout) view.findViewById(R.id.container_Ll);
            item_name_Tv = (TextView) view.findViewById(R.id.item_name_Tv);
            item_price_Tv = (TextView) view.findViewById(R.id.item_price_Tv);

            container_Ll.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public RequestOffersDetailsAdapter(Context context,
                                       ArrayList<RequestOffersDetailsModel> reuqestOffersDetailsModels,
                                       AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.reuqestOffersDetailsModels = reuqestOffersDetailsModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(RequestOffersDetailsAdapter.MyViewHolder holder, int position) {

        RequestOffersDetailsModel reuqestOffersDetailsModel = reuqestOffersDetailsModels.get(position);


    }

    @Override
    public int getItemCount() {
        return reuqestOffersDetailsModels.size();
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
    public RequestOffersDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_request_offers_details_recy_view, parent, false);
        return new RequestOffersDetailsAdapter.MyViewHolder(v);
    }
}
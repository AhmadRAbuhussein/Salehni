package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;
;
import com.salehni.salehni.data.model.CarPartsOffersModel;

import java.util.ArrayList;

public class CarPartsOffersRecyViewAdapter extends
        RecyclerView.Adapter<CarPartsOffersRecyViewAdapter.MyViewHolder> {
    ArrayList<CarPartsOffersModel> carPartsOffersModels;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView provider_Tv;
        public TextView price_Tv;
        public TextView items_Tv;
        public ImageView details_Iv;

        public MyViewHolder(View view) {
            super(view);


            provider_Tv = (TextView) view.findViewById(R.id.provider_Tv);
            price_Tv = (TextView) view.findViewById(R.id.price_Tv);
            items_Tv = (TextView) view.findViewById(R.id.items_Tv);
            details_Iv = (ImageView) view.findViewById(R.id.details_Iv);

            details_Iv.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public CarPartsOffersRecyViewAdapter(Context context,
                                         ArrayList<CarPartsOffersModel> carPartsOffersModels,
                                         AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.carPartsOffersModels = carPartsOffersModels;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(CarPartsOffersRecyViewAdapter.MyViewHolder holder, int position) {

        CarPartsOffersModel carPartsOffersModel = carPartsOffersModels.get(position);

//        holder.fix_pic_Iv.setImageBitmap(Global.convertStringToBitmap(fixingPicturesModel.getImg()));


    }

    @Override
    public int getItemCount() {
        return carPartsOffersModels.size();
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
    public CarPartsOffersRecyViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_offers_recy_view, parent, false);
        return new CarPartsOffersRecyViewAdapter.MyViewHolder(v);
    }
}
package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;
import com.salehni.salehni.util.Global;

import java.util.ArrayList;

public class MechanicImagesRequestAdapter extends
        RecyclerView.Adapter<MechanicImagesRequestAdapter.MyViewHolder> {
    ArrayList<String> images;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public LinearLayout container_Fl;
        public ImageView accedant_pic_Iv;

        public MyViewHolder(View view) {
            super(view);


            container_Fl = (LinearLayout) view.findViewById(R.id.container_Fl);
            accedant_pic_Iv = (ImageView) view.findViewById(R.id.accedant_pic_Iv);

            container_Fl.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public MechanicImagesRequestAdapter(Context context,
                                        ArrayList<String> images,
                                        AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.images = images;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String image = images.get(position);

        holder.accedant_pic_Iv.setImageBitmap(Global.convertStringToBitmap(image));


    }

    @Override
    public int getItemCount() {
        return images.size();
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mehcanic_request_recy_view, parent, false);
        return new MyViewHolder(v);
    }
}

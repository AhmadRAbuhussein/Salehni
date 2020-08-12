package com.salehni.salehni.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.AccedentImagesModel;
import com.salehni.salehni.util.Global;

import java.util.ArrayList;

public class CustomRequestRecyViewAdapter extends
        RecyclerView.Adapter<CustomRequestRecyViewAdapter.MyViewHolder> {
    ArrayList<AccedentImagesModel> customRequestModelRecyViews;
    private AdapterView.OnItemClickListener onItemClickListener;

    Context context;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public FrameLayout container_Fl;
        public ImageView fix_pic_Iv;
        public ImageView removePic_Iv;

        public MyViewHolder(View view) {
            super(view);


            container_Fl = (FrameLayout) view.findViewById(R.id.container_Fl);
            fix_pic_Iv = (ImageView) view.findViewById(R.id.accedant_pic_Iv);
            removePic_Iv = (ImageView) view.findViewById(R.id.removePic_Iv);

            container_Fl.setOnClickListener(this);
            removePic_Iv.setOnClickListener(this);
            fix_pic_Iv.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //passing the clicked position to the parent class
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());

        }
    }

    public CustomRequestRecyViewAdapter(Context context,
                                        ArrayList<AccedentImagesModel> customRequestModelRecyViews,
                                        AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.customRequestModelRecyViews = customRequestModelRecyViews;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AccedentImagesModel accedentImagesModel = customRequestModelRecyViews.get(position);

        holder.fix_pic_Iv.setImageBitmap(Global.convertStringToBitmap(accedentImagesModel.getImg()));


    }

    @Override
    public int getItemCount() {
        return customRequestModelRecyViews.size();
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
                .inflate(R.layout.row_img_recy_view, parent, false);
        return new MyViewHolder(v);
    }
}

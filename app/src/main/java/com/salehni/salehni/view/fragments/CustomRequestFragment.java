package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.view.adapters.FixingPicturesRecyViewAdapter;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.FixingPicturesModel;

import java.util.ArrayList;

public class CustomRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView recyclerView;
    FixingPicturesRecyViewAdapter fixingPicturesRecyViewAdapter;
    ArrayList<FixingPicturesModel> fixingPicturesModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_request, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        testingData();

        return view;
    }

    private void testingData() {

        fixingPicturesModels = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            FixingPicturesModel fixingPicturesModel = new FixingPicturesModel();
            fixingPicturesModel.setId(i + 1);

            fixingPicturesModels.add(fixingPicturesModel);
        }

        intiRecView(fixingPicturesModels);
    }

    public void intiRecView(ArrayList<FixingPicturesModel> fixingPicturesModels) {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fixingPicturesRecyViewAdapter = new FixingPicturesRecyViewAdapter(getActivity(), fixingPicturesModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider));

        recyclerView.addItemDecoration(itemDecorator);


        recyclerView.setAdapter(fixingPicturesRecyViewAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

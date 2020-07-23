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

import com.salehni.salehni.view.adapters.CustomRequestRecyViewAdapter;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.CustomRequestModel;

import java.util.ArrayList;

public class CustomRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView recyclerView;
    CustomRequestRecyViewAdapter customRequestRecyViewAdapter;
    ArrayList<CustomRequestModel> customRequestModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_request, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        testingData();

        return view;
    }

    private void testingData() {

        customRequestModels = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            CustomRequestModel customRequestModel = new CustomRequestModel();
            customRequestModel.setId(i + 1);

            customRequestModels.add(customRequestModel);
        }

        intiRecView(customRequestModels);
    }

    public void intiRecView(ArrayList<CustomRequestModel> customRequestModels) {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        customRequestRecyViewAdapter = new CustomRequestRecyViewAdapter(getActivity(), customRequestModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider));

        recyclerView.addItemDecoration(itemDecorator);


        recyclerView.setAdapter(customRequestRecyViewAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

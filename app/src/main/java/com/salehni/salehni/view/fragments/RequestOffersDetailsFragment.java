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

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.RequestOffersDetailsModel;
import com.salehni.salehni.data.model.ReuqestOffersModel;

import com.salehni.salehni.view.adapters.RequestOffersDetailsAdapter;

import java.util.ArrayList;

public class RequestOffersDetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView items_Rv;
    RequestOffersDetailsAdapter requestOffersDetailsAdapter;
    ArrayList<RequestOffersDetailsModel> reuqestOffersDetailsModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_offers_details, container, false);
        items_Rv = (RecyclerView) view.findViewById(R.id.items_Rv);

        testingData();


        return view;
    }

    private void testingData() {

        reuqestOffersDetailsModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            RequestOffersDetailsModel reuqestOffersDetailsModel = new RequestOffersDetailsModel();
            reuqestOffersDetailsModel.setId(i + 1);

            reuqestOffersDetailsModels.add(reuqestOffersDetailsModel);
        }

        intiRecView(reuqestOffersDetailsModels);
    }

    public void intiRecView(ArrayList<RequestOffersDetailsModel> reuqestOffersDetailsModels) {

        items_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        items_Rv.setLayoutManager(layoutManager);

        requestOffersDetailsAdapter = new RequestOffersDetailsAdapter(getActivity(), reuqestOffersDetailsModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        items_Rv.addItemDecoration(itemDecorator);


        items_Rv.setAdapter(requestOffersDetailsAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

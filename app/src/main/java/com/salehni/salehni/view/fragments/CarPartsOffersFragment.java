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

import com.salehni.salehni.data.model.CarPartsOffersModel;

import com.salehni.salehni.view.adapters.CarPartsOffersRecyViewAdapter;

import java.util.ArrayList;

public class CarPartsOffersFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView carParts_Rv;
    CarPartsOffersRecyViewAdapter offersRecyViewAdapter;
    ArrayList<CarPartsOffersModel> carPartsOffersModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_parts_offers, container, false);
        carParts_Rv = (RecyclerView) view.findViewById(R.id.carParts_Rv);
        carParts_Rv.setNestedScrollingEnabled(false);
        testingData();


        return view;
    }

    private void testingData() {

        carPartsOffersModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            CarPartsOffersModel carPartsOffersModel = new CarPartsOffersModel();
            carPartsOffersModel.setId(i + 1);

            carPartsOffersModels.add(carPartsOffersModel);
        }

        intiRecView(carPartsOffersModels);
    }

    public void intiRecView(ArrayList<CarPartsOffersModel> carPartsOffersModels) {

        carParts_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        carParts_Rv.setLayoutManager(layoutManager);

        offersRecyViewAdapter = new CarPartsOffersRecyViewAdapter(getActivity(), carPartsOffersModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        carParts_Rv.addItemDecoration(itemDecorator);


        carParts_Rv.setAdapter(offersRecyViewAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

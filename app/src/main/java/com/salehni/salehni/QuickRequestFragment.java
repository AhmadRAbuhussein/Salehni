package com.salehni.salehni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class QuickRequestFragment extends Fragment {

    GridView carParts_Gv;
    CarPartsGridViewAdapter carPartsGridViewAdapter;

    ArrayList<CarPartsModel> carPartsModels;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_request, container, false);

        carParts_Gv = view.findViewById(R.id.carParts_Gv);

        carPartsModels = new ArrayList<>();

        for (int i = 0; i < 9; i++) {

            CarPartsModel carPartsModel = new CarPartsModel();
            carPartsModel.setTitle_en("parts " + (i + 1));

            carPartsModels.add(carPartsModel);
        }

        carPartsGridViewAdapter = new CarPartsGridViewAdapter(getActivity(), carPartsModels);
        carParts_Gv.setAdapter(carPartsGridViewAdapter);


        return view;
    }


}
package com.salehni.salehni;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class QuickRequestFragment extends Fragment {

    GridView carParts_Gv;
    CarPartsGridViewAdapter carPartsGridViewAdapter;
    LinearLayout custom_request_Ll;

    ArrayList<CarPartsModel> carPartsModels;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_request, container, false);

        carParts_Gv = view.findViewById(R.id.carParts_Gv);
        custom_request_Ll = view.findViewById(R.id.custom_request_Ll);


        setDrawer();


        custom_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomRequestFragment.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void setDrawer() {
        carPartsModels = new ArrayList<>();

        for (int i = 0; i < 9; i++) {

            CarPartsModel carPartsModel = new CarPartsModel();
            carPartsModel.setTitle_en("parts " + (i + 1));

            carPartsModels.add(carPartsModel);
        }

        carPartsGridViewAdapter = new CarPartsGridViewAdapter(getActivity(), carPartsModels);
        carParts_Gv.setAdapter(carPartsGridViewAdapter);

    }


}
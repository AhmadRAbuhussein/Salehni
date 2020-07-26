package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.QuickRequestModel;
import com.salehni.salehni.view.adapters.QuickRequestGridViewAdapter;

import java.util.ArrayList;

public class QuickRequestFragment extends Fragment {

    GridView carParts_Gv;
    QuickRequestGridViewAdapter quickRequestGridViewAdapter;
    LinearLayout custom_request_Ll, submit_Btn;

    ArrayList<QuickRequestModel> quickRequestModels;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_request, container, false);

        carParts_Gv = view.findViewById(R.id.carParts_Gv);
        custom_request_Ll = view.findViewById(R.id.custom_request_Ll);
        submit_Btn = view.findViewById(R.id.submit_Btn);


        carPartsGrid();

        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestOffersFragment requestOffersFragment = new RequestOffersFragment();
                setFragment(requestOffersFragment);
            }
        });


        custom_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomRequestFragment customRequestFragment = new CustomRequestFragment();
                setFragment(customRequestFragment);
            }
        });


        return view;
    }

    private void carPartsGrid() {
        quickRequestModels = new ArrayList<>();

        for (int i = 0; i < 9; i++) {

            QuickRequestModel quickRequestModel = new QuickRequestModel();
            quickRequestModel.setTitle_en("parts " + (i + 1));

            quickRequestModels.add(quickRequestModel);
        }

        quickRequestGridViewAdapter = new QuickRequestGridViewAdapter(getActivity(), quickRequestModels);
        carParts_Gv.setAdapter(quickRequestGridViewAdapter);

    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
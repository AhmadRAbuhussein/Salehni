package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.ReuqestOffersModel;

import com.salehni.salehni.view.adapters.RequestOffersDetailsAdapter;
import com.salehni.salehni.view.adapters.RequestOffersRecyViewAdapter;

import java.util.ArrayList;

public class RequestOffersFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView carParts_Rv;
    RequestOffersRecyViewAdapter requestOffersRecyViewAdapter;
    ArrayList<ReuqestOffersModel> reuqestOffersModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_offers, container, false);
        carParts_Rv = (RecyclerView) view.findViewById(R.id.carParts_Rv);
        carParts_Rv.setNestedScrollingEnabled(false);
        testingData();


        return view;
    }

    private void testingData() {

        reuqestOffersModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ReuqestOffersModel reuqestOffersDetailsModel = new ReuqestOffersModel();
            reuqestOffersDetailsModel.setId(i + 1);

            reuqestOffersModels.add(reuqestOffersDetailsModel);
        }

        intiRecView(reuqestOffersModels);
    }

    public void intiRecView(ArrayList<ReuqestOffersModel> reuqestOffersModels) {

        carParts_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        carParts_Rv.setLayoutManager(layoutManager);

        requestOffersRecyViewAdapter = new RequestOffersRecyViewAdapter(getActivity(), reuqestOffersModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        carParts_Rv.addItemDecoration(itemDecorator);


        carParts_Rv.setAdapter(requestOffersRecyViewAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        RequestOffersDetailsFragment requestOffersDetailsFragment = new RequestOffersDetailsFragment();
        setFragment(requestOffersDetailsFragment);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

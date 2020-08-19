package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.RequestOffersDetailsModel;

import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.RequestOffersDetailsAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class RequestOffersDetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView items_Rv;
    RequestOffersDetailsAdapter requestOffersDetailsAdapter;
    ArrayList<RequestOffersDetailsModel> requestOffersDetailsModels;

    LinearLayout acceptOffer_Ll;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_offers_details, container, false);
        items_Rv = (RecyclerView) view.findViewById(R.id.items_Rv);
        acceptOffer_Ll = (LinearLayout) view.findViewById(R.id.acceptOffer_Ll);
        acceptOffer_Ll.requestFocus();

        testingData();

        acceptOffer_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WinchesListFragment winchesListFragment = new WinchesListFragment();
                setFragment(winchesListFragment, "winchesListFragment");
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.request_details));

    }

    private void testingData() {

        requestOffersDetailsModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            RequestOffersDetailsModel reuqestOffersDetailsModel = new RequestOffersDetailsModel();
            reuqestOffersDetailsModel.setId(i + 1);

            requestOffersDetailsModels.add(reuqestOffersDetailsModel);
        }

        intiRecView(requestOffersDetailsModels);
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

    public void setFragment(Fragment fragment, String tag) {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

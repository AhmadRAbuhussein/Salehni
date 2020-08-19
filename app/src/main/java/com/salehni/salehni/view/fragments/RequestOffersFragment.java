package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.MyRequestModel;
import com.salehni.salehni.data.model.RequestOffersModel;

import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.RequestOffersRecyViewAdapter;
import com.salehni.salehni.viewmodel.MyRequestViewModel;
import com.salehni.salehni.viewmodel.RequestOffersViewModel;

import java.util.ArrayList;

public class RequestOffersFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView carParts_Rv;
    RequestOffersRecyViewAdapter requestOffersRecyViewAdapter;
    ArrayList<RequestOffersModel> requestOffersModelArrayList;

    RequestOffersViewModel requestOffersViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestOffersModelArrayList = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_offers, container, false);
        carParts_Rv = (RecyclerView) view.findViewById(R.id.carParts_Rv);
        carParts_Rv.setNestedScrollingEnabled(false);
        //testingData();

        requestOffersModelArrayList = new ArrayList<>();

        requestOffersViewModel = ViewModelProviders.of(requireActivity()).get(RequestOffersViewModel.class);
        requestOffersViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        requestOffersViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        requestOffersViewModel.arrayListMutableLiveData.observe(this, new Observer<ArrayList<RequestOffersModel>>() {
            @Override
            public void onChanged(ArrayList<RequestOffersModel> requestOffersModels) {

                if (requestOffersModels != null) {

                    requestOffersModelArrayList.clear();

                    requestOffersModelArrayList.addAll(requestOffersModels);

                    if (requestOffersRecyViewAdapter != null) {

                        requestOffersRecyViewAdapter.notifyDataSetChanged();
                    } else {

                        intiRecView(requestOffersModelArrayList);
                    }

                }

            }


        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        requestOffersViewModel.getData();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.request_offers));
    }

    public void setFragment(Fragment fragment, String tag) {
        requestOffersRecyViewAdapter = null;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    private void testingData() {
//
//        requestOffersModels = new ArrayList<>();
//
//        for (int i = 0; i < 20; i++) {
//            RequestOffersModel reuqestOffersDetailsModel = new RequestOffersModel();
//            reuqestOffersDetailsModel.setId(i + 1);
//
//            requestOffersModels.add(reuqestOffersDetailsModel);
//        }
//
//        intiRecView(requestOffersModels);
//    }

    public void intiRecView(ArrayList<RequestOffersModel> requestOffersModels) {

        carParts_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        carParts_Rv.setLayoutManager(layoutManager);

        requestOffersRecyViewAdapter = new RequestOffersRecyViewAdapter(getActivity(), requestOffersModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        carParts_Rv.addItemDecoration(itemDecorator);


        carParts_Rv.setAdapter(requestOffersRecyViewAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        RequestOffersDetailsFragment requestOffersDetailsFragment = new RequestOffersDetailsFragment();
        setFragment(requestOffersDetailsFragment, "requestOffersDetailsFragment");
    }

//    public void setFragment(Fragment fragment) {
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.mainFrameLayout, fragment, null);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}

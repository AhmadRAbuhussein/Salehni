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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.salehni.salehni.R;

import com.salehni.salehni.data.model.CustomRequestModel;
import com.salehni.salehni.data.model.MyRequestFragModel;
import com.salehni.salehni.data.model.MyRequestModel;

import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.MyRequestAdapter;
import com.salehni.salehni.viewmodel.MyRequestViewModel;

import java.util.ArrayList;


public class MyRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView requests_Rv;
    MyRequestAdapter myRequestAdapter;
    ArrayList<MyRequestModel> myRequestModels;

    MyRequestViewModel myRequestViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request, container, false);

        requests_Rv = (RecyclerView) view.findViewById(R.id.requests_Rv);
        //testingData();

        myRequestViewModel = ViewModelProviders.of(getActivity()).get(MyRequestViewModel.class);
        myRequestViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        myRequestViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        myRequestViewModel.arrayListMutableLiveData.observe(this, new Observer<MyRequestFragModel>() {
            @Override
            public void onChanged(MyRequestFragModel myRequestFragModel) {

            }


        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.my_request));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        RequestOffersFragment requestOffersFragment = new RequestOffersFragment();
        setFragment(requestOffersFragment);

    }

    private void testingData() {

        myRequestModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            MyRequestModel myRequestModel = new MyRequestModel();
            myRequestModel.setId(i + 1);

            myRequestModels.add(myRequestModel);
        }

        intiRecView(myRequestModels);
    }

    public void intiRecView(ArrayList<MyRequestModel> myRequestModels) {

        requests_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        requests_Rv.setLayoutManager(layoutManager);

        myRequestAdapter = new MyRequestAdapter(getActivity(), myRequestModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        requests_Rv.addItemDecoration(itemDecorator);


        requests_Rv.setAdapter(myRequestAdapter);


    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setData(MyRequestFragModel myRequestFragModel) {
        // team_name_tv.setText(myRequestFragModel.team_name);


    }

}

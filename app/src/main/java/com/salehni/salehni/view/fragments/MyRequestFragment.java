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

import com.salehni.salehni.data.model.MyRequestModel;

import com.salehni.salehni.view.adapters.MyRequestAdapter;

import java.util.ArrayList;


public class MyRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView requests_Rv;
    MyRequestAdapter myRequestAdapter;
    ArrayList<MyRequestModel> myRequestModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request, container, false);

        requests_Rv = (RecyclerView) view.findViewById(R.id.requests_Rv);
        testingData();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

}

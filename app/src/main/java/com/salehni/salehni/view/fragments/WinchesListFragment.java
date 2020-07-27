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

import com.salehni.salehni.data.model.WinchesListModel;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.RequestOffersRecyViewAdapter;
import com.salehni.salehni.view.adapters.WinchesListAdapter;

import java.util.ArrayList;

public class WinchesListFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView winches_Rv;
    WinchesListAdapter winchesListAdapter;
    ArrayList<WinchesListModel> winchesListModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winches_list, container, false);
        winches_Rv = (RecyclerView) view.findViewById(R.id.winches_Rv);
        winches_Rv.setNestedScrollingEnabled(false);
        testingData();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.winches_list));
    }

    private void testingData() {

        winchesListModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            WinchesListModel winchesListModel = new WinchesListModel();
            winchesListModel.setId(i + 1);

            winchesListModels.add(winchesListModel);
        }

        intiRecView(winchesListModels);
    }

    public void intiRecView(ArrayList<WinchesListModel> winchesListModels) {

        winches_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        winches_Rv.setLayoutManager(layoutManager);

        winchesListAdapter = new WinchesListAdapter(getActivity(), winchesListModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        winches_Rv.addItemDecoration(itemDecorator);


        winches_Rv.setAdapter(winchesListAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

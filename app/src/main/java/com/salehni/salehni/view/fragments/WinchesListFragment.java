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

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.WinchesListModel;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.WinchesListAdapter;
import com.salehni.salehni.viewmodel.WinchesListViewModel;

import java.util.ArrayList;

public class WinchesListFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView winches_Rv;
    WinchesListAdapter winchesListAdapter;
    ArrayList<WinchesListModel> winchesListArrayList;

    WinchesListViewModel winchesListViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winches_list, container, false);
        winches_Rv = (RecyclerView) view.findViewById(R.id.winches_Rv);
        winches_Rv.setNestedScrollingEnabled(false);

        winchesListArrayList = new ArrayList<>();

        winchesListViewModel = ViewModelProviders.of(requireActivity()).get(WinchesListViewModel.class);
        winchesListViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        winchesListViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        winchesListViewModel.arrayListMutableLiveData.observe(this, new Observer<ArrayList<WinchesListModel>>() {
            @Override
            public void onChanged(ArrayList<WinchesListModel> winchesListModels) {

                if (winchesListModels != null) {

                    winchesListArrayList.clear();

                    winchesListArrayList.addAll(winchesListModels);

                    if (winchesListAdapter != null) {

                        winchesListAdapter.notifyDataSetChanged();
                    } else {

                        intiRecView(winchesListArrayList);
                    }

                }

            }


        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        winchesListViewModel.getData();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.winches_list));
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

        ClientNotificationFragment clientNotificationFragment = new ClientNotificationFragment();
        setFragment(clientNotificationFragment);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

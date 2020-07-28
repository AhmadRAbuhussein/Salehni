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
import com.salehni.salehni.data.model.ClientNotificationModel;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.ClientNotificationAdapter;
import com.salehni.salehni.view.adapters.MechanicNotificationAdapter;

import java.util.ArrayList;


public class MechanicNotificationFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView notification_Rv;
    MechanicNotificationAdapter mechanicNotificationAdapter;
    ArrayList<MechanicNotificationModel> mechanicNotificationModels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mechanic_notification, container, false);

        notification_Rv = (RecyclerView) view.findViewById(R.id.notification_Rv);
        testingData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.notification));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        MechanicRequestFragment mechanicRequestFragment = new MechanicRequestFragment();
        setFragment(mechanicRequestFragment);

    }

    private void testingData() {

        mechanicNotificationModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            MechanicNotificationModel mechanicNotificationModel = new MechanicNotificationModel();
            mechanicNotificationModel.setId(i + 1);

            mechanicNotificationModels.add(mechanicNotificationModel);
        }

        intiRecView(mechanicNotificationModels);
    }

    public void intiRecView(ArrayList<MechanicNotificationModel> mechanicNotificationModels) {

        notification_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notification_Rv.setLayoutManager(layoutManager);

        mechanicNotificationAdapter = new MechanicNotificationAdapter(getActivity(), mechanicNotificationModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        notification_Rv.addItemDecoration(itemDecorator);

        notification_Rv.setAdapter(mechanicNotificationAdapter);

    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

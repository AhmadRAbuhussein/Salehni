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
import com.salehni.salehni.data.model.ClientNotificationModel;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.MechanicNotificationAdapter;
import com.salehni.salehni.viewmodel.ClientNotificationViewModel;
import com.salehni.salehni.viewmodel.MechanicNotificationViewModel;

import java.util.ArrayList;


public class MechanicNotificationFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView notification_Rv;
    MechanicNotificationAdapter mechanicNotificationAdapter;
    ArrayList<MechanicNotificationModel> mechanicNotificationArraylist;

    MechanicNotificationViewModel mechanicNotificationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mechanicNotificationArraylist = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mechanic_notification, container, false);

        notification_Rv = (RecyclerView) view.findViewById(R.id.notification_Rv);

        mechanicNotificationViewModel = ViewModelProviders.of(requireActivity()).get(MechanicNotificationViewModel.class);
        mechanicNotificationViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        mechanicNotificationViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        mechanicNotificationViewModel.arrayListMutableLiveData.observe(this, new Observer<ArrayList<MechanicNotificationModel>>() {
            @Override
            public void onChanged(ArrayList<MechanicNotificationModel> mechanicNotificationModels) {

                if (mechanicNotificationModels != null) {

                    mechanicNotificationArraylist.clear();

                    mechanicNotificationArraylist.addAll(mechanicNotificationModels);

                    if (mechanicNotificationAdapter != null) {

                        mechanicNotificationAdapter.notifyDataSetChanged();
                    } else {

                        intiRecView(mechanicNotificationArraylist);
                    }

                }

            }


        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mechanicNotificationViewModel.getData();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.notification));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        MechanicRequestFragment mechanicRequestFragment = new MechanicRequestFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.selectedMechanicNotification, mechanicNotificationArraylist.get(position));
        mechanicRequestFragment.setArguments(bundle);

        setFragment(mechanicRequestFragment, "mechanicRequestFragment");

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

    public void setFragment(Fragment fragment, String tag) {
        mechanicNotificationAdapter = null;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

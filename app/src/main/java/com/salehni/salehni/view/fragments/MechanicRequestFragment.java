package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRadioButton;
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
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.data.model.MechanicRequestModel;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.MechanicImagesRequestAdapter;
import com.salehni.salehni.viewmodel.MechanicRequestViewModel;

import java.util.ArrayList;

public class MechanicRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    MechanicImagesRequestAdapter mechanicRequestAdapter;

    MechanicRequestViewModel mechanicRequestViewModel;

    LinearLayout send_request_Ll;

    MechanicNotificationModel mechanicNotificationModel;

    AppCompatRadioButton fixAtLocation_Rb;
    AppCompatRadioButton fixAtMechanic_Rb;
    TextView numOfImages_Tv;
    TextView notes_Tv;
    TextView location_Tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mechanic_send_request, container, false);

        img_recycler_view = (RecyclerView) view.findViewById(R.id.img_recycler_view);
        send_request_Ll = (LinearLayout) view.findViewById(R.id.send_request_Ll);
        fixAtLocation_Rb = (AppCompatRadioButton) view.findViewById(R.id.fixAtLocation_Rb);
        fixAtMechanic_Rb = (AppCompatRadioButton) view.findViewById(R.id.fixAtMechanic_Rb);
        numOfImages_Tv = (TextView) view.findViewById(R.id.numOfImages_Tv);
        notes_Tv = (TextView) view.findViewById(R.id.notes_Tv);
        location_Tv = (TextView) view.findViewById(R.id.location_Tv);

        send_request_Ll.requestFocus();

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteYourOfferFragment writeYourOfferFragment = new WriteYourOfferFragment();
                setFragment(writeYourOfferFragment);
            }
        });

        mechanicRequestViewModel = ViewModelProviders.of(requireActivity()).get(MechanicRequestViewModel.class);
        mechanicRequestViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        mechanicRequestViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        mechanicRequestViewModel.mechanicRequestModelMutableLiveData.observe(this, new Observer<MechanicRequestModel>() {
            @Override
            public void onChanged(MechanicRequestModel mechanicRequestModel) {

                if (mechanicRequestModel != null) {

                    setData(mechanicRequestModel);


                }

            }


        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mechanicRequestViewModel.getData(mechanicNotificationModel);

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.send_request));
    }

    public void intiRecView(ArrayList<String> images) {

        img_recycler_view.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        img_recycler_view.setLayoutManager(layoutManager);

        mechanicRequestAdapter = new MechanicImagesRequestAdapter(getActivity(), images, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider));

        img_recycler_view.addItemDecoration(itemDecorator);


        img_recycler_view.setAdapter(mechanicRequestAdapter);


    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setData(MechanicRequestModel mechanicRequestModel) {

        if (Integer.parseInt(mechanicRequestModel.getFix_at()) == 0) {
            fixAtLocation_Rb.setChecked(true);
        } else {
            fixAtMechanic_Rb.setChecked(true);
        }
        numOfImages_Tv.setText(mechanicRequestModel.getImages().size() + " " + getResources().getString(R.string.images_));

        intiRecView(mechanicRequestModel.getImages());

        notes_Tv.setText(mechanicRequestModel.getNotes());

        location_Tv.setText(mechanicRequestModel.getLocation());
    }


}

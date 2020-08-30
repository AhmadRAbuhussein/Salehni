package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.WriteYourOfferModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.viewmodel.WriteYourOfferViewModel;

public class WriteYourOfferFragment extends Fragment {

    LinearLayout send_request_Ll;
    EditText price_Et;
    EditText notes_Et;

    WriteYourOfferViewModel writeYourOfferViewModel;

    String request_id = "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_offer, container, false);

        price_Et = view.findViewById(R.id.price_Et);
        notes_Et = view.findViewById(R.id.notes_Et);
        send_request_Ll = view.findViewById(R.id.send_request_Ll);
        send_request_Ll.requestFocus();

        getExtra();

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {

                    writeYourOfferViewModel.getData(setOfferData());
                }
            }
        });

        writeYourOfferViewModel = ViewModelProviders.of(getActivity()).get(WriteYourOfferViewModel.class);
        writeYourOfferViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        writeYourOfferViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        writeYourOfferViewModel.sendOfferStatusModelMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {

                if (status) {
                    MechanicNotificationFragment mechanicNotificationFragment = new MechanicNotificationFragment();
                    setFragment(mechanicNotificationFragment);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.write_your_offer));
    }

    public void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            request_id = bundle.getString(Constants.request_id);
        }
    }

    public WriteYourOfferModel setOfferData() {

        WriteYourOfferModel writeYourOfferModel = new WriteYourOfferModel();

        writeYourOfferModel.setRequest_id(request_id);
        writeYourOfferModel.setPrice(price_Et.getText().toString());
        writeYourOfferModel.setNote(notes_Et.getText().toString());

        return writeYourOfferModel;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean checkValidation() {

        boolean validation = true;

        if (TextUtils.isEmpty(price_Et.getText().toString().trim())) {
            Global.toast(getActivity(), getResources().getString(R.string.enter_price));
            validation = false;
        }

        return validation;


    }
}

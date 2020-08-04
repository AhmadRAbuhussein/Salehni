package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.salehni.salehni.R;


public class PrivacyPolicyFragment extends Fragment {

    TextView privacyPolicy_Tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        // Inflate the layout for this fragment


        privacyPolicy_Tv = view.findViewById(R.id.privacyPolicy_Tv);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}

package com.salehni.salehni.view.fragments.Drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.salehni.salehni.R;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;


public class TermsConditionFragment extends Fragment {

    TextView termsCondition_Tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_terms_condition, container, false);
        // Inflate the layout for this fragment


        termsCondition_Tv = view.findViewById(R.id.termsCondition_Tv);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.terms_conditions));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}

package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salehni.salehni.R;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;

import java.util.Objects;

public class AcceptedOfferFragment extends Fragment {

    LinearLayout byWinch_Ll;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted_offer, container, false);

        byWinch_Ll = view.findViewById(R.id.byWinch_Ll);
        byWinch_Ll.requestFocus();

        byWinch_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WinchesListFragment winchesListFragment = new WinchesListFragment();
                setFragment(winchesListFragment, "winchesListFragment");
            }
        });

        return view;
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.accepted_offer));
    }
}


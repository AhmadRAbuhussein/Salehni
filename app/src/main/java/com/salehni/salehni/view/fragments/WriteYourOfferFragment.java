package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.salehni.salehni.R;

public class WriteYourOfferFragment extends Fragment {

    LinearLayout send_request_Ll;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_offer, container, false);

        send_request_Ll = view.findViewById(R.id.send_request_Ll);
        send_request_Ll.requestFocus();

        return view;
    }
}

package com.salehni.salehni.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.CustomRequestRecyViewAdapter;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.CustomRequestModel;

import java.util.ArrayList;

public class CustomRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    CustomRequestRecyViewAdapter customRequestRecyViewAdapter;
    ArrayList<CustomRequestModel> customRequestModels;
    LinearLayout send_request_Ll;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_request, container, false);

        send_request_Ll = (LinearLayout) view.findViewById(R.id.send_request_Ll);
        img_recycler_view = (RecyclerView) view.findViewById(R.id.img_recycler_view);
        testingData();

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRequestFragment myRequestFragment = new MyRequestFragment();
                setFragment(myRequestFragment);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.send_request));
    }

    private void testingData() {

        customRequestModels = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            CustomRequestModel customRequestModel = new CustomRequestModel();
            customRequestModel.setId(i + 1);

            customRequestModels.add(customRequestModel);
        }

        intiRecView(customRequestModels);
    }

    public void intiRecView(ArrayList<CustomRequestModel> customRequestModels) {

        img_recycler_view.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        img_recycler_view.setLayoutManager(layoutManager);

        customRequestRecyViewAdapter = new CustomRequestRecyViewAdapter(getActivity(), customRequestModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider));

        img_recycler_view.addItemDecoration(itemDecorator);


        img_recycler_view.setAdapter(customRequestRecyViewAdapter);


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
}

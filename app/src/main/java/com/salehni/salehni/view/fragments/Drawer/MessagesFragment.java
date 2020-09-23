package com.salehni.salehni.view.fragments.Drawer;

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
import com.salehni.salehni.data.model.MessagesModel;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.ClientNotificationAdapter;
import com.salehni.salehni.view.adapters.MessagesAdapter;
import com.salehni.salehni.view.fragments.MechanicNotificationFragment;
import com.salehni.salehni.viewmodel.ClientNotificationViewModel;

import java.util.ArrayList;


public class MessagesFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView MessagesRecyView;
    MessagesAdapter messagesAdapter;
    ArrayList<MessagesModel> messagesModelArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messagesModelArrayList = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        MessagesRecyView = (RecyclerView) view.findViewById(R.id.MessagesRecyView);

        testingData();

        return view;
    }

    private void testingData() {

        messagesModelArrayList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            MessagesModel messagesModel = new MessagesModel();
            messagesModel.setId(i + 1);

            messagesModelArrayList.add(messagesModel);
        }

        intiRecView(messagesModelArrayList);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.messages));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void intiRecView(ArrayList<MessagesModel> messagesModels) {

        MessagesRecyView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        MessagesRecyView.setLayoutManager(layoutManager);

        messagesAdapter = new MessagesAdapter(getActivity(), messagesModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        MessagesRecyView.addItemDecoration(itemDecorator);

        MessagesRecyView.setAdapter(messagesAdapter);

    }

    public void setFragment(Fragment fragment) {
        messagesAdapter = null;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

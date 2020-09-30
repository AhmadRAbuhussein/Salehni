package com.salehni.salehni.view.fragments.Drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.ChatMessageModel;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.ChatMessageAdapter;

import java.util.ArrayList;


public class ChatRoomFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView chat_room_Rv;
    ChatMessageAdapter chatMessageAdapter;
    ArrayList<ChatMessageModel> chatMessageModels;

    EditText chat_text_Tv;
    ImageView send_Btn;

    int i = 1;

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatMessageModels = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        chat_room_Rv = (RecyclerView) view.findViewById(R.id.chat_room_Rv);
        chat_text_Tv = (EditText) view.findViewById(R.id.chat_text_Tv);
        send_Btn = (ImageView) view.findViewById(R.id.send_Btn);

        send_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewUser(String.valueOf(i++), chat_text_Tv.toString());
            }
        });

        testingData();

        return view;
    }

    private void writeNewUser(String userId, String message) {

        mDatabase.child("users").child(userId).setValue(message);
    }


    private void testingData() {

        chatMessageModels = new ArrayList<>();

        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setId(1);
        chatMessageModels.add(chatMessageModel);

        ChatMessageModel chatMessageModel2 = new ChatMessageModel();
        chatMessageModel2.setId(2);
        chatMessageModels.add(chatMessageModel2);

        ChatMessageModel chatMessageModel3 = new ChatMessageModel();
        chatMessageModel3.setId(3);
        chatMessageModels.add(chatMessageModel3);

        ChatMessageModel chatMessageModel4 = new ChatMessageModel();
        chatMessageModel4.setId(2);
        chatMessageModels.add(chatMessageModel4);

        intiRecView(chatMessageModels);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.chat));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void intiRecView(ArrayList<ChatMessageModel> chatMessageModels) {

        chat_room_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        chat_room_Rv.setLayoutManager(layoutManager);

        chatMessageAdapter = new ChatMessageAdapter(getActivity(), chatMessageModels, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        chat_room_Rv.addItemDecoration(itemDecorator);

        chat_room_Rv.setAdapter(chatMessageAdapter);

    }

    public void setFragment(Fragment fragment) {
        chatMessageAdapter = null;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

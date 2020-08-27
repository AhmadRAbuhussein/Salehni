package com.salehni.salehni.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.data.model.MechanicRequestModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.activities.VideoActivity;
import com.salehni.salehni.view.adapters.MechanicImagesRequestAdapter;
import com.salehni.salehni.viewmodel.MechanicRequestViewModel;

import java.util.ArrayList;

import static com.salehni.salehni.util.Constants.selectedVideoPath;
import static com.salehni.salehni.util.MyApplication.context;

public class MechanicRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    MechanicImagesRequestAdapter mechanicRequestAdapter;

    MechanicRequestViewModel mechanicRequestViewModel;

    MechanicRequestModel mechanicRequestModelData;

    LinearLayout send_request_Ll;

    MechanicNotificationModel mechanicNotificationModel;

    ImageView atLocation_Iv;
    ImageView atMechanic_Iv;
    TextView numOfImages_Tv;
    TextView notes_Tv;
    TextView location_Tv;
    TextView watchVideo_Tv;

    PopupWindow popupWindow;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mechanic_send_request, container, false);

        img_recycler_view = (RecyclerView) view.findViewById(R.id.img_recycler_view);
        send_request_Ll = (LinearLayout) view.findViewById(R.id.send_request_Ll);

        atLocation_Iv = (ImageView) view.findViewById(R.id.atLocation_Iv);
        atMechanic_Iv = (ImageView) view.findViewById(R.id.atMechanic_Iv);

        numOfImages_Tv = (TextView) view.findViewById(R.id.numOfImages_Tv);
        notes_Tv = (TextView) view.findViewById(R.id.notes_Tv);
        location_Tv = (TextView) view.findViewById(R.id.location_Tv);
        watchVideo_Tv = (TextView) view.findViewById(R.id.watchVideo_Tv);

        send_request_Ll.requestFocus();

        getExtra();

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

                    mechanicRequestModelData = mechanicRequestModel;
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int accedant_pic_Iv_ID = R.id.accedant_pic_Iv;

        if (view.getId() == accedant_pic_Iv_ID) {
            showImage_popup(mechanicRequestModelData.getImages().get(position));
        }
    }

    public void setData(final MechanicRequestModel mechanicRequestModel) {

        if (Integer.parseInt(mechanicRequestModel.getFix_at()) == 0) {
            atLocation_Iv.setBackground(getResources().getDrawable(R.drawable.radio_checked));
            atMechanic_Iv.setBackground(getResources().getDrawable(R.drawable.radio_unchecked));
        } else {
            atLocation_Iv.setBackground(getResources().getDrawable(R.drawable.radio_unchecked));
            atMechanic_Iv.setBackground(getResources().getDrawable(R.drawable.radio_checked));
        }
        numOfImages_Tv.setText(mechanicRequestModel.getImages().size() + " " + getResources().getString(R.string.images_));

        if (mechanicRequestAdapter != null) {
            mechanicRequestAdapter.notifyDataSetChanged();
        } else {
            intiRecView(mechanicRequestModel.getImages());
        }


        notes_Tv.setText(mechanicRequestModel.getNotes());

        location_Tv.setText(mechanicRequestModel.getLocation());

        watchVideo_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedVideoPath.length() > 0) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(Constants.selectedVideoPath, mechanicRequestModel.getVideo());
                    startActivity(intent);
                }
            }
        });
    }

    public void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mechanicNotificationModel = bundle.getParcelable(Constants.selectedMechanicNotification);
        }

    }

    public void showImage_popup(String img) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.show_image2_popup, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        ImageView removePic_Iv = (ImageView) layout.findViewById(R.id.removePic_Iv);
        ImageView accident_Iv = (ImageView) layout.findViewById(R.id.accident_Iv);

        removePic_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        Glide.with(context)
                .load(img)
                .centerCrop()
                .placeholder(R.color.grey)
                .into(accident_Iv);


        Global.dimBehind(popupWindow);

    }

    private void refreshDataViews(int size) {
        numOfImages_Tv.setText(size + " " + getResources().getString(R.string.images2));

        mechanicRequestAdapter.notifyDataSetChanged();
    }


}


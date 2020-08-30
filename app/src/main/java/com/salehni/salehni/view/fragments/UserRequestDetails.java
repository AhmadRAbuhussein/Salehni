package com.salehni.salehni.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.salehni.salehni.data.model.UserRequestDetailsModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.activities.VideoActivity;
import com.salehni.salehni.view.adapters.MechanicImagesRequestAdapter;
import com.salehni.salehni.viewmodel.UserRequestDetailsViewModel;

import java.util.ArrayList;
import java.util.Locale;

import static com.salehni.salehni.util.Constants.selectedVideoPath;
import static com.salehni.salehni.util.MyApplication.context;

public class UserRequestDetails extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    MechanicImagesRequestAdapter mechanicRequestAdapter;

    UserRequestDetailsViewModel userRequestDetailsViewModel;

    UserRequestDetailsModel userRequestDetailsModelData;

    LinearLayout send_request_Ll;

    MechanicNotificationModel mechanicNotificationModel;

    ImageView atLocation_Iv;
    ImageView atMechanic_Iv;
    TextView numOfImages_Tv;
    TextView notes_Tv;
    TextView location_Tv;
    TextView watchVideo_Tv;
    LinearLayout location_Ll;

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
        location_Ll = (LinearLayout) view.findViewById(R.id.location_Ll);

        send_request_Ll.requestFocus();

        getExtra();

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteYourOfferFragment writeYourOfferFragment = new WriteYourOfferFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Constants.request_id, userRequestDetailsModelData.getRequest_id());
                writeYourOfferFragment.setArguments(bundle);

                setFragment(writeYourOfferFragment);
            }
        });

        userRequestDetailsViewModel = ViewModelProviders.of(requireActivity()).get(UserRequestDetailsViewModel.class);
        userRequestDetailsViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        userRequestDetailsViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        userRequestDetailsViewModel.mechanicRequestModelMutableLiveData.observe(this, new Observer<UserRequestDetailsModel>() {
            @Override
            public void onChanged(UserRequestDetailsModel userRequestDetailsModel) {

                if (userRequestDetailsModel != null) {

                    userRequestDetailsModelData = userRequestDetailsModel;
                    setData(userRequestDetailsModel);


                }

            }


        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        userRequestDetailsViewModel.getData(mechanicNotificationModel);

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.user_request_details));
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
        mechanicRequestAdapter = null;
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
            showImage_popup(userRequestDetailsModelData.getImages().get(position));
        }
    }

    public void setData(final UserRequestDetailsModel userRequestDetailsModel) {

        if (Integer.parseInt(userRequestDetailsModel.getFix_at()) == 0) {
            atLocation_Iv.setBackground(getResources().getDrawable(R.drawable.radio_checked));
            atMechanic_Iv.setBackground(getResources().getDrawable(R.drawable.radio_unchecked));
        } else {
            atLocation_Iv.setBackground(getResources().getDrawable(R.drawable.radio_unchecked));
            atMechanic_Iv.setBackground(getResources().getDrawable(R.drawable.radio_checked));
        }
        numOfImages_Tv.setText(userRequestDetailsModel.getImages().size() + " " + getResources().getString(R.string.images_));

        if (mechanicRequestAdapter != null) {
            mechanicRequestAdapter.notifyDataSetChanged();
        } else {
            intiRecView(userRequestDetailsModel.getImages());
        }


        notes_Tv.setText(userRequestDetailsModel.getNotes());

        location_Tv.setText(userRequestDetailsModel.getLocation());

        watchVideo_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedVideoPath.length() > 0) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(Constants.selectedVideoPath, userRequestDetailsModel.getVideo());
                    startActivity(intent);
                }
            }
        });

        location_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + userRequestDetailsModel.getLat() + "," + userRequestDetailsModel.getLon() + "";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
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


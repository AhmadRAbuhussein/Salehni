package com.salehni.salehni.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.data.model.CustomRequestModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.CustomRequestRecyViewAdapter;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.AccedentImagesModel;
import com.salehni.salehni.viewmodel.CustomRequestViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class CustomRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    CustomRequestRecyViewAdapter customRequestRecyViewAdapter;
    ArrayList<AccedentImagesModel> accedentImagesModels;
    LinearLayout send_request_Ll;

    FrameLayout takeImages_Fl;
    PopupWindow popupWindow;

    CustomRequestViewModel customRequestViewModel;

    String mCurrentPhotoPath = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_request, container, false);

        send_request_Ll = (LinearLayout) view.findViewById(R.id.send_request_Ll);
        img_recycler_view = (RecyclerView) view.findViewById(R.id.img_recycler_view);
        takeImages_Fl = (FrameLayout) view.findViewById(R.id.takeImages_Fl);

        accedentImagesModels = new ArrayList<>();

        takeImages_Fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCameraPermissionGranted()) {

                    takeImagePopup();

                }

            }
        });

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomRequestModel customRequestModel = new CustomRequestModel();

                customRequestModel.setUser_id(1);
                customRequestModel.setFix_at(1);
                customRequestModel.setImg(null);
                customRequestModel.setVideo(null);
                customRequestModel.setLat(0);
                customRequestModel.setLon(0);
                customRequestModel.setLocation("");
                customRequestModel.setNotes("");

                customRequestViewModel.getData(customRequestModel);
            }
        });

        customRequestViewModel = ViewModelProviders.of(getActivity()).get(CustomRequestViewModel.class);
        customRequestViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        customRequestViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        customRequestViewModel.customRequestStatusModelMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {

                if (status) {
                    MyRequestFragment myRequestFragment = new MyRequestFragment();
                    setFragment(myRequestFragment);

                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.send_request));
    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");

                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.openCamera);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.openCamera) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public void intiRecView(ArrayList<AccedentImagesModel> accedentImagesModels) {

        img_recycler_view.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        img_recycler_view.setLayoutManager(layoutManager);

        customRequestRecyViewAdapter = new CustomRequestRecyViewAdapter(getActivity(), accedentImagesModels, this);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        int sremovePic_Iv_ID = R.id.removePic_Iv;

        if (view.getId() == sremovePic_Iv_ID) {

            accedentImagesModels.remove(position);

            customRequestRecyViewAdapter.notifyDataSetChanged();

        }

    }

    private void takeImagePopup() {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.choose_photo_popup, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setWidth(width - 30);
        popupWindow.setHeight(height - 20);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        Button open_cam_Btn = (Button) layout.findViewById(R.id.open_cam_Btn);
        Button gallery_Btn = (Button) layout.findViewById(R.id.gallery_Btn);

        open_cam_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
////                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                try {
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createImageFile()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                startActivityForResult(intent, Constants.openCamera);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, Constants.openCamera);
                }

                popupWindow.dismiss();
            }
        });
        gallery_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.openGallery);

                popupWindow.dismiss();
            }
        });

        Global.dimBehind(popupWindow);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == Constants.openCamera) {

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");

                if (bitmap != null) {
//
                    bitmap = getResizedBitmap(bitmap, 400);
                    String mImageString = Global.convertBitmabToString(bitmap);
                    sendImageToRecyView(mImageString);

                }

            } else if (requestCode == Constants.openGallery) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
//                IDProf.setImageBitmap(thumbnail);
                String mImageString = Global.convertBitmabToString(thumbnail);
                sendImageToRecyView(mImageString);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void sendImageToRecyView(String img) {

        AccedentImagesModel customRequestModelRecyView = new AccedentImagesModel();
        customRequestModelRecyView.setImg(img);

        accedentImagesModels.add(customRequestModelRecyView);

        if (accedentImagesModels != null) {

            if (customRequestRecyViewAdapter != null) {

                customRequestRecyViewAdapter.notifyDataSetChanged();
            } else {

                intiRecView(accedentImagesModels);
            }

        }


    }

}

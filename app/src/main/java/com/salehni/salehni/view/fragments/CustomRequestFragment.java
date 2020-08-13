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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.salehni.salehni.view.activities.VideoActivity;
import com.salehni.salehni.view.adapters.CustomRequestRecyViewAdapter;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.AccedentImagesModel;
import com.salehni.salehni.viewmodel.CustomRequestViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class CustomRequestFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView img_recycler_view;
    CustomRequestRecyViewAdapter customRequestRecyViewAdapter;
    ArrayList<AccedentImagesModel> accedentImagesModels;
    LinearLayout send_request_Ll;

    FrameLayout takeImages_Fl;
    FrameLayout video_Fl;
    PopupWindow popupWindow;
    TextView images_number;
    TextView your_video_Tv;
    TextView retake_Tv;
    TextView locationAddress_Tv;
    LinearLayout location_Ll;

    private LocationManager locationManager;

    double latitude = 0;
    double longitude = 0;

    boolean enabled;

    String selectedVideoPath = "";

    CustomRequestViewModel customRequestViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_request, container, false);

        send_request_Ll = (LinearLayout) view.findViewById(R.id.send_request_Ll);
        img_recycler_view = (RecyclerView) view.findViewById(R.id.img_recycler_view);
        takeImages_Fl = (FrameLayout) view.findViewById(R.id.takeImages_Fl);
        images_number = (TextView) view.findViewById(R.id.images_number);
        video_Fl = (FrameLayout) view.findViewById(R.id.video_Fl);
        location_Ll = (LinearLayout) view.findViewById(R.id.location_Ll);
        your_video_Tv = (TextView) view.findViewById(R.id.your_video_Tv);
        retake_Tv = (TextView) view.findViewById(R.id.retake_Tv);
        locationAddress_Tv = (TextView) view.findViewById(R.id.locationAddress_Tv);

        accedentImagesModels = new ArrayList<>();

        location_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!enabled) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                } else {

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.openLocation);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.openLocation);
                        }
                    } else {
                        getCurrentLocation(getActivity());
                    }
                }

            }
        });

        your_video_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedVideoPath.length() > 0) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(Constants.selectedVideoPath, selectedVideoPath);
                    startActivity(intent);

                }
            }
        });

        retake_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, Constants.openVideo);
                }
            }
        });

        takeImages_Fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCameraPermissionGranted()) {

                    takeImagePopup();
                }
            }
        });

        video_Fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, Constants.openVideo);
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
        } else if (requestCode == Constants.openLocation) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    getCurrentLocation(getActivity());
                }
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        int removePic_Iv_ID = R.id.removePic_Iv;
        int accedant_pic_Iv_ID = R.id.accedant_pic_Iv;

        if (view.getId() == removePic_Iv_ID) {

            accedentImagesModels.remove(position);

            images_number.setText(accedentImagesModels.size() + " images");

            customRequestRecyViewAdapter.notifyDataSetChanged();

        } else if (view.getId() == accedant_pic_Iv_ID) {

            showImage_popup(accedentImagesModels.get(position).getImg(), position);

        }

    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
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
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        Button open_cam_Btn = (Button) layout.findViewById(R.id.open_cam_Btn);
        Button gallery_Btn = (Button) layout.findViewById(R.id.gallery_Btn);
        ImageView popupDismiss_Iv = (ImageView) layout.findViewById(R.id.popupDismiss_Iv);

        popupDismiss_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        open_cam_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            } else if (requestCode == Constants.openVideo) {

                Uri selectedVideoUri = data.getData();
                selectedVideoPath = getRealPathFromURI(selectedVideoUri);
                Toast.makeText(getActivity(), selectedVideoPath, Toast.LENGTH_LONG).show();

                if (selectedVideoPath.length() > 0) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(Constants.selectedVideoPath, selectedVideoPath);
                    startActivity(intent);

                    retake_Tv.setVisibility(View.VISIBLE);
                    your_video_Tv.setText(getResources().getString(R.string.video1));
                }
            }

        } else if (resultCode == RESULT_CANCELED) {

            // User cancelled the video capture
            Toast.makeText(getActivity(), "User cancelled the video capture.", Toast.LENGTH_LONG).show();

        } else {
            // Video capture failed, advise user
            Toast.makeText(getActivity(), "Video capture failed.", Toast.LENGTH_LONG).show();
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

        images_number.setText(accedentImagesModels.size() + " images");
    }

    public String getRealPathFromURI(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    public void getCurrentLocation(Context con) {
        Log.d("Find Location", "in find_location");
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) con.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            locationAddress_Tv.setText(Global.getAddressFromLatLon(getActivity(), latitude, longitude));
                        }

                        public void onProviderDisabled(String provider) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {
                        }
                    });
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                locationAddress_Tv.setText(Global.getAddressFromLatLon(getActivity(), latitude, longitude));
            }
        }
    }

    public void showImage_popup(String img, final int position) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.show_image_popup, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_zoom);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        ImageView accident_Iv = (ImageView) layout.findViewById(R.id.accident_Iv);
        Button remove_Btn = (Button) layout.findViewById(R.id.remove_Btn);
        Button close_Btn = (Button) layout.findViewById(R.id.close_Btn);

        accident_Iv.setImageBitmap(Global.convertStringToBitmap(img));

        close_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        remove_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accedentImagesModels.remove(position);
            }
        });

        Global.dimBehind(popupWindow);

    }
}


package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.data.model.SignInTokenModel;
import com.salehni.salehni.data.model.UserRequestDetailsModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRequestDetailsViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<UserRequestDetailsModel> mechanicRequestModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();

    Context context;

    public UserRequestDetailsViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData(MechanicNotificationModel mechanicNotificationModel, SignInTokenModel signInTokenModel) {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            String request_id = "";
            if (mechanicNotificationModel != null) {
                mechanicNotificationModel.getRequest_id();
            }

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("user_id", signInTokenModel.getId());
                jsonObject.put("request_id", request_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.getUserRequest_Url, headerParams, mRequestBody);

        } else {

            showToastMutableLiveData.setValue(context.getResources().getString(R.string.validationInternetConnection));
        }


    }

    @Override
    public void callbackOnSuccess(String response) {

        showProgressDialogMutableLiveData.setValue(false);

        try {
            JSONObject jsonObject = new JSONObject(response);

            boolean status = jsonObject.getBoolean("status");
            String error = jsonObject.getString("error");
            JSONObject data = jsonObject.getJSONObject("data");

            if (status) {

                UserRequestDetailsModel userRequestDetailsModel = new UserRequestDetailsModel();

                userRequestDetailsModel.setUser_id(data.getInt("user_id"));
                userRequestDetailsModel.setRequest_id(data.getString("request_id"));
                userRequestDetailsModel.setFix_at(data.getString("fix_at"));
                userRequestDetailsModel.setVideo(data.getString("video"));
                userRequestDetailsModel.setLat(data.getString("lat"));
                userRequestDetailsModel.setLon(data.getString("lon"));
                userRequestDetailsModel.setLocation(data.getString("location"));
                userRequestDetailsModel.setNotes(data.getString("notes"));

                JSONArray imagesArray = data.getJSONArray("images");
                ArrayList<String> images = new ArrayList<>();
                for (int i = 0; i < imagesArray.length(); i++) {

                    JSONObject temp = imagesArray.getJSONObject(i);
                    images.add(temp.getString("image"));
                }
                userRequestDetailsModel.setImages(images);

                mechanicRequestModelMutableLiveData.setValue(userRequestDetailsModel);

            } else {
                showToastMutableLiveData.setValue(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

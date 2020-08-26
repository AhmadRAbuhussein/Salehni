package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.ClientNotificationModel;
import com.salehni.salehni.data.model.MechanicNotificationModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MechanicNotificationViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<ArrayList<MechanicNotificationModel>> arrayListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();


    Context context;

    public MechanicNotificationViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData() {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("user_id", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.mechanicNotifications_Url, headerParams, mRequestBody);

        } else {

            showToastMutableLiveData.setValue(context.getResources().getString(R.string.validationInternetConnection));
        }


    }

    @Override
    public void callbackOnSuccess(String response) {

        showProgressDialogMutableLiveData.setValue(false);

        ArrayList<MechanicNotificationModel> mechanicNotificationModels = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

            boolean status = jsonObject.getBoolean("status");
            String error = jsonObject.getString("error");
            JSONObject data = jsonObject.getJSONObject("data");

            if (status) {

                JSONArray notificationArray = data.getJSONArray("notifications");
                for (int i = 0; i < notificationArray.length(); i++) {

                    JSONObject temp = notificationArray.getJSONObject(i);

                    MechanicNotificationModel mechanicNotificationModel = new MechanicNotificationModel();

                    mechanicNotificationModel.setId(temp.getInt("id"));
                    mechanicNotificationModel.setCustomer_name(temp.getString("customer_name"));
                    mechanicNotificationModel.setRequest_id(temp.getString("request_id"));
                    mechanicNotificationModel.setTime(temp.getString("time"));

                    mechanicNotificationModels.add(mechanicNotificationModel);

                }
            } else {
                showToastMutableLiveData.setValue(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayListMutableLiveData.setValue(mechanicNotificationModels);

    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.MyRequestFragModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRequestViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData arrayListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();

    ArrayList<MyRequestFragModel> myRequestFragModels;
    Context context;

    public MyRequestViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData(MyRequestFragModel myRequestFragModel) {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("user_id", myRequestFragModel.getUser_id());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.myRequest_Url, headerParams, mRequestBody);

        } else {

            showToastMutableLiveData.setValue(context.getResources().getString(R.string.validationInternetConnection));
        }


    }

    @Override
    public void callbackOnSuccess(String response) {

        showProgressDialogMutableLiveData.setValue(false);

        boolean status = false;
        String error = "";

        try {
            JSONObject jsonObject = new JSONObject(response);

            status = jsonObject.getBoolean("status");
            error = jsonObject.getString("error");
            JSONObject data = jsonObject.getJSONObject("data");

            if (status) {
                JSONArray requestsArray = data.getJSONArray("requests");
                for (int i = 0; i < requestsArray.length(); i++) {

                    JSONObject temp = requestsArray.getJSONObject(i);

                    MyRequestFragModel myRequestFragModel = new MyRequestFragModel();

                    myRequestFragModel.setUser_id(temp.getInt("id"));
                    myRequestFragModel.setTime(temp.getString("time"));

                    myRequestFragModels.add(myRequestFragModel);

                    showProgressDialogMutableLiveData.setValue(false);
                }
            } else {
                showToastMutableLiveData.setValue(error);
                showProgressDialogMutableLiveData.setValue(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayListMutableLiveData.setValue(myRequestFragModels);

    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

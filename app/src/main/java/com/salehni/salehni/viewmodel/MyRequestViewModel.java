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
import com.salehni.salehni.data.model.MyRequestModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRequestViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<ArrayList<MyRequestModel>> arrayListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();


    Context context;

    public MyRequestViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData() {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                //TODO get user id from tiny db
                jsonObject.put("user_id", "");

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
        ArrayList<MyRequestModel> myRequestModels = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

            status = jsonObject.getBoolean("status");
            error = jsonObject.getString("error");
            JSONObject data = jsonObject.getJSONObject("data");

            if (status) {

                JSONArray requestsArray = data.getJSONArray("requests");
                for (int i = 0; i < requestsArray.length(); i++) {

                    JSONObject temp = requestsArray.getJSONObject(i);

                    MyRequestModel myRequestModel = new MyRequestModel();

                    myRequestModel.setId(temp.getInt("id"));
                    myRequestModel.setTime(temp.getString("time"));

                    myRequestModels.add(myRequestModel);

                }
            } else {
                showToastMutableLiveData.setValue(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayListMutableLiveData.setValue(myRequestModels);

    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

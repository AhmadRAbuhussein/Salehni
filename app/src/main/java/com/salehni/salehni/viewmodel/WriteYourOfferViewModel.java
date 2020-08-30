package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.RequestOffersModel;
import com.salehni.salehni.data.model.WriteYourOfferModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteYourOfferViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<Boolean> sendOfferStatusModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();

    Context context;

    public WriteYourOfferViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData(WriteYourOfferModel writeYourOfferModel) {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("machanic_id", "");
                jsonObject.put("request_id", writeYourOfferModel.getRequest_id());
                jsonObject.put("price", writeYourOfferModel.getPrice());
                jsonObject.put("note", writeYourOfferModel.getNote());
                jsonObject.put("voice_note", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.mechanicSendOffer_Url, headerParams, mRequestBody);

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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (status) {
            sendOfferStatusModelMutableLiveData.setValue(status);
        } else {
            showToastMutableLiveData.setValue(error);
        }


    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.SignInTokenModel;
import com.salehni.salehni.data.model.SigninModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<String> signinTokenStringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();

    Context context;

    public SigninViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData(SigninModel signinModel) {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("mobile_number", signinModel.getPhoneNumber());
                jsonObject.put("country_code", signinModel.getCountry_code());
                jsonObject.put("password", signinModel.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.signin_Url, headerParams, mRequestBody);

        } else {

            showToastMutableLiveData.setValue(context.getResources().getString(R.string.validationInternetConnection));
        }


    }

    @Override
    public void callbackOnSuccess(String response) {

        showProgressDialogMutableLiveData.setValue(false);

        boolean status = false;
        String error = "";
        String token = "";

        try {
            JSONObject jsonObject =
                    new JSONObject(response);

            status = jsonObject.getBoolean("status");
            error = jsonObject.getString("error");

            JSONObject dataJsonObject = jsonObject.getJSONObject("data");

            token = dataJsonObject.getString("token");

            //TODO add token to tiny_DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (status) {

            signinTokenStringMutableLiveData.setValue(token);
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

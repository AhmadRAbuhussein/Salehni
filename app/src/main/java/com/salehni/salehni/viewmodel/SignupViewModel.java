package com.salehni.salehni.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.salehni.salehni.R;
import com.salehni.salehni.data.api.ApiData;
import com.salehni.salehni.data.api.InterfaceApi;
import com.salehni.salehni.data.model.SignupModel;
import com.salehni.salehni.data.model.SignupStatusModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupViewModel extends AndroidViewModel implements InterfaceApi {

    public MutableLiveData<SignupStatusModel> signupStatusModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgressDialogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showToastMutableLiveData = new MutableLiveData<>();

    Context context;

    public SignupViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
    }

    public void getData(SignupModel signupModel) {

        if (Global.isNetworkAvailable(context)) {

            showProgressDialogMutableLiveData.setValue(true);

            Map<String, String> headerParams = new HashMap<String, String>();

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("full_name", signupModel.getName());
                jsonObject.put("mobile_number", signupModel.getPh_no());
                jsonObject.put("country_code", signupModel.getC_code());
                jsonObject.put("email", signupModel.getEmail());
                jsonObject.put("password", signupModel.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = jsonObject.toString();

            ApiData apiData = new ApiData();

            apiData.getdata(this.getApplication(), this, Constants.main_url + Constants.signup_Url, headerParams, mRequestBody);


        } else {

            showToastMutableLiveData.setValue(context.getResources().getString(R.string.validationInternetConnection));
        }


    }

    @Override
    public void callbackOnSuccess(String response) {

        showProgressDialogMutableLiveData.setValue(false);

        SignupStatusModel signupStatusModel = new SignupStatusModel();

        boolean status = false;

        try {
            JSONObject jsonObject = new JSONObject(response);

            status = jsonObject.getBoolean("status");

            JSONObject dataJsonObject = jsonObject.getJSONObject("data");

            String token = dataJsonObject.getString("token");
            String otp = dataJsonObject.getString("otp");

            signupStatusModel.setStatus(status);
            signupStatusModel.setOtp(otp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        signupStatusModelMutableLiveData.setValue(signupStatusModel);

    }

    @Override
    public void callbackOnError(String response) {

        showProgressDialogMutableLiveData.setValue(false);
        showToastMutableLiveData.setValue(response);

    }
}

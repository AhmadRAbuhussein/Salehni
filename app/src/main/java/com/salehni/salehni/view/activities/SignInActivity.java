package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.SignInTokenModel;
import com.salehni.salehni.data.model.SigninModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.util.TinyDB;
import com.salehni.salehni.viewmodel.SigninViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SignInActivity extends AppCompatActivity {

    TextView sign_up_Tv;
    ImageView remeberMeCheckbox;
    Button login_Btn;
    EditText mobileNumber_Et;
    EditText password_Et;
    TextView c_code_Tv;
    TextView forget_password_Tv;

    SigninViewModel signinViewModel;

    TinyDB tinydb;

    boolean isRemember = false;

    SignInTokenModel signInTokenModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tinydb = new TinyDB(this);

        sign_up_Tv = findViewById(R.id.sign_up_Tv);
        remeberMeCheckbox = findViewById(R.id.remeberMeCheckbox);
        login_Btn = findViewById(R.id.login_Btn);

        mobileNumber_Et = findViewById(R.id.mobileNumber_Et);
        password_Et = findViewById(R.id.password_Et);
        c_code_Tv = findViewById(R.id.c_code_Tv);
        forget_password_Tv = findViewById(R.id.forget_password_Tv);

        forget_password_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        sign_up_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    SigninModel signinModel = new SigninModel();

                    signinModel.setPhoneNumber(mobileNumber_Et.getText().toString().trim());
                    signinModel.setCountry_code(c_code_Tv.getText().toString().trim());
                    signinModel.setPassword(password_Et.getText().toString().trim());

                    signinViewModel.getData(signinModel);
                }
            }
        });

        remeberMeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRemember) {

                    isRemember = false;

                    remeberMeCheckbox.setBackground(getResources().getDrawable(R.drawable.checkbox_unselected_icon));

                } else {

                    isRemember = true;

                    remeberMeCheckbox.setBackground(getResources().getDrawable(R.drawable.checkbox_selected_icon));
                }
            }
        });

        signinViewModel = ViewModelProviders.of(this).get(SigninViewModel.class);
        signinViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(SignInActivity.this);
                } else {
                    Global.progressDismiss();
                }

            }
        });

        signinViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getApplicationContext(), s);

            }
        });

        signinViewModel.signinTokenStringMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String token) {
                try {
                    decodedLoginToken(token);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void decodedLoginToken(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

            String info = getJson(split[1]);

            if (info.length() > 0) {
                saveLoginUserInfo(info);
            }
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    private void saveLoginUserInfo(String info) {

        try {
            JSONObject jsonObject = new JSONObject(info);

            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            int user_type = jsonObject.getInt("user_type");

            signInTokenModel = new SignInTokenModel();
            signInTokenModel.setId(id);
            signInTokenModel.setName(name);
            signInTokenModel.setUser_type(user_type);

            tinydb.putObject(Constants.login_token, signInTokenModel);

            if (!id.equalsIgnoreCase("") && user_type != 0) {
                Intent intent = new Intent(SignInActivity.this, MainPageCustomerActivity.class);
                startActivity(intent);
                finish();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean checkValidation() {

        boolean validation = true;

        if (TextUtils.isEmpty(mobileNumber_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.enter_your_number));
            validation = false;
        } else if (TextUtils.isEmpty(password_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.enter_your_ps));
            validation = false;
        }
        return validation;

    }
}

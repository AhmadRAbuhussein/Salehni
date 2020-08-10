package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.salehni.salehni.data.model.SigninModel;
import com.salehni.salehni.data.model.SignupModel;
import com.salehni.salehni.data.model.SignupStatusModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.viewmodel.SigninViewModel;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SignInActivity extends AppCompatActivity {

    TextView sign_up_Tv;
    ImageView remeberMeCheckbox;
    Button login_Btn;
    EditText mobileNumber_Et;
    EditText password_Et;
    TextView c_code_Tv;

    SigninViewModel signinViewModel;

    boolean isRemember = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sign_up_Tv = findViewById(R.id.sign_up_Tv);
        remeberMeCheckbox = findViewById(R.id.remeberMeCheckbox);
        login_Btn = findViewById(R.id.login_Btn);

        mobileNumber_Et = findViewById(R.id.mobileNumber_Et);
        password_Et = findViewById(R.id.password_Et);
        c_code_Tv = findViewById(R.id.c_code_Tv);

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

        signinViewModel.signinStatusModelMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {

                if (status) {
                    Intent intent = new Intent(SignInActivity.this, MainPageCustomerActivity.class);
                    startActivity(intent);
                    finish();

                } else
                    Global.toast(SignInActivity.this, getResources().getString(R.string.enter_your_info));
            }
        });

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

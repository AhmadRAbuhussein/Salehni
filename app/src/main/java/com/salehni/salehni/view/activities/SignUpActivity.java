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
import com.salehni.salehni.data.model.SignupModel;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.viewmodel.SignupViewModel;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SignUpActivity extends AppCompatActivity {

    Button sign_up_Btn;
    ImageView remeberMeCheckbox;

    EditText fullName_Et;
    EditText phNo_Tv;
    EditText email_Et;
    EditText password_Et;
    EditText confirm_ps_Et;
    TextView c_code_Tv;

    SignupViewModel signupViewModel;

    boolean isRemember = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up_Btn = findViewById(R.id.sign_up_Btn);

        fullName_Et = findViewById(R.id.fullName_Et);
        phNo_Tv = findViewById(R.id.phNo_Tv);
        c_code_Tv = findViewById(R.id.c_code_Tv);
        email_Et = findViewById(R.id.email_Et);
        password_Et = findViewById(R.id.password_Et);
        confirm_ps_Et = findViewById(R.id.confirm_ps_Et);

        remeberMeCheckbox = findViewById(R.id.remeberMeCheckbox);

        sign_up_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    SignupModel signupModel = new SignupModel();

                    signupModel.setName(fullName_Et.getText().toString().trim());
                    signupModel.setPh_no(phNo_Tv.getText().toString().trim());
                    signupModel.setC_code(c_code_Tv.getText().toString().trim());
                    signupModel.setEmail(email_Et.getText().toString().trim());
                    signupModel.setPassword(password_Et.getText().toString().trim());

                    signupViewModel.getData(signupModel);
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

        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
        signupViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(SignUpActivity.this);
                } else {
                    Global.progressDismiss();
                }

            }
        });

        signupViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getApplicationContext(), s);

            }
        });

        signupViewModel.booleanMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean signupStatus) {

                if (signupStatus) {
                    Intent intent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
                    startActivity(intent);

                }

            }
        });


    }

    private boolean checkValidation() {

        boolean validation = true;

        if (TextUtils.isEmpty(fullName_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.enter_your_name));
            validation = false;
        } else if (TextUtils.isEmpty(phNo_Tv.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.enter_your_number));
            validation = false;
        } else if (TextUtils.isEmpty(password_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.enter_your_ps));
            validation = false;
        } else if (TextUtils.isEmpty(confirm_ps_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.confrim_ps));
            validation = false;
        } else if (password_Et.getText().toString().trim().length() <= 4) {
            Global.toast(this, getResources().getString(R.string.password_more_than_4));// sawee al toast
            validation = false;
        } else if (!password_Et.getText().toString().trim().equalsIgnoreCase(confirm_ps_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.confrim_ps));// sawee al toast
            validation = false;
        }

        return validation;


    }

}



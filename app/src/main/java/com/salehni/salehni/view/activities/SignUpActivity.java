package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.SignupModel;
import com.salehni.salehni.data.model.SignupStatusModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.util.TinyDB;
import com.salehni.salehni.viewmodel.SignupViewModel;

import java.util.ArrayList;

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
    LinearLayout countryCode_LL;

    PopupWindow popupWindow;

    SignupViewModel signupViewModel;

    static boolean isRemember = false;

    TinyDB tinydb;

    ArrayAdapter adapter;

    ArrayList<String> countries;
    ArrayList<String> countryObjsFilter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tinydb = new TinyDB(this);

        countries = new ArrayList<>();

        sign_up_Btn = findViewById(R.id.sign_up_Btn);

        fullName_Et = findViewById(R.id.fullName_Et);
        phNo_Tv = findViewById(R.id.phNo_Tv);
        c_code_Tv = findViewById(R.id.c_code_Tv);
        countryCode_LL = findViewById(R.id.countryCode_LL);
        email_Et = findViewById(R.id.email_Et);
        password_Et = findViewById(R.id.password_Et);
        confirm_ps_Et = findViewById(R.id.confirm_ps_Et);

        remeberMeCheckbox = findViewById(R.id.remeberMeCheckbox);

        getDefaultCountryCodeValue();

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

        countryCode_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countries = Global.getCountry();

                if (countries.size() > 0) {
                    countryCodePopup();
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

        signupViewModel.signupStatusModelMutableLiveData.observe(this, new Observer<SignupStatusModel>() {
            @Override
            public void onChanged(SignupStatusModel signupStatusModel) {

                if (signupStatusModel.isStatus()) {
                    Intent intent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
                    intent.putExtra(Constants.otp_key, signupStatusModel.getOtp());
                    startActivity(intent);
                    finish();

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
        } else if (!isRemember) {
            Global.toast(this, getResources().getString(R.string.check_privacy_and_policy));
        } else if (password_Et.getText().toString().trim().length() <= 4) {
            Global.toast(this, getResources().getString(R.string.password_more_than_4));// sawee al toast
            validation = false;
        } else if (!password_Et.getText().toString().trim().equalsIgnoreCase(confirm_ps_Et.getText().toString().trim())) {
            Global.toast(this, getResources().getString(R.string.confrim_ps));// sawee al toast
            validation = false;
        }

        return validation;


    }

    private void getDefaultCountryCodeValue() {

//        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String countryCodeValue = tm.getNetworkCountryIso();

        String countryCodeValue = "JO";

        c_code_Tv.setText(Global.getPhone(countryCodeValue));
    }

    private void countryCodePopup() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) SignUpActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_select_country, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setWidth(width - 30);
        popupWindow.setHeight(height - 20);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        ListView popupList_Lv = (ListView) layout.findViewById(R.id.popupList_Lv);
        EditText search_Et = (EditText) layout.findViewById(R.id.search_Et);

        countryObjsFilter = new ArrayList<>(countries);

        if (countries.size() > 0) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryObjsFilter);
            popupList_Lv.setAdapter(adapter);

        }

        search_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {

                    countryObjsFilter.clear();

                    String keyword = s.toString().toLowerCase().trim();

                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).toLowerCase().contains(keyword)) {
                            countryObjsFilter.add(countries.get(i));
                        }
                    }

                    adapter.notifyDataSetChanged();

                } else {

                    countryObjsFilter.clear();

                    for (int i = 0; i < countries.size(); i++) {
                        countryObjsFilter.add(countries.get(i));
                    }

                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popupList_Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c_code_Tv.setText(Global.getPhone(Global.getCountryCode(countryObjsFilter.get(position))));

                popupWindow.dismiss();
            }
        });

        Global.dimBehind(popupWindow);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }
}



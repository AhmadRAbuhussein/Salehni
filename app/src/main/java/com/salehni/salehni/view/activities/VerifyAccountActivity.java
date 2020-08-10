package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mukesh.OtpView;
import com.salehni.salehni.R;
import com.salehni.salehni.data.model.SignupStatusModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class VerifyAccountActivity extends AppCompatActivity {

    Button verify_Btn;

    String otp = "";

    OtpView otpView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account_activity);

        verify_Btn = findViewById(R.id.verify_Btn);
        otpView = findViewById(R.id.otp_view);

        getExtra();

        verify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otpView.getText().toString().equals(otp)) {
                    Intent intent = new Intent(VerifyAccountActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Global.toast(VerifyAccountActivity.this, getResources().getString(R.string.otp_code));
            }
        });

    }

    private void getExtra() {
        Intent intent = getIntent();
        otp = intent.getStringExtra(Constants.otp_key);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(VerifyAccountActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}

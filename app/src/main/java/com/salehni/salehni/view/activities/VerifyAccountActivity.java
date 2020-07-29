package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.salehni.salehni.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class VerifyAccountActivity extends AppCompatActivity {

    Button verify_Btn;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account_activity);

        verify_Btn = findViewById(R.id.verify_Btn);

        verify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyAccountActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}

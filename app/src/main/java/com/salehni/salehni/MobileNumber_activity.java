package com.salehni.salehni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MobileNumber_activity extends AppCompatActivity {

    Button submit_Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        submit_Btn = findViewById(R.id.submit_Btn);

        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MobileNumber_activity.this, VerifyAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}

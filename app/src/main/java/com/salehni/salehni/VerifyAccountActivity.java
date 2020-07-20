package com.salehni.salehni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyAccountActivity extends AppCompatActivity {

    Button verify_Btn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_account_activity);

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

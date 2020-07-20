package com.salehni.salehni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseLanguageActivity extends AppCompatActivity {

    Button en_Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        en_Btn = findViewById(R.id.en_Btn);

        en_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLanguageActivity.this, MobileNumber_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

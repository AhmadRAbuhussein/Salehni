package com.salehni.salehni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    Button sign_up_Btn;
    ImageView remeberMeCheckbox;

    boolean isRemember = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up_Btn = findViewById(R.id.sign_up_Btn);
        remeberMeCheckbox = findViewById(R.id.remeberMeCheckbox);

        sign_up_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
                startActivity(intent);

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
            }
        });
    }
}

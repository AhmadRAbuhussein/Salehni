package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.salehni.salehni.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SignInActivity extends AppCompatActivity {

    TextView sign_up_Tv;
    ImageView remeberMeCheckbox;
    Button login_Btn;

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

        sign_up_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                ;
            }
        });

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, MainPageCustomerActivity.class);
                startActivity(intent);
                finish();
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


    }
}

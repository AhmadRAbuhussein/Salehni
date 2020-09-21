package com.salehni.salehni.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.salehni.salehni.R;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.viewmodel.MainUrlViewModel;
import com.salehni.salehni.viewmodel.SigninViewModel;

import java.util.HashMap;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.content.ContentValues.TAG;
public class SplashScreenActivity extends AppCompatActivity {

    FirebaseRemoteConfig mFirebaseRemoteConfig;
    MainUrlViewModel mainUrlViewModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                    }
                });


        mainUrlViewModel = ViewModelProviders.of(this).get(MainUrlViewModel.class);
        mainUrlViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(SplashScreenActivity.this);
                } else {
                    Global.progressDismiss();
                }

            }
        });

        mainUrlViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getApplicationContext(), s);

            }
        });

        mainUrlViewModel.mainUrlStringMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String main_url) {
                Constants.main_url = main_url;
            }
        });

        delay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainUrlViewModel.getData(this);
    }


    public void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, ChooseLanguageActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
    }
}

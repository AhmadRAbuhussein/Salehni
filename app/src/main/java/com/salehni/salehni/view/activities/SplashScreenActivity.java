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
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.salehni.salehni.R;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.viewmodel.MainUrlViewModel;
import com.salehni.salehni.viewmodel.SigninViewModel;

import java.util.HashMap;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

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

        //remoteConfig();

//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//
//        HashMap<String,Object> defaults = new HashMap<>();
//        defaults.put("mechanic_notification_url",5);
//        mFirebaseRemoteConfig.setDefaultsAsync(defaults);
//        Task<Void> fetch = mFirebaseRemoteConfig.fetch(0);
//        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                mFirebaseRemoteConfig.fetchAndActivate();
//                Constants.mechanicNotifications_Url=mFirebaseRemoteConfig.getString("mechanicNotifications_Url");
//            }
//        });

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

    //    private void remoteConfig() {
//
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
//
//        mFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        if (task.isSuccessful()) {
//                            Constants.main_url = mFirebaseRemoteConfig.getString(Constants.main_key);
//                        } else {
//                            Global.toast(SplashScreenActivity.this, "Fetch failed");
//                        }
//                    }
//                });
//
//    }

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

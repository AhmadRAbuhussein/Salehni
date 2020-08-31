package com.salehni.salehni.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.salehni.salehni.R;
import com.salehni.salehni.data.model.WriteYourOfferModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.viewmodel.WriteYourOfferViewModel;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

public class WriteYourOfferFragment extends Fragment {

    LinearLayout voice_description_Ll;
    LinearLayout send_request_Ll;
    EditText price_Et;
    EditText notes_Et;
    TextView voice_note_time_Tv;
    TextView voice_time_Tv;
    FrameLayout voice_record_Fl;

    WriteYourOfferViewModel writeYourOfferViewModel;

    String request_id = "";

    ImageView start_recording_Iv;
    ImageView stop_recording_Iv;
    ImageView play_recording_Iv;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean audioRecordingPermissionGranted = false;

    private String fileName;
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;

    int time = 0;
    int timePrevious = 0;
    boolean playingCheck = false;
    long startTime = 0;

    Handler handler;
    Runnable runnable;

    RoundedHorizontalProgressBar roundedHorizontalProgressBar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_offer, container, false);

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        voice_note_time_Tv = view.findViewById(R.id.voice_note_time_Tv);
        voice_time_Tv = view.findViewById(R.id.voice_time_Tv);
        start_recording_Iv = view.findViewById(R.id.start_recording_Iv);
        stop_recording_Iv = view.findViewById(R.id.stop_recording_Iv);
        price_Et = view.findViewById(R.id.price_Et);
        notes_Et = view.findViewById(R.id.notes_Et);
        voice_description_Ll = view.findViewById(R.id.voice_description_Ll);
        voice_record_Fl = view.findViewById(R.id.voice_record_Fl);
        play_recording_Iv = view.findViewById(R.id.play_recording_Iv);
        roundedHorizontalProgressBar = view.findViewById(R.id.progress_bar_1);
        send_request_Ll = view.findViewById(R.id.send_request_Ll);
        send_request_Ll.requestFocus();

        getExtra();

        start_recording_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
                stop_recording_Iv.setVisibility(View.VISIBLE);
                voice_note_time_Tv.setText(getResources().getString(R.string.time0));
                voice_time_Tv.setVisibility(View.VISIBLE);
                voice_description_Ll.setVisibility(View.GONE);
                voice_record_Fl.setVisibility(View.GONE);
                playingCheck = true;
            }
        });

        stop_recording_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
                stop_recording_Iv.setVisibility(View.GONE);
                voice_note_time_Tv.setText(getResources().getString(R.string.send_voice_note));
                voice_time_Tv.setVisibility(View.GONE);
                voice_description_Ll.setVisibility(View.VISIBLE);
                voice_record_Fl.setVisibility(View.VISIBLE);
                playingCheck = false;

            }
        });

        play_recording_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playRecording();
            }
        });

        send_request_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {

                    writeYourOfferViewModel.getData(setOfferData());
                }
            }
        });

        writeYourOfferViewModel = ViewModelProviders.of(getActivity()).get(WriteYourOfferViewModel.class);
        writeYourOfferViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        writeYourOfferViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        writeYourOfferViewModel.sendOfferStatusModelMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {

                if (status) {
                    MechanicNotificationFragment mechanicNotificationFragment = new MechanicNotificationFragment();
                    setFragment(mechanicNotificationFragment);
                }
            }
        });

        return view;
    }

    private void threadLooper() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (playingCheck) {

//                    if (time > 0) {
//                        roundedHorizontalProgressBar.animateProgress(60000, time - 1000, time); // (animationDuration, oldProgress, newProgress)
//                    } else {
//                        roundedHorizontalProgressBar.animateProgress(60000, 0, 0); // (animationDuration, oldProgress, newProgress)
//                    }

                    roundedHorizontalProgressBar.animateProgress(60, timePrevious, time);// (animationDuration, oldProgress, newProgress)


                    time += 1;
                    timePrevious = time - 1;

                    countdown();

                    handler.postDelayed(this, 1000L);  // 1 second delay
                } else {
                    stopRecording();
                    stop_recording_Iv.setVisibility(View.GONE);
                    voice_note_time_Tv.setText(getResources().getString(R.string.send_voice_note));
                    voice_time_Tv.setVisibility(View.GONE);
                    voice_description_Ll.setVisibility(View.VISIBLE);
                    voice_record_Fl.setVisibility(View.VISIBLE);
                    playingCheck = false;
                }

            }
        };
        handler.post(runnable);
    }

    private void countdown() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (playingCheck) {
                    try {
                        voice_note_time_Tv.setText(Global.formatDateFromDateString(Constants.SS, Constants.MM_SS, (millisUntilFinished / 1000) + ""));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            public void onFinish() {
            }

        }.start();
    }

    private void startRecording() {
        String uuid = UUID.randomUUID().toString();
        fileName = getActivity().getExternalCacheDir().getAbsolutePath() + "/" + uuid + ".3gp";
        Log.i(WriteYourOfferFragment.class.getSimpleName(), fileName);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(WriteYourOfferFragment.class.getSimpleName() + ":startRecording()", "prepare() failed");
        }

        recorder.start();
        threadLooper();
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                audioRecordingPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        if (!audioRecordingPermissionGranted) {
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.write_your_offer));
    }

    public void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            request_id = bundle.getString(Constants.request_id);
        }
    }

    public WriteYourOfferModel setOfferData() {

        WriteYourOfferModel writeYourOfferModel = new WriteYourOfferModel();

        writeYourOfferModel.setRequest_id(request_id);
        writeYourOfferModel.setPrice(price_Et.getText().toString());
        writeYourOfferModel.setNote(notes_Et.getText().toString());

        return writeYourOfferModel;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean checkValidation() {

        boolean validation = true;

        if (TextUtils.isEmpty(price_Et.getText().toString().trim())) {
            Global.toast(getActivity(), getResources().getString(R.string.enter_price));
            validation = false;
        }

        return validation;


    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(WriteYourOfferFragment.class.getSimpleName() + ":playRecording()", "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

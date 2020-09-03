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
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WriteYourOfferFragment extends Fragment implements Runnable {

    LinearLayout voice_description_Ll;
    LinearLayout send_request_Ll;
    EditText price_Et;
    EditText notes_Et;
    TextView voice_note_time_Tv;
    TextView voice_time_Tv;
    TextView voice_time_description_Tv;
    TextView start_time_tv;
    TextView end_time_Tv;
    FrameLayout voice_record_Fl;

    WriteYourOfferViewModel writeYourOfferViewModel;

    String request_id = "";

    @InjectView(R.id.start_recording_Iv)
    ImageView start_recording_Iv;
    @InjectView(R.id.stop_recording_Iv)
    ImageView stop_recording_Iv;
    @InjectView(R.id.play_recording_Iv)
    ImageView play_recording_Iv;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean audioRecordingPermissionGranted = false;

    private String fileName = "";
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;

    int time = 0;
    int timePrevious = 0;
    boolean playingCheck = false;
    long startTime = 0;
    boolean timerRecorderStart = false;
    boolean progressTimeStart = false;
    int timeOfListenRecord = 0;

    Handler handler;
    Runnable runnable;

    boolean pause = false;
    boolean wasPlaying = false;

    RoundedHorizontalProgressBar roundedHorizontalProgressBar;
    CountDownTimer countDownTimer;

    SeekBar seekBar;
    int currentPosition;
    int total;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_offer, container, false);
        ButterKnife.inject(this, view);
        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        voice_note_time_Tv = view.findViewById(R.id.voice_note_time_Tv);
        voice_time_Tv = view.findViewById(R.id.voice_time_Tv);
//        start_recording_Iv = view.findViewById(R.id.start_recording_Iv);
//        stop_recording_Iv = view.findViewById(R.id.stop_recording_Iv);
        price_Et = view.findViewById(R.id.price_Et);
        notes_Et = view.findViewById(R.id.notes_Et);
        voice_description_Ll = view.findViewById(R.id.voice_description_Ll);
        voice_record_Fl = view.findViewById(R.id.voice_record_Fl);
//        play_recording_Iv = view.findViewById(R.id.play_recording_Iv);
        roundedHorizontalProgressBar = view.findViewById(R.id.progress_bar_1);
        seekBar = view.findViewById(R.id.seekbar);
        voice_time_description_Tv = view.findViewById(R.id.voice_time_description_Tv);
        start_time_tv = view.findViewById(R.id.start_time_tv);
        end_time_Tv = view.findViewById(R.id.end_time_Tv);
        send_request_Ll = view.findViewById(R.id.send_request_Ll);
        send_request_Ll.requestFocus();

        seekBar.setEnabled(false);

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
                playingCheck = false;

            }
        });

        play_recording_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fileName.equalsIgnoreCase("")) {
                    playRecording();
                }

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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                int x = (int) Math.ceil(progress / 1000f);

                if (x > 0 && mediaPlayer != null && !mediaPlayer.isPlaying() && !playingCheck) {
                    clearMediaPlayer();
                    play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
                    seekBar.setProgress(0);
                }

                try {
                    start_time_tv.setText(Global.formatDateFromDateString(Constants.S, Constants.MM_SS, timeOfListenRecord + ""));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                } else {

                    if (!end_time_Tv.getText().toString().equalsIgnoreCase("")) {
                        try {
                            if ((timeOfListenRecord + "").equalsIgnoreCase(Global.formatDateFromDateString(Constants.MM_SS, Constants.S, end_time_Tv.getText().toString()))) {
                                {
                                    clearMediaPlayer();
//                            play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
                                    seekBar.setProgress(0);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
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

                    if (!progressTimeStart) {
                        progressTimeStart = true;
                        roundedHorizontalProgressBar.animateProgress(60000, 100);// (animationDuration, oldProgress, newProgress)

                    }


                    time += 1;

                    if (!timerRecorderStart) {
                        timerRecorderStart = true;
                        countdown();
                    }


                    handler.postDelayed(this, 1000);  // 1 second delay
                } else {
                    stopRecording();

                }

            }
        };
        handler.post(runnable);
    }

    @Override
    public void run() {

        int currentPosition = mediaPlayer.getCurrentPosition();
        int total = mediaPlayer.getDuration();


        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
            try {

                Thread.sleep(1000);

                currentPosition = mediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }


            seekBar.setProgress(currentPosition);

            timeOfListenRecord += 1;
        }
    }

    private void countdown() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (playingCheck) {
                    voice_note_time_Tv.setText(Global.convertDate((millisUntilFinished) + "", Constants.MM_SS));
                }

            }

            public void onFinish() {

                playingCheck = false;
            }

        }.start();
    }

    private void startRecording() {

        clearMediaPlayer();
        play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
        seekBar.setProgress(0);

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
        roundedHorizontalProgressBar.clearAnimation();
        stop_recording_Iv.setVisibility(View.GONE);
        voice_note_time_Tv.setText(getResources().getString(R.string.send_voice_note));
        voice_time_Tv.setVisibility(View.GONE);
        voice_description_Ll.setVisibility(View.VISIBLE);
        roundedHorizontalProgressBar.setProgress(0);
        countDownTimer.cancel();


        try {
            voice_time_description_Tv.setText(getResources().getString(R.string.sound1) + " " + Global.formatDateFromDateString(Constants.S, Constants.MM_SS, time + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        voice_record_Fl.setVisibility(View.VISIBLE);
        try {
            end_time_Tv.setText(Global.formatDateFromDateString(Constants.S, Constants.MM_SS, time + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        playingCheck = false;
        timerRecorderStart = false;
        progressTimeStart = false;

        if (recorder != null) {
            recorder.release();
            recorder = null;
        }


        time = 0;
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

    public void playRecording() {

        try {

            seekBar.setEnabled(true);

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                clearMediaPlayer();
//                seekBar.setProgress(0);
                pauseMediaPlayer();
                wasPlaying = true;
                play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
            }


            if (!wasPlaying) {


                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }

                if (pause) {
                    pause = false;
                    mediaPlayer.start();

                    play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pause));

                    new Thread((Runnable) this).start();
                } else {
                    play_recording_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pause));

                    mediaPlayer.setDataSource(fileName);

                    mediaPlayer.prepare();
                    mediaPlayer.setVolume(0.5f, 0.5f);
                    mediaPlayer.setLooping(false);
                    seekBar.setMax(mediaPlayer.getDuration());

                    mediaPlayer.start();
                    new Thread((Runnable) this).start();
                }
                currentPosition = mediaPlayer.getCurrentPosition();
                total = mediaPlayer.getDuration();
            }

            wasPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void pauseMediaPlayer() {

        if (mediaPlayer != null) {
            pause = true;
            mediaPlayer.pause();
        }

    }

//    private void playRecording() {
//        mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(fileName);
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    stopPlaying();
//                }
//            });
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            Log.e(WriteYourOfferFragment.class.getSimpleName() + ":playRecording()", "prepare() failed");
//        }
//    }
//
//    private void stopPlaying() {
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }

    private void clearMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            timeOfListenRecord = 0;
        }

    }
}

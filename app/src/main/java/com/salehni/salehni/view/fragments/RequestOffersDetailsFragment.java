package com.salehni.salehni.view.fragments;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salehni.salehni.R;

import com.salehni.salehni.data.model.ItemsInnerObject;

import com.salehni.salehni.data.model.RequestOffersModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.view.activities.MainPageCustomerActivity;
import com.salehni.salehni.view.adapters.RequestOffersDetailsAdapter;
import com.salehni.salehni.viewmodel.AcceptOfferViewModel;
import com.salehni.salehni.viewmodel.CustomRequestViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RequestOffersDetailsFragment extends Fragment implements AdapterView.OnItemClickListener, Runnable {

    RecyclerView items_Rv;
    RequestOffersDetailsAdapter requestOffersDetailsAdapter;
//    ArrayList<RequestOffersDetailsModel> requestOffersDetailsModels;

    LinearLayout acceptOffer_Ll;

    RequestOffersModel requestOffersModel;

    TextView totalPrice_Tv;
    TextView working_days_Tv;
    TextView note_Tv;
    TextView sumPrice_Tv;

    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    boolean wasPlaying = false;
    ImageView playPause_Iv;
    ImageView share_IV;

    int fix_at = 1;

    boolean pause = false;

    AcceptOfferViewModel acceptOfferViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayer = new MediaPlayer();

        acceptOfferViewModel = ViewModelProviders.of(getActivity()).get(AcceptOfferViewModel.class);
        acceptOfferViewModel.showProgressDialogMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Global.progress(getActivity());
                } else {
                    Global.progressDismiss();
                }

            }
        });

        acceptOfferViewModel.showToastMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Global.toast(getActivity().getApplicationContext(), s);

            }
        });

        acceptOfferViewModel.acceptOfferStatusModelMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {

                if (status) {

                    if (fix_at == 0) {

                    } else {
                        AcceptedOfferFragment acceptedOfferFragment = new AcceptedOfferFragment();
                        setFragment(acceptedOfferFragment, "acceptedOfferFragment");
                    }
                }
            }
        });


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_offers_details, container, false);
        items_Rv = (RecyclerView) view.findViewById(R.id.items_Rv);
        acceptOffer_Ll = (LinearLayout) view.findViewById(R.id.acceptOffer_Ll);

        totalPrice_Tv = (TextView) view.findViewById(R.id.totalPrice_Tv);
        working_days_Tv = (TextView) view.findViewById(R.id.working_days_Tv);
        note_Tv = (TextView) view.findViewById(R.id.note_Tv);
        sumPrice_Tv = (TextView) view.findViewById(R.id.sumPrice_Tv);
        playPause_Iv = (ImageView) view.findViewById(R.id.playPause_Iv);
        share_IV = (ImageView) view.findViewById(R.id.share_IV);
        seekBar = view.findViewById(R.id.seekbar);
        acceptOffer_Ll.requestFocus();

        seekBar.setEnabled(false);

        acceptOffer_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptOfferViewModel.getData(requestOffersModel);
            }
        });

        getExtra();
        setData();
        initialItemsList();

        share_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, requestOffersModel.getOfferInnerObject().getVoice_note());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        playPause_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!requestOffersModel.getOfferInnerObject().getVoice_note().equalsIgnoreCase("")) {
                    playSong();
                }

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                int x = (int) Math.ceil(progress / 1000f);

                if (x > 0 && mediaPlayer != null && !mediaPlayer.isPlaying() && !pause) {
                    clearMediaPlayer();
                    playPause_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
                    seekBar.setProgress(0);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }

            }

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainPageCustomerActivity.title_Tv.setText(getResources().getString(R.string.request_details));

    }

    private void initialItemsList() {

        intiRecView(requestOffersModel.getOfferInnerObject().getItemsInnerObjects());
    }

    public void intiRecView(ArrayList<ItemsInnerObject> itemsInnerObjects) {

        items_Rv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        items_Rv.setLayoutManager(layoutManager);

        requestOffersDetailsAdapter = new RequestOffersDetailsAdapter(getActivity(), itemsInnerObjects, this);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recycleview_divider_height));

        items_Rv.addItemDecoration(itemDecorator);


        items_Rv.setAdapter(requestOffersDetailsAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setFragment(Fragment fragment, String tag) {
        requestOffersDetailsAdapter = null;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            requestOffersModel = bundle.getParcelable(Constants.selectedRequest);

            fix_at = requestOffersModel.getFix_at();
        }
    }

    public void setData() {
        totalPrice_Tv.setText(requestOffersModel.getTotal_price());
        working_days_Tv.setText(requestOffersModel.getWorking_days());
        note_Tv.setText(requestOffersModel.getOfferInnerObject().getNote());
        sumPrice_Tv.setText(setSumPrice());
    }

    public String setSumPrice() {
        int sum = 0;
        for (int i = 0; i < requestOffersModel.getOfferInnerObject().getItemsInnerObjects().size(); i++) {
            sum = sum + Integer.parseInt(requestOffersModel.getOfferInnerObject().getItemsInnerObjects().get(i).getPrice());
        }
        return sum + "";
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

        }
    }

    public void playSong() {

        try {

            seekBar.setEnabled(true);

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                clearMediaPlayer();
//                seekBar.setProgress(0);
                pauseMediaPlayer();
                wasPlaying = true;
                playPause_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.play));
            }


            if (!wasPlaying) {


                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }

                if (pause) {
                    pause = false;
                    mediaPlayer.start();

                    playPause_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pause));

                    new Thread(this).start();
                } else {
                    playPause_Iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pause));

                    mediaPlayer.setDataSource(requestOffersModel.getOfferInnerObject().getVoice_note());

                    mediaPlayer.prepare();
                    mediaPlayer.setVolume(0.5f, 0.5f);
                    mediaPlayer.setLooping(false);
                    seekBar.setMax(mediaPlayer.getDuration());

                    mediaPlayer.start();
                    new Thread(this).start();
                }


            }

            wasPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onDestroy() {

        clearMediaPlayer();

        super.onDestroy();

    }

    private void clearMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    private void pauseMediaPlayer() {

        if (mediaPlayer != null) {
            pause = true;
            mediaPlayer.pause();
        }

    }

}

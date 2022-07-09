package com.bastkiller.sound.mosquitosoundkiller;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bastkiller.sound.mosquitosoundkiller.hxaudio.model.audio.HXSound;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int mProgres = 1;
    private int songIndex = 0;
    private boolean isPlaying = true;
    private boolean status;


    List<Integer> songList = Arrays.asList(R.raw.sound9, R.raw.sound10, R.raw.sound11, R.raw.sound12, R.raw.sound13,
            R.raw.sound14, R.raw.sound15, R.raw.sound16, R.raw.sound17, R.raw.sound18, R.raw.sound19,
            R.raw.sound20, R.raw.sound21, R.raw.sound22);

    ImageView imageView, imageView2;
    ToggleButton btn_on_of;
    Croller croller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        status = getPreferences(MODE_PRIVATE).getBoolean("song_status", false);
        songIndex = getPreferences(MODE_PRIVATE).getInt("song_index", 1);
        croller.setLabel("" + (songIndex + 8) + " kHz");


        if (status) {
            btn_on_of.setChecked(true);
            btn_on_of.setTextColor(getResources().getColor(R.color.toogle_on));

        } else {
            btn_on_of.setChecked(false);
            btn_on_of.setTextColor(getResources().getColor(R.color.toogle_off));
        }


        saveStatus(false);


        if (btn_on_of.isChecked()) {
            playSound();
        } else if (!btn_on_of.isChecked()) {
            pouseSound();
        }

        btn_on_of.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                playSound();
                btn_on_of.setTextColor(getResources().getColor(R.color.toogle_on));
            } else if (!isChecked) {
                pouseSound();
                btn_on_of.setTextColor(getResources().getColor(R.color.toogle_off));
            }

        });


        croller.setProgress(songIndex);
        if (croller.getProgress() == 0)
            croller.setLabel("");
        croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                int progress = croller.getProgress();
                if (progress > 0) {
                    croller.setLabel("" + (progress + 8) + " kHz");
                    songIndex = progress - 1;
                    saveIndex();
                    if (isPlaying) {
                        playSound();
                    } else {
                        pouseSound();
                    }
                }
            }
        });


    }


    private void initView() {
        btn_on_of = findViewById(R.id.toogl_button);
        croller = findViewById(R.id.croller);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        YoYo.with(Techniques.StandUp)
                .duration(3000)
                .playOn(imageView2);


    }

    private void pouseSound() {
        HXSound.pause();
        isPlaying = false;
    }

    private void playSound() {
        HXSound.pause();
        //HXSound.clear();
        HXSound.sound()
                .load(songList.get(songIndex))
                .looped(true)
                .play(MainActivity.this);
        isPlaying = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveIndex();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (btn_on_of.isChecked())
            saveStatus(true);

    }

    private void saveIndex() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("song_index", songIndex);
        editor.apply();
    }

    private void saveStatus(boolean status) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("song_status", status);
        editor.apply();
    }


}

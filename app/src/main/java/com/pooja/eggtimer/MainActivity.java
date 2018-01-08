package com.pooja.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button controlButton;
    Boolean activeCounter;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        controlButton = (Button) findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setCounter(View view) {
        Log.i("button", "tapped");
        if (activeCounter == false) {
            activeCounter = true;
            seekBar.setEnabled(false);
            controlButton.setText("Stop");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                   // Log.i("Finish", "timer");
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        }
        else {
            resetTimer();
        }

    }

    public void updateTimer(int secondsLeft) {
        int min = (int) secondsLeft / 60;
        int sec = (int) secondsLeft - min * 60;

        String secStr = Integer.toString(sec);
        if (sec <= 9) {
            secStr = "0" + secStr;
        }
        textView.setText(Integer.toString(min) + ":" + secStr);
    }

    public void resetTimer() {
        //Log.i("reset", "timer");
        textView.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("Go!");
        seekBar.setEnabled(true);
        activeCounter = false;
    }


}

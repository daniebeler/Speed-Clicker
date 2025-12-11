package com.daniebeler.speed_clicker;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    TextView tvCounter;
    TextView tvTime;
    TextView tvStart;
    TextView tvHighscore;
    Button btnClicker;

    int KontrollInt = 1, iScore;

    boolean bLeftGame = false;
    boolean bTimeIsRunning = false;

    SharedPreferences shHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvCounter = findViewById(R.id.idCounter);
        tvTime = findViewById(R.id.idTime);
        tvStart = findViewById(R.id.idStart);
        tvHighscore = findViewById(R.id.idHighscore);
        btnClicker =  findViewById(R.id.idClicker);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tvStart, "textColor",
                Color.TRANSPARENT, Color.WHITE);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(500);
        colorAnim.start();

        ObjectAnimator colorAnim2 = ObjectAnimator.ofInt(tvTime, "textColor",
                Color.TRANSPARENT, Color.WHITE);
        colorAnim2.setEvaluator(new ArgbEvaluator());
        colorAnim2.setDuration(500);
        colorAnim2.start();

        ObjectAnimator colorAnim3 = ObjectAnimator.ofInt(tvCounter, "textColor",
                Color.TRANSPARENT, Color.WHITE);
        colorAnim3.setEvaluator(new ArgbEvaluator());
        colorAnim3.setDuration(500);
        colorAnim3.start();

        shHighscore = getApplicationContext().getSharedPreferences("highscore", 0);

        if(shHighscore.getInt("highscore", 0) > 0){
            tvHighscore.setText("Highscore: " + shHighscore.getInt("highscore", 0));
            ObjectAnimator colorAnim4 = ObjectAnimator.ofInt(tvHighscore, "textColor",
                    Color.TRANSPARENT, Color.WHITE);
            colorAnim4.setEvaluator(new ArgbEvaluator());
            colorAnim4.setDuration(500);
            colorAnim4.start();
        }
        else{
            tvHighscore.setTextColor(Color.TRANSPARENT);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        //SoundService.StartMusic(0);
        if (bLeftGame){
            startActivity(new Intent(GameActivity.this, GameActivity.class));
        }

        btnClicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                    if(KontrollInt == 2){
                        //SoundService.ClickSound();
                        iScore++;
                        tvCounter.setText(String.valueOf(iScore));
                        tvCounter.startAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.anim_shake));
                    }
                    else if (KontrollInt == 1 && !bTimeIsRunning) {
                        startonefingerClick();
                    }
                }
                return false;
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        //SoundService.PauseMusic();
    }

    private void startonefingerClick() {
        bTimeIsRunning = true;
        KontrollInt = 2;
        iScore = 0;

        //SoundService.ClickSound();
        iScore++;
        tvCounter.setText(String.valueOf(iScore));
        tvCounter.startAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.anim_shake));

        if(tvHighscore.getCurrentTextColor() == Color.WHITE){
            ObjectAnimator colorAnim = ObjectAnimator.ofInt(tvHighscore, "textColor",
                    Color.WHITE, Color.TRANSPARENT);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setDuration(500);
            colorAnim.start();
        }

        ObjectAnimator colorAnim2 = ObjectAnimator.ofInt(tvStart, "textColor",
                Color.WHITE, Color.TRANSPARENT);
        colorAnim2.setEvaluator(new ArgbEvaluator());
        colorAnim2.setDuration(500);
        colorAnim2.start();

        new CountDownTimer(10 * 1000, 10) {
            @Override
            public void onTick(long millis) {
                tvTime.setText(String.format("%.1f", (float) millis / 1000));
            }

            @Override
            public void onFinish() {
                if (!bLeftGame){
                    KontrollInt = 1;

                    if(iScore > shHighscore.getInt("highscore", 0)){
                        shHighscore.edit().putInt("highscore", iScore).apply();
                    }

                    tvHighscore.setText("Highscore: " + String.valueOf(shHighscore.getInt("highscore", 0)));
                    ObjectAnimator colorAnim = ObjectAnimator.ofInt(tvHighscore, "textColor",
                            Color.TRANSPARENT, Color.WHITE);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.setDuration(500);
                    colorAnim.start();

                    ObjectAnimator colorAnim3 = ObjectAnimator.ofInt(tvTime, "textColor",
                            Color.WHITE, Color.TRANSPARENT);
                    colorAnim3.setEvaluator(new ArgbEvaluator());
                    colorAnim3.setDuration(500);
                    colorAnim3.start();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            bTimeIsRunning = false;

                            ObjectAnimator colorAnim2 = ObjectAnimator.ofInt(tvStart, "textColor",
                                    Color.TRANSPARENT, Color.WHITE);
                            colorAnim2.setEvaluator(new ArgbEvaluator());
                            colorAnim2.setDuration(500);
                            colorAnim2.start();

                            tvTime.setText("10");
                            ObjectAnimator colorAnim1 = ObjectAnimator.ofInt(tvTime, "textColor",
                                    Color.TRANSPARENT, Color.WHITE);
                            colorAnim1.setEvaluator(new ArgbEvaluator());
                            colorAnim1.setDuration(500);
                            colorAnim1.start();
                        }
                    }, 2500);
                }
            }
        }.start();
    }
}
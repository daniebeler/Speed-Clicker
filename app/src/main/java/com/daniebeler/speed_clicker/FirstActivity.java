package com.daniebeler.speed_clicker;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    TextView tvDaniebeler;

    boolean bInBackground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        tvDaniebeler = findViewById(R.id.idDaniebeler);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tvDaniebeler, "textColor",
                Color.TRANSPARENT, Color.WHITE);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(1000);
        colorAnim.start();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        bInBackground = false;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                ObjectAnimator colorAnim = ObjectAnimator.ofInt(tvDaniebeler, "textColor",
                        Color.WHITE, Color.TRANSPARENT);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setDuration(1000);
                colorAnim.start();
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!bInBackground) {
                    startActivity(new Intent(FirstActivity.this, GameActivity.class));
                    //overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                }
            }
        }, 2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bInBackground = true;
    }

    @Override
    public void onBackPressed() {
    }
}
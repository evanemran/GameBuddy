package com.evanemran.gamebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ImageView verifidBannerSplash;
    RelativeLayout leadsBanner;
    TextView versionNoSplash;
    private final long sleep = 3000;
    Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        leadsBanner = findViewById(R.id.leadsBanner);
        verifidBannerSplash = findViewById(R.id.verifidBannerSplash);
        versionNoSplash = findViewById(R.id.versionNoSplash);

        verifidBannerSplash.setAnimation(topAnim);
        leadsBanner.setAnimation(bottomAnim);
        versionNoSplash.setAnimation(bottomAnim);

        startMain();

        try {
            PackageInfo pInfo =
                    getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            versionNoSplash.setText("Version: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startMain() {
        mThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(sleep);
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };

        mThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mThread != null) mThread.interrupt();
    }
}
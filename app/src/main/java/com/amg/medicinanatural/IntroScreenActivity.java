package com.amg.medicinanatural;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes3.dex */
public class IntroScreenActivity extends AppCompatActivity {
    private static final long COUNTER_TIME = 5;
    private static final String LOG_TAG = "SplashActivity";
    private long secondsRemaining;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        createTimer(COUNTER_TIME);
    }

    private void createTimer(long seconds) {
        CountDownTimer countDownTimer = new CountDownTimer(seconds * 1000, 1000L) { // from class: com.amg.medicinanatural2.IntroScreenActivity.1
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                IntroScreenActivity.this.secondsRemaining = (millisUntilFinished / 1000) + 1;
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                IntroScreenActivity.this.secondsRemaining = 0L;
                Application application = IntroScreenActivity.this.getApplication();
                IntroScreenActivity.this.startMainActivity();
            }
        };
        countDownTimer.start();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

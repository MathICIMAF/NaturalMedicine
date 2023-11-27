package com.amg.medicinanatural;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

/* loaded from: classes3.dex */
public class DolenciaDetailActivity extends AppCompatActivity {
    private static DolenciaItem dolencia;
    SharedPreferences.Editor edit;
    private InterstitialAd mInterstitialAd;
    SharedPreferences preference;
    int views;

    public static void launch(Activity activity, DolenciaItem dolencia2) {
        Intent intent = getLaunchIntent(activity);
        activity.startActivityForResult(intent, 1);
        dolencia = dolencia2;
    }

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, DolenciaDetailActivity.class);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolencia_detail);
        SharedPreferences preferences = getPreferences(0);
        this.preference = preferences;
        this.edit = preferences.edit();
        if (dolencia == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        getSupportActionBar().setTitle(dolencia.getNombre());
        int i = this.preference.getInt(DetailActivity.VIEWS, 1);
        this.views = i;
        if (i % 6 == 0) {
            //loadAdInter();
        } else {
            this.edit.putInt(DetailActivity.VIEWS, i + 1);
            this.edit.apply();
        }
        /*AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

         */
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText(dolencia.getDesc());
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(dolencia.getNombre());
        String[] remedios = dolencia.getRemedios();
        LinearLayout layout = (LinearLayout) findViewById(R.id.container);
        for (int i2 = 0; i2 < remedios.length; i2++) {
            View v = View.inflate(this, R.layout.remedio_item, null);
            TextView text = (TextView) v.findViewById(R.id.dolencia);
            text.setText((i2 + 1) + "-" + remedios[i2]);
            layout.addView(v);
        }
    }

    public void loadAdInter() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.inter), adRequest, new InterstitialAdLoadCallback() { // from class: com.amg.medicinanatural2.DolenciaDetailActivity.1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(InterstitialAd interstitialAd) {
                DolenciaDetailActivity.this.mInterstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.amg.medicinanatural2.DolenciaDetailActivity.1.1
                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdDismissedFullScreenContent() {
                        DolenciaDetailActivity.this.mInterstitialAd = null;
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        DolenciaDetailActivity.this.mInterstitialAd = null;
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdShowedFullScreenContent() {
                    }
                });
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                DolenciaDetailActivity.this.mInterstitialAd = null;
            }
        });
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 16908332) {
            finish();
            if (this.views % 6 == 0) {
                //showInterstitial();
                return true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInterstitial() {
        InterstitialAd interstitialAd = this.mInterstitialAd;
        if (interstitialAd != null) {
            interstitialAd.show(this);
            this.edit.putInt(DetailActivity.VIEWS, this.views + 1);
            this.edit.apply();
        }
    }
}

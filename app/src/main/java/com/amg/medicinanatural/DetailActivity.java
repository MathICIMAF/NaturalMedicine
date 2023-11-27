package com.amg.medicinanatural;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

/* loaded from: classes3.dex */
public class DetailActivity extends AppCompatActivity {
    public static final String VIEWS = "VIEWS";
    public static Activity parent;
    private static PlantaItem planta;
    SharedPreferences.Editor edit;
    private InterstitialAd mInterstitialAd;
    SharedPreferences preference;
    ViewFlipper viewFlipper;
    int views;

    public static void launch(Activity activity, PlantaItem planta2) {
        Intent intent = getLaunchIntent(activity);
        activity.startActivityForResult(intent, 1);
        planta = planta2;
        parent = activity;
    }

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SharedPreferences preferences = getPreferences(0);
        this.preference = preferences;
        this.edit = preferences.edit();
        if (planta == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        int i = this.preference.getInt(VIEWS, 1);
        this.views = i;
        if (i % 5 == 0) {
            //loadAdInter();
        } else {
            this.edit.putInt(VIEWS, i + 1);
            this.edit.apply();
        }
        final ImageView favorito = (ImageView) findViewById(R.id.favoritos);
        setBackImage(favorito);
        favorito.setOnClickListener(new View.OnClickListener() { // from class: com.amg.medicinanatural2.DetailActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DetailActivity.planta.setFavorito(!DetailActivity.planta.isFavorito());
                DetailActivity.this.setBackImage(favorito);
                try {
                    MainActivity main = (MainActivity) DetailActivity.parent;
                    if (DetailActivity.planta.isFavorito()) {
                        main.putFavorite(DetailActivity.planta.getPos());
                    } else {
                        main.removeFavorite(DetailActivity.planta.getPos());
                    }
                } catch (Exception e) {
                }
            }
        });
        /*AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        AdView adView1 = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);

         */
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setText(planta.getNombre());
        TextView cientifico = (TextView) findViewById(R.id.cientifico);
        cientifico.setText(planta.getNCientifico());
        TextView modo = (TextView) findViewById(R.id.modo);
        modo.setText(planta.getModo());
        TextView organo = (TextView) findViewById(R.id.organo);
        organo.setText(planta.getOrganoUsado());
        TextView usos = (TextView) findViewById(R.id.uso);
        usos.setText(planta.getUsos());
        final String[] terminos = getResources().getStringArray(R.array.terminos);
        final String[] explicaciones = getResources().getStringArray(R.array.terminos_explicaciones);
        SpannableStringBuilder spanTxt = new SpannableStringBuilder();
        int i2 = 0;
        while (i2 < planta.getCategorias().size()) {
            final int cat = planta.getCategorias().get(i2).intValue() - 1;
            spanTxt.append((CharSequence) terminos[cat]);
            spanTxt.setSpan(new ClickableSpan() { // from class: com.amg.medicinanatural2.DetailActivity.2
                @Override // android.text.style.ClickableSpan
                public void onClick(View widget) {
                    MyDialogFragment dialogFragment = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString(MyDialogFragment.STitle, terminos[cat]);
                    args.putString(MyDialogFragment.SText, explicaciones[cat]);
                    dialogFragment.setArguments(args);
                    dialogFragment.show(DetailActivity.this.getFragmentManager(), "");
                }
            }, spanTxt.length() - terminos[cat].length(), spanTxt.length(), 0);
            planta.getCategorias().size();
            spanTxt.append((CharSequence) ", ");
            i2++;
        }
        TextView term = (TextView) findViewById(R.id.categoria);
        term.setMovementMethod(LinkMovementMethod.getInstance());
        term.setText(spanTxt, TextView.BufferType.SPANNABLE);
        this.viewFlipper = (ViewFlipper) findViewById(R.id.v_flipper);
        String imageName = planta.getImagen();
        int[] images = {getResources().getIdentifier(imageName, "drawable", getPackageName()), getResources().getIdentifier(imageName + "1", "drawable", getPackageName()), getResources().getIdentifier(imageName + "2", "drawable", getPackageName())};
        for (int image : images) {
            flipperImages(image);
        }
    }

    public void loadAdInter() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.inter), adRequest, new InterstitialAdLoadCallback() { // from class: com.amg.medicinanatural2.DetailActivity.3
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(InterstitialAd interstitialAd) {
                DetailActivity.this.mInterstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.amg.medicinanatural2.DetailActivity.3.1
                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdDismissedFullScreenContent() {
                        DetailActivity.this.mInterstitialAd = null;
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        DetailActivity.this.mInterstitialAd = null;
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdShowedFullScreenContent() {
                    }
                });
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                DetailActivity.this.mInterstitialAd = null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackImage(ImageView favorito) {
        if (planta.isFavorito()) {
            favorito.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        } else {
            favorito.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 16908332) {
            finish();
            if (this.views % 5 == 0) {
        //        showInterstitial();
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
            this.edit.putInt(VIEWS, this.views + 1);
            this.edit.apply();
        }
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        this.viewFlipper.addView(imageView);
        this.viewFlipper.setFlipInterval(3500);
        this.viewFlipper.setAutoStart(true);
        this.viewFlipper.setInAnimation(this, R.anim.abc_fade_in);
        this.viewFlipper.setOutAnimation(this, R.anim.abc_fade_out);
    }

    public void backButton(View v) {
        finish();
        if (this.views % 5 == 0) {
          //  showInterstitial();
        }
    }
}

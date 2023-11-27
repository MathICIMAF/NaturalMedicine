package com.amg.medicinanatural;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes3.dex */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String FAV = "FAVORITOS";
    AdView adView;
    AdView adView1;
    PlantasAdapter adapter;
    DolenciasAdapter adapterDolencias;
    PreparadosAdapter adapterPrep;
    ArrayList<DolenciaItem> dolencias;
    public SharedPreferences.Editor edit;
    List<PlantaItem> favoritos;
    LinearLayout inicio;
    boolean isFavoritos;
    private RecyclerView.LayoutManager lManager;
    private RecyclerView list;
    private NativeAd nativeAd;
    private NativeAdView nativeAdView;
    ArrayList<Object> objects = new ArrayList<>();
    ArrayList<PlantaItem> plantas;
    public SharedPreferences preference;
    ArrayList<Preparado> preparados;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() { // from class: com.amg.medicinanatural2.MainActivity.1
            @Override // com.google.android.gms.ads.initialization.OnInitializationCompleteListener
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

         */
        this.inicio = (LinearLayout) findViewById(R.id.inicio);
        this.adView = (AdView) findViewById(R.id.adView);
        this.adView1 = (AdView) findViewById(R.id.adView1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences preferences = getPreferences(0);
        this.preference = preferences;
        this.edit = preferences.edit();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        this.list = recyclerView;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.lManager = linearLayoutManager;
        this.list.setLayoutManager(linearLayoutManager);
        this.plantas = new ArrayList<>();
        this.favoritos = new ArrayList();
        String[] files = getResources().getStringArray(R.array.files);
        String[] caracts = getResources().getStringArray(R.array.caracteristicas);
        String favorites = this.preference.getString(FAV, "");
        for (int i = 0; i < files.length; i++) {
            boolean isFavorite = contains(favorites, i + "");
            PlantaItem planta = new PlantaItem(caracts[i], files[i], i, isFavorite);
            this.plantas.add(planta);
        }
        sortValues(this.plantas);
        //this.adView.loadAd(new AdRequest.Builder().build());
        //this.adView1.loadAd(new AdRequest.Builder().build());
        setAdapterInicio();
        inicializaDolencias();
        inicializaPreparados();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isFavoritos) {
            setAdapterFavoritos();
        }
    }

    private void loadNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.nativo));
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() { // from class: com.amg.medicinanatural2.MainActivity.2
            @Override // com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
            public void onNativeAdLoaded(NativeAd nativeAd) {
                if (MainActivity.this.nativeAd != null) {
                    MainActivity.this.nativeAd.destroy();
                }
                MainActivity.this.nativeAd = nativeAd;
                MainActivity mainActivity = MainActivity.this;
                mainActivity.nativeAdView = (NativeAdView) mainActivity.getLayoutInflater().inflate(R.layout.ad_unified, (ViewGroup) null);
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.populateNativeAdView(nativeAd, mainActivity2.nativeAdView);
                int rand = new Random().nextInt(MainActivity.this.objects.size());
                MainActivity.this.objects.add(rand, MainActivity.this.nativeAdView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() { // from class: com.amg.medicinanatural2.MainActivity.3
            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    void inicializaPreparados() {
        this.preparados = new ArrayList<>();
        String[] preps = getResources().getStringArray(R.array.preparaciones_frecuentes);
        for (String str : preps) {
            Preparado preparado = new Preparado(str);
            this.preparados.add(preparado);
        }
        this.adapterPrep = new PreparadosAdapter(this.preparados);
    }

    void inicializaDolencias() {
        this.dolencias = new ArrayList<>();
        String[] dols = getResources().getStringArray(R.array.dolencias);
        for (String str : dols) {
            DolenciaItem dolencia = new DolenciaItem(str);
            this.dolencias.add(dolencia);
        }
        sortValuesDolencias();
        this.adapterDolencias = new DolenciasAdapter(this, this.dolencias);
    }

    void setAdapterDolencias() {
        getSupportActionBar().setTitle(getString(R.string.dolen));
        this.inicio.setBackgroundResource(R.drawable.white_back);
        this.list.setAdapter(this.adapterDolencias);
    }

    void setAdapterInicio() {
        //loadNativeAds();
        this.objects = new ArrayList<>(this.plantas);
        this.adapter = new PlantasAdapter(this, this.objects);
        this.isFavoritos = false;
        getSupportActionBar().setTitle(getString(R.string.app_name));
        this.list.setAdapter(this.adapter);
        this.inicio.setBackgroundResource(R.drawable.aa_degradado);
    }

    void setAdapterPreparados() {
        getSupportActionBar().setTitle(getString(R.string.prepa));
        this.inicio.setBackgroundResource(R.drawable.white_back);
        this.list.setAdapter(this.adapterPrep);
    }

    void setAdapterFavoritos() {
        this.favoritos = takeFavoritos();
        this.objects = new ArrayList<>(this.favoritos);
        //loadNativeAds();
        this.adapter = new PlantasAdapter(this, this.objects);
        this.isFavoritos = true;
        getSupportActionBar().setTitle(getString(R.string.favs));
        this.list.setAdapter(this.adapter);
        this.inicio.setBackgroundResource(R.drawable.aa_degradado);
    }

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getMediaContent().getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() { // from class: com.amg.medicinanatural2.MainActivity.4
                @Override // com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: com.amg.medicinanatural2.MainActivity.5
            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) {
                try {
                    ((PlantasAdapter) MainActivity.this.list.getAdapter()).getFilter().filter(newText);
                } catch (Exception e) {
                }
                try {
                    ((DolenciasAdapter) MainActivity.this.list.getAdapter()).getFilter().filter(newText);
                } catch (Exception e2) {
                }
                try {
                    ((PreparadosAdapter) MainActivity.this.list.getAdapter()).getFilter().filter(newText);
                    return false;
                } catch (Exception e3) {
                    return false;
                }
            }
        });
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id=" + getPackageName();
            sharingIntent.putExtra("android.intent.extra.SUBJECT", R.string.share);
            sharingIntent.putExtra("android.intent.extra.TEXT", shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            return true;
        }
        if (id == R.id.action_rateus) {
            launchMarket();
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchMarket() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_advertencia) {
            MyDialogFragment dialogFragment = new MyDialogFragment();
            Bundle args = new Bundle();
            args.putString(MyDialogFragment.STitle, getString(R.string.advert));
            args.putString(MyDialogFragment.SText, getString(R.string.advertencia));
            dialogFragment.setArguments(args);
            dialogFragment.show(getFragmentManager(), "");
        } else if (id == R.id.action_inicio) {
            setAdapterInicio();
            this.inicio.setVisibility(View.VISIBLE);
        } else if (id == R.id.action_dolencias) {
            setAdapterDolencias();
        } else if (id == R.id.action_preparados) {
            this.isFavoritos = false;
            setAdapterPreparados();
        } else if (id == R.id.action_favoritos) {
            setAdapterFavoritos();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void sortValues(List<PlantaItem> plantas) {
        int N = plantas.size();
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (plantas.get(i).getNombre().compareTo(plantas.get(j).getNombre()) > 0) {
                    PlantaItem aux = plantas.get(i);
                    plantas.set(i, plantas.get(j));
                    plantas.set(j, aux);
                }
            }
        }
    }

    void sortValuesDolencias() {
        int N = this.dolencias.size();
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (this.dolencias.get(i).getNombre().compareTo(this.dolencias.get(j).getNombre()) > 0) {
                    DolenciaItem aux = this.dolencias.get(i);
                    ArrayList<DolenciaItem> arrayList = this.dolencias;
                    arrayList.set(i, arrayList.get(j));
                    this.dolencias.set(j, aux);
                }
            }
        }
    }

    public boolean putFavorite(int planta) {
        String favorites = this.preference.getString(FAV, "");
        String planta_index = String.valueOf(planta);
        if (contains(favorites, planta_index)) {
            this.edit.putString(FAV, remove(favorites, planta_index));
            this.edit.commit();
            return false;
        }
        this.edit.putString(FAV, favorites + planta_index + ";");
        this.edit.commit();
        return true;
    }

    private boolean contains(String favorites, String item) {
        String[] elems;
        try {
            elems = favorites.split(";");
        } catch (Exception e) {
            elems = new String[0];
        }
        for (String str : elems) {
            if (str.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void removeFavorite(int index) {
        String favorites = this.preference.getString(FAV, "");
        String new_string = remove(favorites, "" + index);
        this.edit.putString(FAV, new_string);
        this.edit.commit();
    }

    private String remove(String favorites, String item) {
        String[] elems = favorites.split(";");
        String result = "";
        for (int i = 0; i < elems.length; i++) {
            if (!elems[i].equals(item)) {
                result = result + elems[i] + ";";
            }
        }
        return result;
    }

    List<PlantaItem> takeFavoritos() {
        String[] favs;
        String favorites = this.preference.getString(FAV, "");
        try {
            favs = favorites.split(";");
        } catch (Exception e) {
            favs = new String[0];
        }
        ArrayList<PlantaItem> res = new ArrayList<>();
        for (String str : favs) {
            try {
                int index = Integer.parseInt(str);
                for (int j = 0; j < this.plantas.size(); j++) {
                    if (this.plantas.get(j).getPos() == index) {
                        res.add(this.plantas.get(j));
                    }
                }
            } catch (Exception e2) {
            }
        }
        sortValues(res);
        return res;
    }
}

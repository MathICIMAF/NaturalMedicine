package com.amg.medicinanatural;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.nativead.NativeAdView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PlantasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private final Context context;
    private List<Object> fullList;
    private List<Object> items;
    private Filter itemsFilter = new Filter() { // from class: com.amg.medicinanatural2.PlantasAdapter.2
        @Override // android.widget.Filter
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Object> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(PlantasAdapter.this.fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Object item : PlantasAdapter.this.fullList) {
                    if (item instanceof PlantaItem) {
                        PlantaItem plantaItem = (PlantaItem) item;
                        if (plantaItem.getNombre().toLowerCase().contains(filterPattern) || plantaItem.getNCientifico().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence constraint, FilterResults results) {
            PlantasAdapter.this.items.clear();
            PlantasAdapter.this.items.addAll((List) results.values);
            PlantasAdapter.this.notifyDataSetChanged();
        }
    };

    public PlantasAdapter(Context context, List<Object> plantas) {
        this.items = plantas;
        this.context = context;
        ArrayList arrayList = new ArrayList();
        this.fullList = arrayList;
        arrayList.addAll(plantas);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View nativeAdView = LayoutInflater.from(this.context).inflate(R.layout.native_ad_container, parent, false);
                return new AdViewHolder(nativeAdView);
            default:
                View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.planta_item, parent, false);
                return new PlantasViewHolder(itemLayoutView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 1:
                AdViewHolder bannerHolder = (AdViewHolder) holder;
                NativeAdView adView = (NativeAdView) this.items.get(position);
                ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                adCardView.addView(adView);
                return;
            default:
                final PlantaItem planta = (PlantaItem) this.items.get(position);
                PlantasViewHolder pvHolder = (PlantasViewHolder) holder;
                pvHolder.name1.setText(planta.getNombre());
                pvHolder.cientif.setText(planta.getNCientifico());
                int id = 0;
                if (planta.getImagen().length() != 0) {
                    id = this.context.getResources().getIdentifier(planta.getImagen(), "drawable", this.context.getPackageName());
                }
                pvHolder.image1.setBackgroundResource(id);
                pvHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.amg.medicinanatural2.PlantasAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        DetailActivity.launch((MainActivity) PlantasAdapter.this.context, planta);
                    }
                });
                return;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        Object recyclerViewItem = this.items.get(position);
        if (recyclerViewItem instanceof NativeAdView) {
            return 1;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        return this.itemsFilter;
    }

    /* loaded from: classes3.dex */
    public class PlantasViewHolder extends RecyclerView.ViewHolder {
        public TextView cientif;
        public ImageView image1;
        LinearLayout layout;
        public TextView name1;

        public PlantasViewHolder(View itemView) {
            super(itemView);
            this.name1 = (TextView) itemView.findViewById(R.id.name);
            this.cientif = (TextView) itemView.findViewById(R.id.cientifico);
            this.image1 = (ImageView) itemView.findViewById(R.id.image);
            this.layout = (LinearLayout) itemView.findViewById(R.id.layout1);
        }
    }

    /* loaded from: classes3.dex */
    public class AdViewHolder extends RecyclerView.ViewHolder {
        AdViewHolder(View view) {
            super(view);
        }
    }
}

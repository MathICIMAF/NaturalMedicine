package com.amg.medicinanatural;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PreparadosAdapter extends RecyclerView.Adapter<PreparadosAdapter.PreparadoViewHolder> implements Filterable {
    List<Preparado> fullList;
    private Filter itemsFilter = new Filter() { // from class: com.amg.medicinanatural2.PreparadosAdapter.1
        @Override // android.widget.Filter
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Preparado> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(PreparadosAdapter.this.fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Preparado item : PreparadosAdapter.this.fullList) {
                    if (item.getNombre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence constraint, FilterResults results) {
            PreparadosAdapter.this.preparados.clear();
            PreparadosAdapter.this.preparados.addAll((List) results.values);
            PreparadosAdapter.this.notifyDataSetChanged();
        }
    };
    List<Preparado> preparados;

    public PreparadosAdapter(List<Preparado> preparados) {
        this.preparados = preparados;
        ArrayList arrayList = new ArrayList();
        this.fullList = arrayList;
        arrayList.addAll(preparados);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PreparadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preparado_item, parent, false);
        return new PreparadoViewHolder(v);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PreparadoViewHolder holder, int position) {
        Preparado preparado = this.preparados.get(position);
        holder.nombre.setText(preparado.getNombre());
        holder.desc.setText(preparado.getDesc());
        AdRequest adRequest = new AdRequest.Builder().build();
        holder.adView.loadAd(adRequest);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        holder.adView1.loadAd(adRequest1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.preparados.size();
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        return this.itemsFilter;
    }

    /* loaded from: classes3.dex */
    public class PreparadoViewHolder extends RecyclerView.ViewHolder {
        public AdView adView;
        public AdView adView1;
        public TextView desc;
        public TextView nombre;

        public PreparadoViewHolder(View itemView) {
            super(itemView);
            this.nombre = (TextView) itemView.findViewById(R.id.nombre);
            this.desc = (TextView) itemView.findViewById(R.id.desc);
            this.adView = (AdView) itemView.findViewById(R.id.adView);
            this.adView1 = (AdView) itemView.findViewById(R.id.adView1);
        }
    }
}

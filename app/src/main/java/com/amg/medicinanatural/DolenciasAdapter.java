package com.amg.medicinanatural;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DolenciasAdapter extends RecyclerView.Adapter<DolenciasAdapter.DolenciaViewHolder> implements Filterable, ItemClickListener {
    private final Context context;
    List<DolenciaItem> dolencias;
    private List<DolenciaItem> fullList;
    private Filter itemsFilter = new Filter() { // from class: com.amg.medicinanatural2.DolenciasAdapter.2
        @Override // android.widget.Filter
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DolenciaItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(DolenciasAdapter.this.fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DolenciaItem item : DolenciasAdapter.this.fullList) {
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
            DolenciasAdapter.this.dolencias.clear();
            DolenciasAdapter.this.dolencias.addAll((List) results.values);
            DolenciasAdapter.this.notifyDataSetChanged();
        }
    };

    public DolenciasAdapter(Context context, List<DolenciaItem> dolencias) {
        this.context = context;
        this.dolencias = dolencias;
        ArrayList arrayList = new ArrayList();
        this.fullList = arrayList;
        arrayList.addAll(dolencias);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DolenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dolencia_item, parent, false);
        return new DolenciaViewHolder(v, this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(DolenciaViewHolder holder, final int position) {
        holder.nombre.setText(this.dolencias.get(position).getNombre());
        holder.layout1.setOnClickListener(new View.OnClickListener() { // from class: com.amg.medicinanatural2.DolenciasAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DolenciaDetailActivity.launch((Activity) DolenciasAdapter.this.context, DolenciasAdapter.this.dolencias.get(position));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dolencias.size();
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        return this.itemsFilter;
    }

    @Override // com.amg.medicinanatural2.ItemClickListener
    public void onItemClick(View view, int position) {
        DolenciaDetailActivity.launch((Activity) this.context, this.dolencias.get(position));
    }

    /* loaded from: classes3.dex */
    public class DolenciaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout layout1;
        public ItemClickListener listener;
        public TextView nombre;

        public DolenciaViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            this.nombre = (TextView) itemView.findViewById(R.id.dolencia);
            this.layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            this.listener.onItemClick(v, getAdapterPosition());
        }
    }
}

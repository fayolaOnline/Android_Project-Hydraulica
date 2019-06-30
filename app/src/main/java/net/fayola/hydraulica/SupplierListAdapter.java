package net.fayola.hydraulica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.SupplierViewHolder> {

    private final LayoutInflater mInflatter;
    private List<Supplier> mSuppliers;

    SupplierListAdapter(Context context) {
        mInflatter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflatter.inflate(R.layout.recyclerview_item, parent, false);
        return new SupplierViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {
        if(mSuppliers != null) {
            Supplier current = mSuppliers.get(position);
            holder.supplierItemView.setText(current.getName());
        } else {
            // data not ready yet
            holder.supplierItemView.setText("No Supplier");
        }
    }

    void setSuppliers(List<Supplier> suppliers) {
        mSuppliers = suppliers;
        notifyDataSetChanged();
    }

    //gets count, initially it's null if not initialized


    @Override
    public int getItemCount() {
        if(mSuppliers != null)
            return mSuppliers.size();
        return 0;
    }

    class SupplierViewHolder extends RecyclerView.ViewHolder {
        private final TextView supplierItemView;

        private SupplierViewHolder(View itemView){
            super(itemView);
            supplierItemView = itemView.findViewById(R.id.supplier_textview);
        }

    }

}

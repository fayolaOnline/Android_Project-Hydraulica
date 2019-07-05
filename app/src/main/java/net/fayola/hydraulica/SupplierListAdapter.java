package net.fayola.hydraulica;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.SupplierViewHolder> {

    private static String TAG = MainActivity.TAG+"::SupplierListAdapter";
    private final LayoutInflater mInflater;
    private List<Supplier> mSuppliers;
    private Context mCtx;



    SupplierListAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        mCtx = context;
    }

    public  Supplier getSupplierAtPosition(int position){
        return mSuppliers.get(position);
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SupplierViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final SupplierViewHolder holder, final int position) {
        if(mSuppliers != null) {
            Supplier current = mSuppliers.get(position);
            holder.supplierItemView.setText(current.getName());
            holder.supplierMore.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"supplier clicked: " + position);
                    //show pop up menu
                    PopupMenu popup = new PopupMenu(mCtx,holder.supplierMore);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.supplier_context);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Supplier supplier = getSupplierAtPosition(position);
                            switch(item.getItemId()) {
                                case R.id.item_edit:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                    builder.setTitle("Change Supplier");

                                    // Set up input
                                    final EditText input = new EditText(mCtx);
                                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                                    builder.setView(input);

                                    // set buttons
                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //put input text in database
                                            if(!input.getText().toString().isEmpty())
                                                MainActivity.mainSVM.update(new Supplier(input.getText().toString()));
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.show();



                                    break;
                                case R.id.item_delete:
                                    MainActivity.mainSVM.delete(supplier);
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        } else {
            // data not ready yet
            holder.supplierItemView.setText(R.string.no_suppliers);
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
        private final TextView supplierMore;

        private SupplierViewHolder(View itemView){
            super(itemView);
            supplierItemView = itemView.findViewById(R.id.supplier_textview);
            supplierMore = itemView.findViewById(R.id.supply_more);

        }

    }

}

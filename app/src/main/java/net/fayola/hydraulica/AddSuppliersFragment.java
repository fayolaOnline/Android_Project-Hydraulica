package net.fayola.hydraulica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSuppliersFragment} interface
 * to handle interaction events.
 * Use the {@link AddSuppliersFragment} factory method to
 * create an instance of this fragment.
 */
public class AddSuppliersFragment extends Fragment {
    public static String TAG = MainActivity.TAG + "::AddSuppliersFragment";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLLM;
    public AddSuppliersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Add Supplier Fragment Created.");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_suppliers, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        if(mRecyclerView!= null) {
            mRecyclerView.setAdapter(MainActivity.mainSupplierAdapter);
            mRecyclerView.setLayoutManager(mLLM);
            mRecyclerView.setLongClickable(true);

            mRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //set context menu

                    return false;
                }
            });

            Log.d(TAG,"mRecyclerView is NOT NULL.");

        } else {
            Log.d(TAG,"mRecyclerView is null.");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mLLM = new LinearLayoutManager(context);



    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showSuppliers (LiveData<List<Supplier>> suppliers) {
        if(suppliers.getValue().isEmpty()) {
            Log.d(TAG,"There are no suppliers.");
        } else {
            Log.d(TAG,"There are some suppliers.");
        }
    }


}

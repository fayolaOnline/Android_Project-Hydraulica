package net.fayola.hydraulica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalcFragment.OnCalcFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcFragment extends Fragment {
    public static String TAG = MainActivity.TAG+"::CalcFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_AIRP = "airP";
    private static final String ARG_RADIUS = "radius";

    private String mAirP;
    private String mRadius;

    private OnCalcFragmentInteractionListener mListener;

   //UI Components
    private EditText mAirPEdit;
    private EditText mRadiusEdit;
    private Button mCalcForceBtn;


    public CalcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param airP Parameter 1.
     * @param radius Parameter 2.
     * @return A new instance of fragment CalcFragment.
     */
    public static CalcFragment newInstance(String airP, String radius) {
        CalcFragment fragment = new CalcFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AIRP, airP);
        args.putString(ARG_RADIUS, radius);
        fragment.setArguments(args);
        Log.d(TAG,"Fragment Created.");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAirP = getArguments().getString(ARG_AIRP);
            mRadius = getArguments().getString(ARG_RADIUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAirPEdit = view.findViewById(R.id.entered_airp);
        mRadiusEdit = view.findViewById(R.id.entered_radius);
        if (getArguments() != null) {
            mAirPEdit.setText(mAirP);
            mRadiusEdit.setText(mRadius);
        }
        mCalcForceBtn = view.findViewById(R.id.calc_force);
        mCalcForceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double p, r;
                p = Double.parseDouble(mAirPEdit.getText().toString());
                r = Double.parseDouble(mRadiusEdit.getText().toString());
                if (p < 20){
                    mAirPEdit.setText("20.0");
                    Toast.makeText(getActivity(),"Air Pressure must be between 10 and 300",Toast.LENGTH_LONG).show();
                } else if (p > 300){
                    mAirPEdit.setText("300.0");
                    Toast.makeText(getActivity(),"Air Pressure must be between 10 and 300",Toast.LENGTH_LONG).show();
                } else if (r < 0.025) {
                    mRadiusEdit.setText("1.0");
                    Toast.makeText(getActivity(),"Radius must be between 0.025 and 10",Toast.LENGTH_LONG).show();
                } else if (r > 10) {
                    mRadiusEdit.setText("1.0");
                    Toast.makeText(getActivity(),"Radius must be between 0.025 and 10",Toast.LENGTH_LONG).show();
                } else {
                    double result = CylindricalForce.check(Double.parseDouble(mAirPEdit.getText().toString()), Double.parseDouble(mRadiusEdit.getText().toString()));
                    if (mListener != null) {
                        mListener.onCalcPerformed(result);
                    }
                }
            }
        });

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalcFragmentInteractionListener) {
            mListener = (OnCalcFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCalcFragmentInteractionListener {
        void onCalcPerformed(double result);
    }
}

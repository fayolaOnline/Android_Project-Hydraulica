package net.fayola.hydraulica;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link CalcResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcResultFragment extends DialogFragment {
    public static String TAG = MainActivity.TAG + "::CalcResultFragment";

    private TextView mTextView;
    private ImageView mImgView;
    private Button mResultBtn;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CALC_RESULT = "calcResult";

    private String mCalcResult;

    public CalcResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param calcResult Parameter 1.
     * @return A new instance of fragment CalcResultFragment.
     */
    public static CalcResultFragment newInstance(String calcResult) {
        CalcResultFragment fragment = new CalcResultFragment();
        Bundle args = new Bundle();
        args.putString(CALC_RESULT, calcResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalcResult = getArguments().getString(CALC_RESULT);
        }
        setStyle(DialogFragment.STYLE_NORMAL,R.style.Theme_AppCompat_Dialog);
        //setStyle(DialogFragment.STYLE_NORMAL, );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_calc_result, container, false);
        Dialog dialog = getDialog();
        dialog.setTitle("Results");
        // Inflate the layout for this fragment
        return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mTextView = view.findViewById(R.id.result_message);
        mTextView.setText(getString(R.string.running));
        mImgView = view.findViewById(R.id.animation_view);
        mImgView.setVisibility(ImageView.VISIBLE);
        mImgView.setBackgroundResource(R.drawable.running_piston);
        mResultBtn = view.findViewById(R.id.result_btn);
        mResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Closing CalcResultFragment - DialogFragment");
                dismiss();
                return;
            }
        });


        AnimationDrawable frameAnimation = (AnimationDrawable) mImgView.getBackground();
        frameAnimation.setCallback(new AnimationDrawableCallback(frameAnimation,mImgView) {
            @Override
            public void onAnimationCompleted() {
                //set text to result
                doneText();
                //enable closing button
                mResultBtn.setEnabled(true);
                Log.i(TAG,"animation completed");

            }

            @Override
            public void onAnimationAdvanced(int currentFrame, int totalFrames) {
                //change text based on frame maybe
                Log.i(TAG,"animation on going: frame "+currentFrame);
                if(currentFrame == 0 || currentFrame == 5 || currentFrame ==10){
                    mTextView.setText(mTextView.getText().toString()+".");
                }

            }
        });
        frameAnimation.start();
    }



    private void doneText(){
        mTextView.setText("The result is " + mCalcResult);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

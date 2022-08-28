package com.ojsusuandloans;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Loan0Fragment extends Fragment {

    Button nextbtn;
    EditText amountinput;
    TextView interestvalue,total;


    private Loan1Fragment.OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (Loan1Fragment.OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }


    public static Fragment newInstance() {
        return new Loan0Fragment();
    }
    public Loan0Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.loan0, container, false);


        nextbtn = view.findViewById(R.id.btn_next);
        amountinput = view.findViewById(R.id.amountinput);
        interestvalue = view.findViewById(R.id.interestvalue);
        total = view.findViewById(R.id.total);



        amountinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    if (Float.parseFloat(charSequence.toString())>=50){
                        String ans = String.valueOf(0.18 * Float.parseFloat(charSequence.toString()));
                        interestvalue.setText(ans);

                        total.setText(String.valueOf(Float.parseFloat(ans)+Float.parseFloat(charSequence.toString())));
                    }}


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountinput.getText().toString().isEmpty()){
                    amountinput.setError("Amount cannot be empty");
                }
                else if (Float.parseFloat(amountinput.getText().toString())<50 |
                        Float.parseFloat(amountinput.getText().toString())>1000){
                    amountinput.setError("Amount must fall with proposed range");
                }else {
                mOnButtonClickListener.onButtonClicked(v);}

            }
        });
        return view;
    }
}
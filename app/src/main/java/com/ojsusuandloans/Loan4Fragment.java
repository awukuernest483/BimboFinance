package com.ojsusuandloans;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Loan4Fragment extends Fragment {

    Button nextbtn,prevbtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText ref1nam, ref1num, ref1rel, ref2nam, ref2num, ref2rel;

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
        return new Loan4Fragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.loan4, container, false);

        nextbtn = view.findViewById(R.id.btn_next);
        prevbtn = view.findViewById(R.id.btn_prev);

        ref1nam = view.findViewById(R.id.ref1name);
        ref1num = view.findViewById(R.id.ref1number);
        ref1rel = view.findViewById(R.id.ref1relationship);
        ref2nam = view.findViewById(R.id.ref2name);
        ref2num = view.findViewById(R.id.ref2number);
        ref2rel = view.findViewById(R.id.ref2relationship);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Loans");





        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ref1nam.getText().toString().isEmpty()|
                ref1num.getText().toString().isEmpty()|
                ref1rel.getText().toString().isEmpty()|
                ref2nam.getText().toString().isEmpty()|
                ref2num.getText().toString().isEmpty()|
                ref2rel.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    nextbtn.setBackgroundColor(Color.parseColor("#3B8FF0"));
                mOnButtonClickListener.onButtonClicked(v);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref1name")
                                    .setValue(ref1nam.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref1number")
                                    .setValue(ref1num.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref1relationship")
                                    .setValue(ref1num.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref2name")
                                    .setValue(ref2nam.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref2number")
                                    .setValue(ref2num.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("ref2relationship")
                                    .setValue(ref2rel.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });

        return view;
    }
}
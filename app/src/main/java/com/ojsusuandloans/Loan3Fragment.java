package com.ojsusuandloans;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Loan3Fragment extends Fragment{

    Button nextbtn,prevbtn;

    private Loan1Fragment.OnButtonClickListener mOnButtonClickListener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

    String[] country = { "Ghana Card", "Passport", "Voters ID"};


    public static Fragment newInstance() {
        return new Loan3Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.loan3, container, false);

        nextbtn = view.findViewById(R.id.btn_next);
        prevbtn = view.findViewById(R.id.btn_prev);

        EditText idnumber = view.findViewById(R.id.id_number);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Loans");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, country);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = view.findViewById(R.id.idspinner);
        sItems.setAdapter(adapter);
        sItems.setSelection(1);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idnumber.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please enter ID number", Toast.LENGTH_SHORT).show();
                }else{
                    nextbtn.setBackgroundColor(Color.parseColor("#3B8FF0"));
                    mOnButtonClickListener.onButtonClicked(v);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("idnumber")
                                    .setValue(idnumber.getText().toString());
                            databaseReference.child("unapproved").child(requireActivity().getIntent().getStringExtra("username")).child("idtype")
                                    .setValue(sItems.getSelectedItem().toString());
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
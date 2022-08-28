package com.ojsusuandloans.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ojsusuandloans.Loans;
import com.ojsusuandloans.loanshistory;
import com.ojsusuandloans.R;
import com.ojsusuandloans.Users;

public class LoansActivity extends AppCompatActivity {

    private RecyclerView recyclerView, allhistoryrecyclerview;
    LoanAdapter adapter;
    LoanshistoryAdapter adapterr;// Create Object of the Adapter class
    Query mbase,mbase1;
    FrameLayout back;
    CardView payloanbutton;
    TextView nohistorytext,allnohistorytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        allnohistorytext = findViewById(R.id.allnohistorytext);
        allhistoryrecyclerview = findViewById(R.id.allloanhistoryrecycler);

        back= findViewById(R.id.backbutton);
        payloanbutton = findViewById(R.id.payloanbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        payloanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoansActivity.this, PayLoanActivity.class);


                startActivity(intent);
            }
        });

        mbase = FirebaseDatabase.getInstance().getReference("/Loans/unapproved").orderByKey().equalTo(getIntent().getStringExtra("username"));
        mbase1 = FirebaseDatabase.getInstance().getReference("/Users").child(getIntent().getStringExtra("username")).child("loanshistory");

        recyclerView = findViewById(R.id.loanhistoryrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allhistoryrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Loans> options
                = new FirebaseRecyclerOptions.Builder<Loans>()
                .setQuery(mbase, Loans.class)
                .build();

        FirebaseRecyclerOptions<loanshistory> optionss
                = new FirebaseRecyclerOptions.Builder<loanshistory>()
                .setQuery(mbase1, loanshistory.class)
                .build();

        adapterr = new LoanshistoryAdapter(optionss);

        allhistoryrecyclerview.setAdapter(adapterr);
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new LoanAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

        nohistorytext = findViewById(R.id.nohistorytext);

        if (adapter.getItemCount() == 0){

            nohistorytext.setVisibility(View.VISIBLE);

        }

        if (adapterr.getItemCount() == 0){

            allnohistorytext.setVisibility(View.VISIBLE);

        }
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
        adapterr.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
        adapterr.stopListening();
    }
}

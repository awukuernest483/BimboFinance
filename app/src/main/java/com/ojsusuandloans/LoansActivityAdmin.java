package com.ojsusuandloans;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class LoansActivityAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    LoansAdapterAdmin adapter; // Create Object of the Adapter class
    Query mbase;
    EditText searchET;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_admin);











        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchET = findViewById(R.id.search_ET);


        mbase = FirebaseDatabase.getInstance().getReference("/Loans/unapproved");






        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Loans> options
                = new FirebaseRecyclerOptions.Builder<Loans>()
                .setQuery(mbase, Loans.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new LoansAdapterAdmin(options);
        // Connecting Adapter class with the Recycler view*/






        recyclerView.setAdapter(adapter);




    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



}






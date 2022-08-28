package com.ojsusuandloans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ojsusuandloans.activity.AlltransactionsAdapter;
import com.ojsusuandloans.activity.SusuhistoryAdapter;

public class Alltransactionsadmin extends AppCompatActivity {

    Query mbase;
    AlltransactionsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alltransactionsadmin);

        mbase = FirebaseDatabase.getInstance().getReference("/Alltransactions");
        recyclerView = findViewById(R.id.alltransactionsrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Alltransactions> options
                = new FirebaseRecyclerOptions.Builder<Alltransactions>()
                .setQuery(mbase, Alltransactions.class)
                .build();

        adapter = new AlltransactionsAdapter(options);
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
package com.ojsusuandloans;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class DepositActivityAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    DepositsAdapterAdmin adapter; // Create Object of the Adapter class
    Query mbase;
    EditText searchET;
    Button searchbtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawadmin);

        searchbtn = findViewById(R.id.searchbtn);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchET = findViewById(R.id.search_ET);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchET.getText().toString();
                mbase = FirebaseDatabase.getInstance().getReference("/Users").orderByChild("email").startAt(search).endAt(search + "\uf8ff");

                Toast.makeText(getApplicationContext(),search,Toast.LENGTH_SHORT).show();
                FirebaseRecyclerOptions<Users> options
                        = new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(mbase, Users.class)
                        .build();
                // Connecting object of required Adapter class to
                // the Adapter class itself
                adapter = new DepositsAdapterAdmin(options);
                // Connecting Adapter class with the Recycler view*/






                recyclerView.setAdapter(adapter);
                adapter.startListening();

            }
        });















        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data








    }









}






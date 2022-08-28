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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ojsusuandloans.DepositActivity;
import com.ojsusuandloans.Loans;
import com.ojsusuandloans.R;
import com.ojsusuandloans.WithdrawActivity;
import com.ojsusuandloans.susuhistory;

public class SusuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    FrameLayout back;
    CardView deposit,withdraw;
    Query mbase;

    SusuhistoryAdapter adapter;
    TextView nohistorytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_susu);

        mbase = FirebaseDatabase.getInstance().getReference("/Users").child(getIntent().getStringExtra("username")).child("susuhistory");
        recyclerView = findViewById(R.id.susuhistoryrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nohistorytext = findViewById(R.id.loanhistorytext);



        FirebaseRecyclerOptions<susuhistory> options
                = new FirebaseRecyclerOptions.Builder<susuhistory>()
                .setQuery(mbase, susuhistory.class)
                .build();

        adapter = new SusuhistoryAdapter(options);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0){

            nohistorytext.setVisibility(View.VISIBLE);

        }

        back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        deposit = findViewById(R.id.depositbutton);
        withdraw = findViewById(R.id.withdrawbutton);


        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent1 = new Intent(SusuActivity.this, DepositActivity.class);
                    intent1.putExtra("username",getIntent().getStringExtra("username"));
                    intent1.putExtra("totalbalance",getIntent().getStringExtra("totalbalance"));
                    startActivity(intent1);


            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SusuActivity.this,WithdrawActivity.class);
                intent.putExtra("totalbalance", getIntent().getStringExtra("totalbalance"));
                intent.putExtra("PinVerified","false");
                intent.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(intent);

            }
        });
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
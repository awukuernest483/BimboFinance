package com.ojsusuandloans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Withdrawprogress extends AppCompatActivity {

    Button done;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Withdrawals withdrawals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawprogress);

        done = findViewById(R.id.btn_done);
        withdrawals = new Withdrawals();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Withdrawals");

        String username = getIntent().getStringExtra("username");
        String amount = getIntent().getStringExtra("amount");
        String accountnumber = getIntent().getStringExtra("accountnumber");
        String accountname = getIntent().getStringExtra("accountname");
        String bank = getIntent().getStringExtra("bank");
        String color = getIntent().getStringExtra("color");


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDatatoFirebase(username, amount, accountnumber,accountname,bank,color);

            }
        });





    }

    private void addDatatoFirebase(String username, String amount, String accountnumber, String accountname, String bank, String color) {


        Map<String,Object> hashMap1 = new HashMap<String, Object>();
        hashMap1.put("username", username);
        hashMap1.put("amount", amount);
        hashMap1.put("accountnumber", accountnumber);
        hashMap1.put("accountname", accountname);
        hashMap1.put("bank", bank);
        hashMap1.put("timestamp", ServerValue.TIMESTAMP);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child(username).setValue(withdrawals);
                databaseReference.child(username).setValue(hashMap1);
                Toast.makeText(Withdrawprogress.this, "Completed successfully", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Withdrawprogress.this, "An error occurred please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Tap Done to Exit",Toast.LENGTH_SHORT).show();
    }
}
package com.ojsusuandloans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class WithdrawExpanded extends AppCompatActivity {

    TextView depositdetails;
    EditText amountwithdraw;
    Button payamount;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1,databaseReference3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_expanded);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(getIntent().getStringExtra("username"));
        databaseReference1 = firebaseDatabase.getReference("Withdrawals").child(getIntent().getStringExtra("username"));

        databaseReference3 = firebaseDatabase.getReference("Alltransactions");



        depositdetails = findViewById(R.id.Withdrawdetails);
        amountwithdraw = findViewById(R.id.amountwithdraw);
        payamount = findViewById(R.id.payamountbutton);


        String details = "Username: "+getIntent().getStringExtra("username")+"\n"+
                "accountname: "+getIntent().getStringExtra("accountname")+"\n"+
                "accountnumber: "+getIntent().getStringExtra("accountnumber")+"\n"+
                "withdrawal amount: "+getIntent().getStringExtra("amount");


        depositdetails.setText(details);


        payamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountwithdraw.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cant be empty", Toast.LENGTH_SHORT).show();
                }else{

                    payamount.setClickable(false);


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Float ans = Float.parseFloat(snapshot.child("susu").getValue().toString())-
                                    Float.parseFloat(amountwithdraw.getText().toString());


                            databaseReference.child("susu").setValue(String.valueOf(ans)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Money subtracted", Toast.LENGTH_SHORT).show();
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("status", "withdrawal");
                                    hashMap.put("amount", amountwithdraw.getText().toString());
                                    hashMap.put("username", getIntent().getStringExtra("username"));
                                    hashMap.put("timestamp", ServerValue.TIMESTAMP);




                                    databaseReference.child("susuhistory").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            databaseReference1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(),"Value removed from Withdrawals", Toast.LENGTH_SHORT).show();
                                                    Map<String,Object> hashMap1 = new HashMap<String, Object>();
                                                    hashMap1.put("status", "withdrawal");
                                                    hashMap1.put("amount", amountwithdraw.getText().toString());
                                                    hashMap1.put("timestamp", ServerValue.TIMESTAMP);
                                                    hashMap1.put("username", getIntent().getStringExtra("username"));




                                                    databaseReference3.push().setValue(hashMap1);
                                                    finish();
                                                }
                                            });

                                        }
                                    });

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });












                }
            }
        });




    }
}
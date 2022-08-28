package com.ojsusuandloans;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DepositExpanded extends AppCompatActivity {

    TextView depositdetails;
    EditText susudeposit,loandeposit;
    Button paysusu,payloan;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_expanded);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(getIntent().getStringExtra("username"));


        databaseReference3 = firebaseDatabase.getReference("Alltransactions");




        depositdetails = findViewById(R.id.depositdetails);
        susudeposit = findViewById(R.id.susudepositet);
        loandeposit = findViewById(R.id.loandepositet);
        paysusu = findViewById(R.id.paysusubutton);
        payloan = findViewById(R.id.payloanbutton);


        String details = "Username:"+getIntent().getStringExtra("username")+"\n"+
                "Email:"+getIntent().getStringExtra("email")+"\n"+
                "Susu balance:"+getIntent().getStringExtra("susu")+"\n"+
                "Loan balance:"+getIntent().getStringExtra("loan");



        depositdetails.setText(details);



        paysusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (susudeposit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cant be empty", Toast.LENGTH_SHORT).show();
                }else{

                    Float ans = Float.parseFloat(getIntent().getStringExtra("susu"))+
                            Float.parseFloat(susudeposit.getText().toString());

                    databaseReference = firebaseDatabase.getReference("Users").child(getIntent().getStringExtra("username"));
                    paysusu.setClickable(false);


                    databaseReference.child("susu").setValue(String.valueOf(ans)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Money added", Toast.LENGTH_SHORT).show();

                            Map <String,Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("status", "deposit");
                            hashMap.put("amount", susudeposit.getText().toString());
                            hashMap.put("timestamp",ServerValue.TIMESTAMP);
                            hashMap.put("username", getIntent().getStringExtra("username"));




                            databaseReference.child("susuhistory").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {



                                    finish();
                                }
                            });


                            Map<String,Object> hashMap1 = new HashMap<String, Object>();
                            hashMap1.put("status", "deposit");
                            hashMap1.put("amount", susudeposit.getText().toString());
                            hashMap1.put("timestamp", ServerValue.TIMESTAMP);
                            hashMap1.put("username", getIntent().getStringExtra("username"));




                            databaseReference3.push().setValue(hashMap1);







                        }
                    });









                }
            }
        });


        payloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loandeposit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cant be empty", Toast.LENGTH_SHORT).show();
                }else{

                    Float ans = Float.parseFloat(getIntent().getStringExtra("loan"))-
                            Float.parseFloat(loandeposit.getText().toString());

                    payloan.setClickable(false);

                    Map<String,Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("approved", "payment");
                    hashMap.put("amount", loandeposit.getText().toString());
                    hashMap.put("timestamp", ServerValue.TIMESTAMP);
                    hashMap.put("username", getIntent().getStringExtra("username"));

                    databaseReference.child("loanshistory").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Money added", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    });

                    databaseReference.child("loan").setValue(String.valueOf(ans)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Map <String,Object> hashMap1 = new HashMap<String, Object>();
                            hashMap1.put("status", "deposit");
                            hashMap1.put("amount", loandeposit.getText().toString());
                            hashMap1.put("timestamp", ServerValue.TIMESTAMP);
                            hashMap1.put("username", getIntent().getStringExtra("username"));



                            databaseReference3.push().setValue(hashMap1);
                            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                        }
                    });






















                }
            }
        });
    }
}
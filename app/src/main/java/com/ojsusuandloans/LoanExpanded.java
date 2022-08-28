package com.ojsusuandloans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoanExpanded extends AppCompatActivity {

    TextView depositdetails;
    EditText loandeposit;
    Button payloan,reject;
    FrameLayout back;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1,databaseReference3;

    ImageView loanpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_expanded);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(getIntent().getStringExtra("username"));
        databaseReference1 = firebaseDatabase.getReference("Loans").child("unapproved").child(getIntent().getStringExtra("username"));
        databaseReference3 = firebaseDatabase.getReference("Alltransactions");

        back = findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        depositdetails = findViewById(R.id.loandetails);

        loandeposit = findViewById(R.id.loanamount);

        payloan = findViewById(R.id.payamountbutton);

        loanpicture = findViewById(R.id.loanpicture);

        reject = findViewById(R.id.rejectbutton);


        String details = "Phonenumber: "+getIntent().getStringExtra("phonenumber")+"\n"+
                "Id type: "+getIntent().getStringExtra("idtype")+"\n"+
                "Id number: "+getIntent().getStringExtra("idnumber")+"\n"+
                "Ref 1 Name: "+getIntent().getStringExtra("ref1name")+"\n"+
                "Ref 1 number: "+getIntent().getStringExtra("ref1number")+"\n"+
                "Ref 1 relationship: "+getIntent().getStringExtra("ref1relationship")+"\n"+
                "Ref 2 name: "+getIntent().getStringExtra("ref2name")+"\n"+
                "Ref 2 number: "+getIntent().getStringExtra("ref2number")+"\n"+
                "Ref 2 relationship: "+getIntent().getStringExtra("ref2relationship")+"\n"+
                "Approved status: "+getIntent().getStringExtra("approved")+"\n"+
                "username: "+getIntent().getStringExtra("username");



        depositdetails.setText(details);


        try {
            Bitmap imageBitmap = decodeFromFirebaseBase64(getIntent().getStringExtra("pictureurl"));
            loanpicture.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }






        payloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loandeposit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cant be empty", Toast.LENGTH_SHORT).show();
                }else{

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            double interestcalc = 1.18 * Float.parseFloat(loandeposit.getText().toString());

                            double ans = Float.parseFloat(snapshot.child("loan").getValue().toString())+
                                    interestcalc;

                            databaseReference.child("loan").setValue(String.valueOf(ans)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Money added", Toast.LENGTH_SHORT).show();
                                }
                            });


                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Map<String,Object> hashMap = new HashMap<String, Object>();
                                    hashMap.put("status", "loan");
                                    hashMap.put("amount", loandeposit.getText().toString());
                                    hashMap.put("timestamp", ServerValue.TIMESTAMP);
                                    hashMap.put("username", getIntent().getStringExtra("username"));
                                    hashMap.put("approved", "yes");

                                    databaseReference.child("loanshistory").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            databaseReference1.removeValue();
                                        }
                                    });




                                    databaseReference1.child("approved").setValue("yes").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),"Loan Approved", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                    databaseReference1.child("amount").setValue(loandeposit.getText().toString());



                                    Map<String,Object> hashMap1 = new HashMap<String, Object>();
                                    hashMap1.put("status", "loan");
                                    hashMap1.put("amount", loandeposit.getText().toString());
                                    hashMap1.put("timestamp", ServerValue.TIMESTAMP);
                                    hashMap1.put("username", getIntent().getStringExtra("username"));



                                    databaseReference3.push().setValue(hashMap1);
                                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });






                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });












                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Float ans = Float.parseFloat(snapshot.child("loan").getValue().toString())+
                                Float.parseFloat("0");

                        databaseReference.child("loan").setValue(String.valueOf(ans)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Money not added", Toast.LENGTH_SHORT).show();
                            }
                        });

                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Map<String,Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("status", "loan");
                                hashMap.put("amount", loandeposit.getText().toString());
                                hashMap.put("timestamp", ServerValue.TIMESTAMP);
                                hashMap.put("username", getIntent().getStringExtra("username"));
                                hashMap.put("approved", "rejected");
                                hashMap.put("duedate","Not applicable");

                                databaseReference.child("loanshistory").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        databaseReference1.removeValue();
                                    }
                                });

                                databaseReference1.child("approved").setValue("rejected").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(),"Loan Approved", Toast.LENGTH_SHORT).show();


                                    }
                                });








                                databaseReference1.child("amount").setValue("0");


                                Map<String,Object> hashMap1 = new HashMap<String, Object>();
                                hashMap1.put("status", "loan");
                                hashMap1.put("amount", "N/A");
                                hashMap1.put("timestamp", ServerValue.TIMESTAMP);
                                hashMap1.put("username", getIntent().getStringExtra("username"));



                                databaseReference3.push().setValue(hashMap1);
                                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });






                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });












            }
        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
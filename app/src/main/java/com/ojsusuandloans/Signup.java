package com.ojsusuandloans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText etRegEmail;
    EditText etRegPassword;
    EditText etfirstname,etlastname;
    LinearLayout tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;
    LottieAnimationView lottieAnimationView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);etRegEmail = findViewById(R.id.useremail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        users = new Users();

        etRegPassword = findViewById(R.id.userpin);
        etfirstname = findViewById(R.id.firstname);
        etlastname = findViewById(R.id.lastname);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        lottieAnimationView = findViewById(R.id.spin);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{

            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });
    }

    private void createUser(){
        String firstname = etfirstname.getText().toString();
        String lastname = etlastname.getText().toString();
        String username = etfirstname.getText().toString() + etlastname.getText().toString();
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String pictureurl = "";
        String susu = "0.00";
        String loan = "0.00";
        String phonenumber = "";

        if (TextUtils.isEmpty(firstname)){
            etfirstname.setError("First name cannot be empty");
            etfirstname.requestFocus();}

        if (TextUtils.isEmpty(lastname)){
            etlastname.setError("First name cannot be empty");
            etlastname.requestFocus();}

        else if (!Pattern.matches("[a-zA-Z0-9]+",username)){
            etfirstname.setError("Name should not contain symbols");
            etfirstname.requestFocus();}

        else{

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(username)) {
                        etfirstname.setError("Username already chosen. Choose another one");
                        etfirstname.requestFocus();
                    }
                    else if (TextUtils.isEmpty(email)){
                        etRegEmail.setError("Email cannot be empty");
                        etRegEmail.requestFocus();
                    }else if (TextUtils.isEmpty(password)){
                        etRegPassword.setError("Pin cannot be empty");
                        etRegPassword.requestFocus();
                    }else{
                        btnRegister.setVisibility(View.INVISIBLE);
                        btnRegister.setClickable(false);
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    addDatatoFirebase(firstname,lastname,username, email, pictureurl,phonenumber,susu,loan,password);

                                }else{
                                    Toast.makeText(Signup.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    btnRegister.setVisibility(View.VISIBLE);
                                    btnRegister.setClickable(true);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }




    }



    private void addDatatoFirebase(String firstname, String lastname, String username, String email, String pictureurl, String phonenumber, String susu, String loan,String password) {

        users.setFirstname(firstname);
        users.setLastname(lastname);
        users.setUsername(username);
        users.setEmail(email);
        users.setPictureurl(pictureurl);
        users.setPhonenumber(phonenumber);
        users.setSusu(susu);
        users.setLoan(loan);
        users.setPassword(password);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child(username).setValue(users);
                Toast.makeText(Signup.this, "You have been registered successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup.this,MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Signup.this, "An error occurred please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    private EditText emailTextView, passwordTextView;
    private Button Btn;
    LinearLayout tvLoginHere;
    TextView forgot;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        tvLoginHere = findViewById(R.id.signupbutton);

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.useremail);
        passwordTextView = findViewById(R.id.userpin);
        Btn = findViewById(R.id.btnLogin);
        LottieAnimationView lottieAnimationView = findViewById(R.id.spin);

        forgot = findViewById(R.id.forgotpass);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });


        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, Signup.class));
            finish();
        });

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {

        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError("Email cannot be empty");
            emailTextView.requestFocus();
        }else if (TextUtils.isEmpty(password)) {
            passwordTextView.setError("Password cannot be empty");
            passwordTextView.requestFocus();
        }else if (email.equals("bimbofinanceadmin")&&password.equals("654321")){
            Intent intent3 = new Intent(Login.this,MainActivityAdmin.class);
            startActivity(intent3);
        }




        else {

            Btn.setVisibility(View.INVISIBLE);
            Btn.setClickable(false);

            // signin existing user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                        "Login successful",
                                                        Toast.LENGTH_LONG)
                                                .show();
                                        Intent intent
                                                = new Intent(Login.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        // sign-in failed
                                        Toast.makeText(getApplicationContext(),
                                                        "Login failed!!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        Btn.setVisibility(View.VISIBLE);
                                        Btn.setClickable(true);
                                    }
                                }
                            });
        }
    }

}
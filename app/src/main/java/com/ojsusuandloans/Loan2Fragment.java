package com.ojsusuandloans;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class Loan2Fragment extends Fragment {


    // create instance of firebase auth
    FirebaseAuth auth;
    String verificationID;
    TextView entermobnumbertext;
    LinearLayout numberlinearlayoutt;
    LottieAnimationView lottieAnimationView,lottieAnimationView1;
    EditText otpnumber;
    Button submit,generateotp;
    Button nextbtn,prevbtn;
    EditText phonenumberr;
    Loans loans;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    private Loan1Fragment.OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (Loan1Fragment.OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }


    public static Fragment newInstance() {
        return new Loan2Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan2, container, false);

        phonenumberr = view.findViewById(R.id.mobile_number);
        otpnumber = view.findViewById(R.id.otpNumber);
        submit = view.findViewById(R.id.submitBtn);
        lottieAnimationView = view.findViewById(R.id.spin);
        lottieAnimationView1 = view.findViewById(R.id.spin1);

        lottieAnimationView1.setVisibility(View.GONE);
        loans = new Loans();

        numberlinearlayoutt = view.findViewById(R.id.numberlinearlayout);

        auth = FirebaseAuth.getInstance();

        generateotp = view.findViewById(R.id.generate_otp);

        entermobnumbertext = view.findViewById(R.id.entermobnumber);

        otpnumber.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);

        nextbtn = view.findViewById(R.id.btn_next);
        prevbtn = view.findViewById(R.id.btn_prev);




        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });


        generateotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phonenumberr.getText().toString().isEmpty() | phonenumberr.getText().toString().length() < 9) {
                    phonenumberr.setError("Phone number cannot be empty");
                    phonenumberr.requestFocus();


                } else {
                    generateotp.setVisibility(View.INVISIBLE);
                    generateotp.setClickable(false);
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber("+233" + phonenumberr.getText().toString())  // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(requireActivity())                 // Activity (for callback binding)
                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);


                }
            }


        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.GONE);

                if (TextUtils.isEmpty(otpnumber.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter OTP code", Toast.LENGTH_SHORT).show();
                } else
                    verifycode(otpnumber.getText().toString());
            }
        });
        return view;
    }


    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null) {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getActivity(), "Verification Failed", Toast.LENGTH_SHORT).show();
            generateotp.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(getActivity(), "We have sent you a code", Toast.LENGTH_SHORT).show();

                entermobnumbertext.setText("Enter OTP code");
                numberlinearlayoutt.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);
                lottieAnimationView1.setVisibility(View.VISIBLE);
                otpnumber.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);






        }
    };

    private void verifycode(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Number Verified", Toast.LENGTH_SHORT).show();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("Loans");
                            databaseReference1 = firebaseDatabase.getReference("Users");
                            createLoan();
                            nextbtn.setBackgroundColor(Color.parseColor("#3B8FF0"));
                            nextbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnButtonClickListener.onButtonClicked(v);
                                }
                            });
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            currentUser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                loginUserAccount();
                                            }
                                        }
                                    });

                        } else
                            Toast.makeText(getActivity(), "Number could not be verified", Toast.LENGTH_SHORT).show();
                        submit.setVisibility(View.VISIBLE);
                        submit.setClickable(true);

                    }


                });
    }

    private void createLoan() {
        String phonenumber = "+233"+phonenumberr.getText().toString();
        String idtype = "";
        String idnumber = "";
        String ref1name = "";
        String ref1number = "";
        String ref1relationship = "";
        String ref2name = "";
        String ref2number = "";
        String ref2relationship = "";
        String pictureurl = "";
        String approved = "no";

        addDatatoFirebase(phonenumber, idtype, idnumber,ref1name,ref1number,ref1relationship,ref2name,ref2number,ref2relationship,pictureurl,approved);




    }

    private void addDatatoFirebase(String phonenumber, String idtype, String idnumber, String ref1name, String ref1number, String ref1relationship, String ref2name, String ref2number, String ref2relationship, String pictureurl, String approved) {
        loans.setPhonenumber(phonenumber);
        loans.setIdtype(idtype);
        loans.setIdnumber(idnumber);
        loans.setRef1name(ref1name);
        loans.setRef1number(ref1number);
        loans.setRef1relationship(ref1relationship);
        loans.setRef2name(ref2name);
        loans.setRef2number(ref2number);
        loans.setRef2relationship(ref2relationship);
        loans.setPictureurl(pictureurl);
        loans.setApproved(approved);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                databaseReference.child("unapproved").child(getActivity().getIntent().getStringExtra("username")).setValue(loans);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loginUserAccount() {

        String email, password;
        email = requireActivity().getIntent().getStringExtra("email");
        password = requireActivity().getIntent().getStringExtra("password");

        // signin existing user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {








                                } else {

                                    // sign-in failed
                                    Toast.makeText(getActivity(),
                                                    "An error ocurred, sign out and try again",
                                                    Toast.LENGTH_LONG)
                                            .show();


                                }
                            }
                        });
    }







}


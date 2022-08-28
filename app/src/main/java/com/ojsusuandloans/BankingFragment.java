package com.ojsusuandloans;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.alexkolpa.fabtoolbar.FabToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ojsusuandloans.activity.LoansActivity;
import com.ojsusuandloans.activity.SusuActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankingFragment extends Fragment {

    DatabaseReference databaseReference;
    TextView usernamer, usernamerr, totalbalancer, susuer , loaner, emailsetterr, passwordsetterr, totalbalancesetterr;
    FirebaseDatabase firebaseDatabase;
    Users users;
    FirebaseAuth mAuth;

    CardView loansbutton,susubutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banking, container, false);

        FabToolbar fabToolbar = view.findViewById(R.id.fab_toolbar);

        usernamer = view.findViewById(R.id.usernamedisplay);
        usernamerr = view.findViewById(R.id.usernameset);
        totalbalancer = view.findViewById(R.id.money);
        susuer = view.findViewById(R.id.moneyy);
        loaner = view.findViewById(R.id.moneyyy);
        emailsetterr= view.findViewById(R.id.emailsetter);
        passwordsetterr= view.findViewById(R.id.passwordsetter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        users = new Users();
        mAuth = FirebaseAuth.getInstance();

        totalbalancesetterr = view.findViewById(R.id.totalbalancesetter);


        Query myquery = databaseReference.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
        myquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String parent = childSnapshot.getKey();

                    databaseReference.child(parent).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            usernamer.setText("Hi, "+snapshot.child("username").getValue().toString());
                            usernamerr.setText(snapshot.child("username").getValue().toString());
                            susuer.setText(snapshot.child("susu").getValue().toString());
                            loaner.setText(snapshot.child("loan").getValue().toString());
                            emailsetterr.setText(snapshot.child("email").getValue().toString());
                            passwordsetterr.setText(snapshot.child("password").getValue().toString());

                            Float ans = Float.parseFloat(snapshot.child("susu").getValue().toString());






                            totalbalancer.setText(String.valueOf(ans).format("%.02f", ans));
                            totalbalancesetterr.setText(snapshot.child("susu").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        view.findViewById(R.id.attach).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabToolbar.hide();
                fabToolbar.setColor(Color.parseColor("#232323"));

            }
        });

        view.findViewById(R.id.applyforloanbutton).setOnClickListener(new View.OnClickListener() {
                                                                          public void onClick(View v) {

                                                                              if (usernamerr.getText().toString().equals("usernamesetter")){
                                                                                  Toast.makeText(getActivity(),"Please ensure you have an internet connection and try again", Toast.LENGTH_SHORT).show();}
                                                                              else {
                                                                                  DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Loans");
                                                                                  DatabaseReference userref = FirebaseDatabase.getInstance().getReference("Users").child(usernamerr.getText().toString());
                                                                                  rootRef.child("unapproved").addValueEventListener(new ValueEventListener() {
                                                                                      @Override
                                                                                      public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                                          if (Float.parseFloat(loaner.getText().toString()) > 5){
                                                                                              Toast.makeText(getActivity(),"Please settle your loan to apply for another", Toast.LENGTH_SHORT).show();

                                                                                          }

                                                                                          else if (snapshot.child(usernamerr.getText().toString()).child("approved").exists()){

                                                                                              if (snapshot.child(usernamerr.getText().toString()).child("approved").getValue().toString().equals("no")) {
                                                                                                  Toast.makeText(getActivity(), "We are currently processing your loan. Your loan is under review", Toast.LENGTH_LONG).show();


                                                                                              } else {

                                                                                                  Intent intent = new Intent(getActivity(), ApplyforloanActivity.class);
                                                                                                  intent.putExtra("email", emailsetterr.getText().toString());
                                                                                                  intent.putExtra("password", passwordsetterr.getText().toString());
                                                                                                  intent.putExtra("username", usernamerr.getText().toString());

                                                                                                  startActivity(intent);


                                                                                              }


                                                                                          }



                                                                                          else {
                                                                                              Intent intent = new Intent(getActivity(), ApplyforloanActivity.class);
                                                                                              intent.putExtra("email", emailsetterr.getText().toString());
                                                                                              intent.putExtra("password", passwordsetterr.getText().toString());
                                                                                              intent.putExtra("username", usernamerr.getText().toString());

                                                                                              startActivity(intent);
                                                                                          }
                                                                                      }

                                                                                      @Override
                                                                                      public void onCancelled(@NonNull DatabaseError error) {

                                                                                      }
                                                                                  });

                                                                              }
                                                                          }
                                                                      });


        CardView depositbutton = view.findViewById(R.id.depositbutton);
        depositbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalbalancesetterr.getText().toString().equals("usernamesetter")){
                    Toast.makeText(getActivity(),"Please ensure you have an internet connection to continue", Toast.LENGTH_SHORT).show();}
                else{
                    Intent intent1 = new Intent(getActivity(),DepositActivity.class);
                    intent1.putExtra("username",usernamerr.getText().toString());
                    intent1.putExtra("totalbalance",totalbalancesetterr.getText().toString());
                    startActivity(intent1);
                }
            }
        });

        CardView withdrawbutton = view.findViewById(R.id.withdrawbutton);
        withdrawbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalbalancesetterr.getText().toString().equals("usernamesetter")){
                    Toast.makeText(getActivity(),"Please ensure you have an internet connection to continue", Toast.LENGTH_SHORT).show();}
                else{
                    Intent intent = new Intent(getActivity(),WithdrawActivity.class);
                    intent.putExtra("totalbalance", totalbalancesetterr.getText().toString());
                    intent.putExtra("PinVerified","false");
                    intent.putExtra("username",usernamerr.getText().toString());
                    startActivity(intent);}
            }
        });





        loansbutton = view.findViewById(R.id.loansbutton);
        susubutton = view.findViewById(R.id.susubutton);



        loansbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalbalancesetterr.getText().toString().equals("usernamesetter")){
                    Toast.makeText(getActivity(),"Please ensure you have an internet connection to continue", Toast.LENGTH_SHORT).show();}
                else {
                    Intent intent = new Intent(getActivity(), LoansActivity.class);
                    intent.putExtra("username", usernamerr.getText().toString());


                    startActivity(intent);
                }
            }
        });



        susubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalbalancesetterr.getText().toString().equals("usernamesetter")){
                    Toast.makeText(getActivity(),"Please ensure you have an internet connection to continue", Toast.LENGTH_SHORT).show();}
                else {
                Intent intent1 = new Intent(getActivity(), SusuActivity.class);
                intent1.putExtra("username",usernamerr.getText().toString());
                intent1.putExtra("PinVerified","false");
                intent1.putExtra("totalbalance", totalbalancesetterr.getText().toString());


                startActivity(intent1);}
            }
        });





        return view;
    }

    private static class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Create some layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            // Create some text
            TextView textView = getTextView(container.getContext());
            textView.setText(String.valueOf(position));
            textView.setLayoutParams(layoutParams);

            RelativeLayout layout = new RelativeLayout(container.getContext());
            layout.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.colorPrimary));
            layout.setLayoutParams(layoutParams);

            layout.addView(textView);
            container.addView(layout);
            return layout;
        }

        private TextView getTextView(Context context) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}

package com.ojsusuandloans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ApplyforloanActivity extends AppCompatActivity implements Loan1Fragment.OnButtonClickListener {

    CustomViewPager pager;

    @Override
    public void onButtonClicked(View view){
        int currPos=pager.getCurrentItem();

        switch(view.getId()){

            case R.id.btn_next:
                //handle currPos is zero
                pager.setCurrentItem(currPos+1,true);
                break;

            case R.id.btn_prev:
                //handle currPos is reached last item
                pager.setCurrentItem(currPos-1,true);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyforloan);


         pager = findViewById(R.id.pager2);
        pager.setPagingEnabled(false);


        assert pager != null;
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        assert indicator != null;
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);


        indicator.setViewPager(pager, Objects.requireNonNull(pager.getAdapter()).getCount());






    }



    public static class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Loan0Fragment.newInstance();
                case 1:
                    return Loan1Fragment.newInstance();
                case 2:
                    return Loan2Fragment.newInstance();
                case 3:
                    return Loan3Fragment.newInstance();
                case 4:
                    return Loan4Fragment.newInstance();
                case 5:
                    return Loan5Fragment.newInstance();
                case 6:
                    return Loan6Fragment.newInstance();
                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            return 7;
        }
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                ApplyforloanActivity.this);

// Setting Dialog Title
        alertDialog2.setTitle("Cancel Loan Application");

// Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want cancel loan application?");

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_baseline_cancel_24);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (pager.getCurrentItem()==0|pager.getCurrentItem()==1){
                            Toast.makeText(getApplicationContext(),"Loan Application Cancelled",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Loans");
                            rootRef.child("unapproved").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    if (snapshot.hasChild(getIntent().getStringExtra("username"))) {
                                        rootRef.child("unapproved").child(getIntent().getStringExtra("username")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(),"Loan Application Cancelled",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });




                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Loan Application Cancelled",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }



                    }
                });

        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });


        alertDialog2.show();



    }
}
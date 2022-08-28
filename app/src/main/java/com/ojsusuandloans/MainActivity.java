package com.ojsusuandloans;

import static com.ojsusuandloans.LoanExpanded.decodeFromFirebaseBase64;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    Users users;
    FirebaseAuth mAuth;

    private ArrayList<String> mTitles = new ArrayList<>();
    private final ArrayList<Integer> mTitlesIcon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        users = new Users();
        mAuth = FirebaseAuth.getInstance();
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        mTitlesIcon.add(R.drawable.ic_group);
        mTitlesIcon.add(R.drawable.ic_group_264);
        mTitlesIcon.add(R.drawable.ic_group_263);


        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        goToFragment(new BankingFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles, mTitlesIcon, this);


        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);






    }

    @Override
    public void onFooterClicked() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"You have been signed out successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onHeaderClicked() {
        if (mViewHolder.textView.getText().toString().equals("Customer name")){
            Toast.makeText(getApplicationContext(),"Please make sure you have an internet connection to continue", Toast.LENGTH_SHORT).show();}
        else {

        Intent intentt = new Intent(MainActivity.this,Profile.class);
        intentt.putExtra(mViewHolder.textView.getText().toString(),"username");
        startActivity(new Intent(MainActivity.this,Profile.class));}
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);


        // Navigate to the right fragment
        switch (position) {
            default:
                goToFragment(new BankingFragment(), false);
                break;
            case 1:
                goToFragment(new SettingsFragment(), false);
                break;
            case 2:
                goToFragment(new AboutFragment(), false);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private final DuoDrawerLayout mDuoDrawerLayout;
        private final DuoMenuView mDuoMenuView;
        private final Toolbar mToolbar;
        TextView textView;
        private TextView hj;
        ImageView userprofile;





        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();

            mToolbar = findViewById(R.id.toolbar);

            textView = mDuoMenuView.getHeaderView().findViewById(R.id.usernamedisplay);


            userprofile = mDuoMenuView.getHeaderView().findViewById(R.id.profilepicture);


            Query myquery = databaseReference.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
            myquery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                        String parent = childSnapshot.getKey();

                        databaseReference.child(parent).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                textView.setText(snapshot.child("username").getValue().toString());

                                if (!snapshot.child("pictureurl").getValue().toString().isEmpty()){

                                try {
                                    Bitmap imageBitmap = decodeFromFirebaseBase64(snapshot.child("pictureurl").getValue().toString());
                                    userprofile.setImageBitmap(imageBitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
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


        }
    }




    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double click BACK to exit", Toast.LENGTH_SHORT).show();
        mViewHolder.mDuoDrawerLayout.closeDrawer();
        mViewHolder.mDuoDrawerLayout.openDrawer();


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}

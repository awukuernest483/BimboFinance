package com.ojsusuandloans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAdmin extends AppCompatActivity {

    Button withdraw,deposit,susu,loan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainadmin);

        withdraw = findViewById(R.id.withdrawbutton);
        deposit = findViewById(R.id.depositbutton);
        susu = findViewById(R.id.susubutton);
        loan = findViewById(R.id.loanbutton);

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this,WithdrawActivityAdmin.class);

                startActivity(intent);
            }
        });

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this,LoansActivityAdmin.class);

                startActivity(intent);
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this,DepositActivityAdmin.class);

                startActivity(intent);
            }
        });

        susu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this,Alltransactionsadmin.class);

                startActivity(intent);
            }
        });
    }
}
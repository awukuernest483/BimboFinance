package com.ojsusuandloans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ojsusuandloans.activity.EnterPinActivity;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;



public class WithdrawActivity extends AppCompatActivity {

    TextView balance,maxwithdraw,spinnerindicator,spinner1indicator;
    FrameLayout back;
    CardView withdraw;

    String name;
    SharedPreferences preferences;
    ScrollView info;

    Spinner spinner,spinner1;
    MaterialRadioGroup materialCompoundGroup;
    MaterialRadioButton bank,network;

    LinearLayout bankl,mobl;

    TextView bankornetworkindicator;

    EditText amount,accnum,accname,amountnetwork,mobilemoneynumber,accnetname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        accnetname = findViewById(R.id.accnetname);

        materialCompoundGroup = findViewById(R.id.material_group);
        bankl = findViewById(R.id.banklinear);
        mobl = findViewById(R.id.mobilemoneylinear);

        bankornetworkindicator = findViewById(R.id.bankornetworkindicator);


        spinnerindicator = findViewById(R.id.spinnerindicator);
        spinner1indicator = findViewById(R.id.spinner1indicator);


        amount = findViewById(R.id.amount);
        accname = findViewById(R.id.accname);
        accnum = findViewById(R.id.accnum);
        amountnetwork = findViewById(R.id.amountnetwork);
        mobilemoneynumber = findViewById(R.id.mobilemoneynumber);




        bank = findViewById(R.id.material_radio_button_bank);
        network = findViewById(R.id.material_radio_button_mobilenetworks);

        materialCompoundGroup.check(0);




        List<Banks> items = new ArrayList<Banks>(5);
        items.add(new Banks(getString(R.string.access), R.drawable.access));
        items.add(new Banks(getString(R.string.uba), R.drawable.uba));
        items.add(new Banks(getString(R.string.ecobank), R.drawable.ecobank));
        items.add(new Banks(getString(R.string.fidelity), R.drawable.fidelity));
        items.add(new Banks(getString(R.string.calbank), R.drawable.calbank));


        List<Banks> items1 = new ArrayList<Banks>(6);
        items1.add(new Banks(getString(R.string.mtn), R.drawable.mtn));
        items1.add(new Banks(getString(R.string.vodafone), R.drawable.vodafone));
        items1.add(new Banks(getString(R.string.airteltigo), R.drawable.airteltigo));
        items1.add(new Banks(getString(R.string.zeepay), R.drawable.zeepay));
        items1.add(new Banks(getString(R.string.gmoney), R.drawable.gmoney));
        items1.add(new Banks(getString(R.string.ghanapay), R.drawable.ghanapay));

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = preferences.getString("Hassetpin", "");


        spinner1 = findViewById(R.id.spinner1);
        spinner1.setAdapter(new BankSpinnerAdapter(this, items1));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spinner1indicator.setText(((Banks) adapterView.getItemAtPosition(position)).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });




        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new BankSpinnerAdapter(this, items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spinnerindicator.setText(((Banks) adapterView.getItemAtPosition(position)).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });

        materialCompoundGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                if (bank.isChecked()){
                    spinner.setVisibility(View.VISIBLE);
                    spinner1.setVisibility(View.GONE);
                    bankl.setVisibility(View.VISIBLE);
                    mobl.setVisibility(View.GONE);
                    bankornetworkindicator.setText("bank");

                }else{
                    spinner.setVisibility(View.GONE);
                    spinner1.setVisibility(View.VISIBLE);
                    bankl.setVisibility(View.GONE);
                    mobl.setVisibility(View.VISIBLE);
                    bankornetworkindicator.setText("network");
                }
            }
        });

        balance = findViewById(R.id.balancetext);
        back = findViewById(R.id.backbutton);
        withdraw = findViewById(R.id.withdrawbutton);
        maxwithdraw = findViewById(R.id.maxwithdrawal);
        info = findViewById(R.id.infoscrollview);

        maxwithdraw.setText(getIntent().getStringExtra("susu"));









        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bankornetworkindicator.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill details",Toast.LENGTH_SHORT).show();
                }
                else if (bankornetworkindicator.getText().toString().equals("bank")&&((amount.getText().toString().isEmpty()|
                        accnum.getText().toString().isEmpty()|
                        accname.getText().toString().isEmpty()))){
                    Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                } else if (bankornetworkindicator.getText().toString().equals("bank")&&Float.parseFloat(amount.getText().toString()) > Float.parseFloat(maxwithdraw.getText().toString()))
                        {
                    Toast.makeText(getApplicationContext(),"Balance is below entered amount",Toast.LENGTH_SHORT).show();
                }
                else if (bankornetworkindicator.getText().toString().equals("network")&&((amountnetwork.getText().toString().isEmpty()|
                        mobilemoneynumber.getText().toString().isEmpty()|
                        accnetname.getText().toString().isEmpty()))){
                    Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                }
                 else if (bankornetworkindicator.getText().toString().equals("network")&&Float.parseFloat(amountnetwork.getText().toString()) > Float.parseFloat(maxwithdraw.getText().toString()))
                {
                Toast.makeText(getApplicationContext(),"Balance is below entered amount",Toast.LENGTH_SHORT).show();
                }


                else if (bankornetworkindicator.getText().toString().equals("network")&&(amountnetwork.getText().toString().isEmpty()|
                        mobilemoneynumber.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    withdraw.setCardBackgroundColor(Color.parseColor("#232324"));
                    Intent intent = new Intent(WithdrawActivity.this, EnterPinActivity.class);
                    if (bankornetworkindicator.getText().toString().equals("bank")){
                        intent.putExtra("amount",amount.getText().toString());
                        intent.putExtra("accountnumber", accnum.getText().toString());
                        intent.putExtra("accountname", accname.getText().toString());
                        intent.putExtra("bank",spinnerindicator.getText().toString());
                        intent.putExtra("username",getIntent().getStringExtra("username"));
                        intent.putExtra("color","#FF0000");
                    }
                    else {
                        intent.putExtra("amount",amountnetwork.getText().toString());
                        intent.putExtra("accountnumber", mobilemoneynumber.getText().toString());
                        intent.putExtra("accountname", accnetname.getText().toString());
                        intent.putExtra("bank",spinner1indicator.getText().toString());
                        intent.putExtra("username",getIntent().getStringExtra("username"));
                        intent.putExtra("color","#FF0000");


                    }
                    startActivity(intent);
                }



            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Float ans = Float.parseFloat(getIntent().getStringExtra("totalbalance")) -
                Float.parseFloat("30.00");

        maxwithdraw.setText(String.valueOf(ans));


        balance.setText(getIntent().getStringExtra("totalbalance"));




    }





    @Override
    public void onResume(){
        super.onResume();


    }


}
package com.ojsusuandloans.activity;

import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ojsusuandloans.Loans;
import com.ojsusuandloans.R;
import com.ojsusuandloans.susuhistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class SusuhistoryAdapter extends FirebaseRecyclerAdapter<
        susuhistory, SusuhistoryAdapter.LoansViewholder> {

    public SusuhistoryAdapter(
            @NonNull FirebaseRecyclerOptions<susuhistory> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void
    onBindViewHolder(@NonNull LoansViewholder holder,
                     int position, @NonNull susuhistory model)
    {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(model.getTimestamp());
        String date = DateFormat.format("EEE, d MMM yyyy hh:mm aaa",cal).toString();




        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        if (model.getStatus().toString().equals("deposit")){
            holder.amounttext.setText("+ Ghs"+ model.getAmount().toString());
            holder.amounttext.setTextColor(Color.parseColor("#00ff00"));
            holder.timetext.setText(date);
            holder.usernametext.setText("");

        }else {
            holder.statustext.setText("Withdrawal");
            holder.amounttext.setText("- Ghs"+ model.getAmount().toString());
            holder.amounttext.setTextColor(Color.parseColor("#ff0000"));
            holder.timetext.setText(date);
            holder.usernametext.setText("");


        }

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")



        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")

    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public LoansViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.susuhistory_item, parent, false);
        return new SusuhistoryAdapter.LoansViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class LoansViewholder
            extends RecyclerView.ViewHolder {
        TextView statustext, amounttext,timetext,usernametext;
        ImageView loanindicator;
        public LoansViewholder(@NonNull View itemView)
        {
            super(itemView);

            statustext = itemView.findViewById(R.id.status);
            amounttext = itemView.findViewById(R.id.amounttext);
            timetext = itemView.findViewById(R.id.timetext);
            usernametext = itemView.findViewById(R.id.usernametext);

        }

    }
}

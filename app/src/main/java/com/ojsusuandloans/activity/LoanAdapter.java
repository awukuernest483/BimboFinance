package com.ojsusuandloans.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ojsusuandloans.Loans;
import com.ojsusuandloans.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoanAdapter extends FirebaseRecyclerAdapter<
        Loans, LoanAdapter.LoansViewholder> {

    public LoanAdapter(
            @NonNull FirebaseRecyclerOptions<Loans> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull LoansViewholder holder,
                     int position, @NonNull Loans model)
    {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(model.getTimestamp());
        String date = DateFormat.format("EEE, d MMM yyyy hh:mm aaa",cal).toString();










        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        if (model.getApproved().toString().equals("no")){
            holder.statustext.setText("Under Review");
            holder.amounttext.setText(model.getAmount());
            holder.loanindicator.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.timetext.setText(date);


        }else if (model.getApproved().toString().equals("payment")){

            holder.statustext.setText("Payment Approved");
            holder.loanindicator.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.amounttext.setText("- "+model.getAmount());
            holder.texto.setText("Loan Payment");
            holder.timetext.setText(date);






        }
        else if (model.getApproved().toString().equals("yes")){
            holder.statustext.setText("Loan Approved");
            holder.statustext.setTextColor(Color.parseColor("#00ff00"));
            holder.amounttext.setText(model.getAmount());
            holder.loanindicator.setBackgroundColor(Color.parseColor("#00ff00"));
            holder.timetext.setText(date);


        }else {

            holder.statustext.setText("Loan Rejected. Please re-apply");
            holder.statustext.setTextColor(Color.parseColor("#ff0000"));
            holder.amounttext.setText(model.getAmount());
            holder.loanindicator.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.timetext.setText(date);



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
                .inflate(R.layout.recycler_item_layout, parent, false);
        return new LoanAdapter.LoansViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class LoansViewholder
            extends RecyclerView.ViewHolder {
        TextView statustext, amounttext,texto,timetext;
        ImageView loanindicator;
        public LoansViewholder(@NonNull View itemView)
        {
            super(itemView);

            statustext = itemView.findViewById(R.id.statustext);
            amounttext = itemView.findViewById(R.id.amounttext);
            loanindicator= itemView.findViewById(R.id.loanindicator);
            timetext = itemView.findViewById(R.id.timetext);
            texto = itemView.findViewById(R.id.texto);
        }

    }
}

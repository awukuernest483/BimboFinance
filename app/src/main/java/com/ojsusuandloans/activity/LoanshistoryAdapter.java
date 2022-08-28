package com.ojsusuandloans.activity;

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
import com.ojsusuandloans.R;
import com.ojsusuandloans.loanshistory;

import java.util.Calendar;
import java.util.Locale;

public class LoanshistoryAdapter extends FirebaseRecyclerAdapter<
        loanshistory, LoanshistoryAdapter.LoansViewholder> {

    public LoanshistoryAdapter(
            @NonNull FirebaseRecyclerOptions<loanshistory> optionss)
    {
        super(optionss);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull LoansViewholder holder,
                     int position, @NonNull loanshistory model)
    {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(model.getTimestamp());
        String date = DateFormat.format("EEE, d MMM yyyy hh:mm aaa",cal).toString();


        Calendar call = Calendar.getInstance(Locale.ENGLISH);
        call.setTimeInMillis(model.getTimestamp() + 864000000L);
        String datee = DateFormat.format("EEE, d MMM yyyy hh:mm aaa",call).toString();


        double interestcalc = 1.18 * Float.parseFloat(model.getAmount());
        double interest = interestcalc - Double.parseDouble(model.getAmount());
        String interestvalue =        String.format("%.2f", interest);










        if (model.getApproved().toString().equals("no")){
            holder.statustext.setText("Under Review");
            holder.amounttext.setText("GHs 50.00");
            holder.loanindicator.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.timetext.setText(date);
            holder.duedatetext.setText("");
            holder.principal.setText("");
            holder.interest.setText("");



        }else if (model.getApproved().toString().equals("payment")){

            holder.statustext.setText("Payment Approved");
            holder.loanindicator.setBackgroundColor(Color.parseColor("#00ff00"));
            holder.amounttext.setText("Ghs "+model.getAmount());
            holder.texto.setText("Loan Payment");
            holder.statustext.setTextColor(Color.parseColor("#00ff00"));
            holder.timetext.setText(date);
            holder.duedatetext.setText("");
            holder.principal.setText("");
            holder.interest.setText("");






        }
        else if (model.getApproved().toString().equals("yes")){
            holder.statustext.setText("Loan Approved");
            holder.statustext.setTextColor(Color.parseColor("#00ff00"));
            holder.amounttext.setText("- Ghs" + interestcalc);
            holder.loanindicator.setBackgroundColor(Color.parseColor("#00ff00"));
            holder.timetext.setText(date);
            holder.duedatetext.setText("Due Date: "+datee);
            holder.principal.setText("Principal:  Ghs "+model.getAmount());
            holder.interest.setText("Interest:  Ghs "+interestvalue);


        }else {

            holder.statustext.setText("Loan Rejected. Please re-apply");
            holder.statustext.setTextColor(Color.parseColor("#ff0000"));
            holder.amounttext.setText(model.getAmount());
            holder.loanindicator.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.timetext.setText(date);
            holder.duedatetext.setText("Not Applicable");
            holder.principal.setText("");
            holder.interest.setText("");





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
        return new LoanshistoryAdapter.LoansViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class LoansViewholder
            extends RecyclerView.ViewHolder {
        TextView statustext, amounttext,texto,timetext,duedatetext,interest,principal;
        ImageView loanindicator;
        public LoansViewholder(@NonNull View itemView)
        {
            super(itemView);

            statustext = itemView.findViewById(R.id.statustext);
            amounttext = itemView.findViewById(R.id.amounttext);
            loanindicator= itemView.findViewById(R.id.loanindicator);
            texto = itemView.findViewById(R.id.texto);
            timetext = itemView.findViewById(R.id.timetext);
            duedatetext = itemView.findViewById(R.id.duedatetext);
            interest = itemView.findViewById(R.id.interest);
            principal = itemView.findViewById(R.id.principal);

        }

    }
}

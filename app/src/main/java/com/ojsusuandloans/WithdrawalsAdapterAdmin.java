package com.ojsusuandloans;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class WithdrawalsAdapterAdmin extends FirebaseRecyclerAdapter<
        Withdrawals, WithdrawalsAdapterAdmin.WithdrawalsViewholder> {

    public WithdrawalsAdapterAdmin(
            @NonNull FirebaseRecyclerOptions<Withdrawals> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull WithdrawalsViewholder holder,
                     int position, @NonNull Withdrawals model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.statustext.setText(model.getUsername());
        holder.statustext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),WithdrawExpanded.class);
                intent.putExtra("accountname",model.getAccountname());
                intent.putExtra("accountnumber",model.getAccountnumber());
                intent.putExtra("amount",model.getAmount());
                intent.putExtra("username",model.getUsername());
                view.getContext().startActivity(intent);

            }
        });


    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public WithdrawalsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.withdrawals_item, parent, false);
        return new WithdrawalsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class WithdrawalsViewholder
            extends RecyclerView.ViewHolder {
        TextView statustext, amounttext;
        ImageView loanindicator;
        public WithdrawalsViewholder(@NonNull View itemView)
        {
            super(itemView);

            statustext = itemView.findViewById(R.id.custom_post_textView);

        }

    }
}

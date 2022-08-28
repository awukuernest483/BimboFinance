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


public class DepositsAdapterAdmin extends FirebaseRecyclerAdapter<
        Users, DepositsAdapterAdmin.WithdrawalsViewholder> {

    public DepositsAdapterAdmin(
            @NonNull FirebaseRecyclerOptions<Users> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull WithdrawalsViewholder holder,
                     int position, @NonNull Users model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.statustext.setText(model.getEmail());
        holder.statustext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DepositExpanded.class);
                intent.putExtra("username",model.getUsername());
                intent.putExtra("email",model.getEmail());
                intent.putExtra("susu",model.getSusu());
                intent.putExtra("loan",model.getLoan());
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

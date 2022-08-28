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


public class LoansAdapterAdmin extends FirebaseRecyclerAdapter<
        Loans, LoansAdapterAdmin.WithdrawalsViewholder> {

    public LoansAdapterAdmin(
            @NonNull FirebaseRecyclerOptions<Loans> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull WithdrawalsViewholder holder,
                     int position, @NonNull Loans model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.statustext.setText(model.getPhonenumber());
        holder.statustext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LoanExpanded.class);
                intent.putExtra("phonenumber",model.getPhonenumber());
                intent.putExtra("idtype",model.getIdtype());
                intent.putExtra("idnumber",model.getIdnumber());
                intent.putExtra("ref1name",model.getRef1name());
                intent.putExtra("ref1number",model.getRef1number());
                intent.putExtra("ref1relationship",model.getRef1relationship());
                intent.putExtra("ref2name",model.getRef2name());
                intent.putExtra("ref2number",model.getRef2number());
                intent.putExtra("ref2relationship",model.getRef2relationship());
                intent.putExtra("pictureurl",model.getPictureurl());
                intent.putExtra("approved", model.getApproved());
                intent.putExtra("username", model.getUsername());
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

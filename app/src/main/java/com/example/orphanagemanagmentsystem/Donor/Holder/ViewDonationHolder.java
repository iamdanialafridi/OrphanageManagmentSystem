package com.example.orphanagemanagmentsystem.Donor.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orphanagemanagmentsystem.Model.Model_Donation;
import com.example.orphanagemanagmentsystem.R;

import java.util.List;

public class ViewDonationHolder extends RecyclerView.Adapter<ViewDonationHolder.ViewHolder> {
Context context;
List<Model_Donation> model_donationList;

    public ViewDonationHolder(@NonNull Context context, List<Model_Donation> model_donationList) {
        this.context = context;
        this.model_donationList = model_donationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     context=parent.getContext();
     View v = LayoutInflater.from(context).inflate(R.layout.donationxml,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Model_Donation model_donation=model_donationList.get(position);
holder.name.setText(model_donation.getName());
holder.email.setText(model_donation.getEmail());
holder.phone.setText(model_donation.getPhone());
holder.cardtype.setText(model_donation.getCardType());
holder.cardnumber.setText(model_donation.getCardNumber());
holder.amount.setText(model_donation.getAmount());
holder.date.setText(model_donation.getDate());
    }

    @Override
    public int getItemCount() {
        return model_donationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,phone,cardtype,cardnumber,amount,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvDonationName);
            email=itemView.findViewById(R.id.tvDonationEmail);
            phone=itemView.findViewById(R.id.tvDonationPhone);
            cardtype=itemView.findViewById(R.id.tvDonationCardType);
            cardnumber=itemView.findViewById(R.id.tvDonationCardNo);
            amount=itemView.findViewById(R.id.tvDonationAmount);
            date=itemView.findViewById(R.id.tvDonationDate);
        }
    }
}

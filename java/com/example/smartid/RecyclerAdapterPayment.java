package com.example.smartid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerAdapterPayment extends RecyclerView.Adapter<RecyclerAdapterPayment.ViewHolderPay>{

    List<Payment> payList;
    private Context payCtx;
    public static int pos = 0;




    public RecyclerAdapterPayment(List<Payment> payList, Context payCtx) {
        this.payList = payList;
        this.payCtx = payCtx;
    }

    @NonNull
    @Override
    public ViewHolderPay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pay, parent, false);

        ViewHolderPay ViewHolderPay = new ViewHolderPay(view);
        return ViewHolderPay;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPay holder, int position) {
        Payment payment = payList.get(position);
        pos=position;
        holder.trDate.setText(payment.getDate());
        holder.vendorName.setText(payment.getVendor());
        holder.amount.setText(payment.getAmount());


    }

    @Override
    public int getItemCount() {
        return payList.size();
    }

    class ViewHolderPay extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView cardImg;
        TextView trDate,vendorName,amount;


        public ViewHolderPay(@NonNull View itemView) {
            super(itemView);

            cardImg = itemView.findViewById(R.id.cardImg);
            trDate = itemView.findViewById(R.id.trDate);
            vendorName = itemView.findViewById(R.id.vendorName);
            amount = itemView.findViewById(R.id.amount);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //
        }
    }

}

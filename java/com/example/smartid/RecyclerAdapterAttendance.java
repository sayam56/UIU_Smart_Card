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

public class RecyclerAdapterAttendance extends RecyclerView.Adapter<RecyclerAdapterAttendance.ViewHolderAtt>{

    List<Attendance> attList;
    private Context attCtx;
    public static int pos = 0;




    public RecyclerAdapterAttendance(List<Attendance> attList, Context attCtx) {
        this.attList = attList;
        this.attCtx = attCtx;
    }

    @NonNull
    @Override
    public ViewHolderAtt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_att, parent, false);

        ViewHolderAtt viewHolderAtt = new ViewHolderAtt(view);
        return viewHolderAtt;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAtt holder, int position) {
        Attendance attendance = attList.get(position);
        pos=position;
        holder.datetv.setText(attendance.getDate());
        holder.presentTv.setText("Present");


    }

    @Override
    public int getItemCount() {
        return attList.size();
    }

    class ViewHolderAtt extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView def;
        TextView datetv,presentTv;


        public ViewHolderAtt(@NonNull View itemView) {
            super(itemView);

            def = itemView.findViewById(R.id.defImage);
            datetv = itemView.findViewById(R.id.dateTv);
            presentTv = itemView.findViewById(R.id.presentTv);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //
        }
    }

}

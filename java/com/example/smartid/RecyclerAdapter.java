package com.example.smartid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    Context mCtx;
    List<Course> list;


    public RecyclerAdapter(Context mCtx, List<Course> list) {
        this.mCtx = mCtx;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*holder.c_code.setText(String.valueOf(position)); //this method binds the data to the recycler view
        holder.c_name.setText(list.get(position));*/

        Course course = list.get(position);

        holder.c_name.setText(course.getC_name());
        holder.section.setText(course.getSection());
    }

    @Override
    public int getItemCount() {
        return list.size(); //the number of items to be returned :3
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView iv;
    TextView c_name,section;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.defaultImage);
            c_name = itemView.findViewById(R.id.c_name);
            section = itemView.findViewById(R.id.c_code);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Course course = list.get(getAdapterPosition());
            Toast.makeText(view.getContext(), course.getC_name(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mCtx,AttendanceUpdateActivity.class);
            intent.putExtra("c_name",course.getC_name());
            intent.putExtra("sec_name",course.getSection());
            intent.putExtra("s_id",AttendanceActivity.extraID);
            mCtx.startActivity(intent);

        }
    }
}

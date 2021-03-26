package com.example.alphademo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<datamodel> data;
    public myadapter(ArrayList<datamodel> data) {
        this.data = data;
    }



    @NonNull
    @Override
    public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {

        holder.image.setImageResource(data.get(position).getImage());
        holder.header.setText(data.get(position).getHeader());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView header;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.picture);
            header = itemView.findViewById(R.id.header);
        }
    }
}







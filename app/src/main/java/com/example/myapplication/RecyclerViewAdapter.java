package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    LayoutInflater inflator;
    private ArrayList<TripInfoClass> mDriverNames = new ArrayList<>();
    //private Context mContext;

    public RecyclerViewAdapter( Context context, ArrayList<TripInfoClass> driverNames){
        mDriverNames = driverNames;
        inflator= LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drivers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHOlder: called.");

        holder.driverName.setText(mDriverNames.get(position).getDriverName());

    }

    @Override
    public int getItemCount() {
        return mDriverNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView driverName;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driver);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

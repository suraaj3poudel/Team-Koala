package com.example.alphademo.views.triplist;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphademo.R;
import com.example.alphademo.database.TripInfo;

import java.util.ArrayList;

public class RecyclerViewTrip extends RecyclerView.Adapter<RecyclerViewTrip.ViewHolder>{

    //LayoutInflater inflator;
    private ArrayList<TripInfo> mTrips = new ArrayList<>();
    View view2;


    public RecyclerViewTrip( Context context, ArrayList<TripInfo> tripInfo){
        mTrips = tripInfo;
        //inflator= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tripinfo,parent,false);
        view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2,parent,false);
        return new ViewHolder(view);
    }

    public void onClickAction(){

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.i("IDtag: ",mTrips.get(position).getNumberSites()+"");

        holder.header.setText(mTrips.get(0).getId());
        holder.num2.setText(mTrips.get(0).getNumberSites()+"");
        holder.num1.setText(mTrips.get(0).getNumberSources()+"");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view2.getContext();
                Fragment fragment = new Trip_listFragment();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment,"TripList");
                transaction.addToBackStack("TripList");
                transaction.commit();
            }
        });

//        holder.navi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //holder.showMessage("Important!","Coming Soon");
//                AppCompatActivity activity = (AppCompatActivity) mapFrag.getContext();
//                Fragment fragment = new MapFragmentTemp();
//                FragmentManager manager = activity.getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.fragment_container,fragment);
//                Bundle args = new Bundle();
//                args.putDouble("d1", mSourceInfo.get(pos).getLatitude());
//                args.putDouble("d2", mSourceInfo.get(pos).getLongitude());
//                args.putString("Message",mSourceInfo.get(position).getSource()+"\n"+mSourceInfo.get(position).getSourceAddress()+"\n"+mSourceInfo.get(position).getSourceCity());
//                Log.i("Long Sent", mSourceInfo.get(pos).getLatitude()+"");
//                fragment.setArguments(args);
//                transaction.commit();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView num1,num2,header;
        LinearLayout tripcard;
        CardView card;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.tripNum);
            num1 = itemView.findViewById(R.id.numSources);
            num2 = itemView.findViewById(R.id.numSites);
            //tripcard = itemView.findViewById(R.id.tripCard);
            card = itemView.findViewById(R.id.card_viewTrips);
        }


        private void showMessage(String title, String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);

            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

}

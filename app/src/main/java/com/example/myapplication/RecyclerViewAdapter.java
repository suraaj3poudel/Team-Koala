package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    LayoutInflater inflator;
    private ArrayList<TripInfoClass> mDriverNames = new ArrayList<>();
    private final View.OnClickListener mOnClickListener = new MyOnClickListener();
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

        holder.driverName.setText(mDriverNames.get(position).getDriverName());
        holder.driverCode.setText(mDriverNames.get(position).getDriverCode());
        holder.truckid.setText(mDriverNames.get(position).getTruckID());
        holder.transport.setText(mDriverNames.get(position).getTransport());
        holder.tripid.setText(mDriverNames.get(position).getTripId());
        holder.tripname.setText(mDriverNames.get(position).getTripName());

        holder.sourcename.setText(mDriverNames.get(position).getSource());
        holder.sourcecode.setText(mDriverNames.get(position).getSourceCode());
        holder.address.setText(mDriverNames.get(position).getSourceAddress());
        holder.scity.setText(mDriverNames.get(position).getSourceCity());
        holder.sstate.setText(mDriverNames.get(position).getSourceState());
        holder.szipcode.setText(mDriverNames.get(position).getSourceZIP());

        holder.site.setText(mDriverNames.get(position).getSite());
        holder.sitecode.setText(mDriverNames.get(position).getSiteCode());
        holder.siaddress.setText(mDriverNames.get(position).getSiteAddress());
        holder.sicity.setText(mDriverNames.get(position).getSiteCity());
        holder.sistate.setText(mDriverNames.get(position).getSiteState());
        holder.sizip.setText(mDriverNames.get(position).getSiteZIP());







    }

    @Override
    public int getItemCount() {
        return mDriverNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView driverName, driverCode, truckid, transport, tripid, tripname, sourcename, sourcecode;
        TextView address, scity, sstate, szipcode, site, sitecode, siaddress, sicity, sistate, sizip;
        TextView sourcenote1, sourcenote2, sourcenote3;
        ProgressBar progressBar;
        LinearLayout parentLayout;
        Button addsourceNotes, siteNotes1, siteNotes2;
        EditText typeSpaceSource, typeSpaceSite1, typeSpaceSite2;
        DatabaseSQLite myDB;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar =itemView.findViewById(R.id.progressBar2);
            driverName = itemView.findViewById(R.id.driver);
            driverCode = itemView.findViewById(R.id.driverCode);
            truckid =  itemView.findViewById(R.id.truckID);
            transport = itemView.findViewById(R.id.truckDes);
            tripid = itemView.findViewById(R.id.tripID);
            tripname = itemView.findViewById(R.id.tripName);
            sourcename = itemView.findViewById(R.id.source);
            sourcecode = itemView.findViewById(R.id.sourceCode);
            address = itemView.findViewById(R.id.address);
            scity = itemView.findViewById(R.id.city);
            sstate = itemView.findViewById(R.id.state);
            szipcode = itemView.findViewById(R.id.zipcode);

            site = itemView.findViewById(R.id.site);
            sitecode = itemView.findViewById(R.id.siteCode);
            siaddress = itemView.findViewById(R.id.siteAddress);
            sicity = itemView.findViewById(R.id.siteCity);
            sistate = itemView.findViewById(R.id.siteState);
            sizip = itemView.findViewById(R.id.siteZipcode);

            parentLayout = itemView.findViewById(R.id.parent_layout);

            myDB = new DatabaseSQLite(itemView.getContext());

            addsourceNotes = itemView.findViewById(R.id.sourceNotes);

            typeSpaceSource = itemView.findViewById(R.id.typeSpaceSource);
            typeSpaceSite1 = itemView.findViewById(R.id.typeSpaceSite1);
            typeSpaceSite2 = itemView.findViewById(R.id.typeSpaceSite2);
            siteNotes1 = itemView.findViewById(R.id.siteNotes1);
            siteNotes2 = itemView.findViewById(R.id.siteNotes2);

            typeSpaceSource.setVisibility(View.GONE);
            typeSpaceSite1.setVisibility(View.GONE);
            typeSpaceSite2.setVisibility(View.GONE);

            sourcenote1 = itemView.findViewById(R.id.sourcenotes1);
            sourcenote2 = itemView.findViewById(R.id.sourcenotes2);
            sourcenote3 = itemView.findViewById(R.id.sourcenotes3);




            addsourceNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = driverCode.getText().toString().trim()+tripid.getText().toString().trim()+sourcecode.getText().toString().trim();

                    if(typeSpaceSource.getVisibility() == View.VISIBLE){
                        addsourceNotes.setText("+ Add Notes");
                        typeSpaceSource.setVisibility(View.GONE);
                        //boolean isInserted =  myDB.addData(id,
                                //typeSpaceSource.getText().toString());

                        boolean isInserted =  myDB.addData(id,
                                "Your Notes Here");
                        Toast.makeText(v.getContext(), myDB.addData(id,
                                "Your Notes Here") +"", Toast.LENGTH_SHORT).show();

                        if(isInserted){
                           // Toast.makeText(v.getContext(), myDB.getNotes(id).getString(1)+ " Hi!" , Toast.LENGTH_SHORT).show();
                            String toShow = "Notes: \n";
                            //toShow += myDB.getNotes(id).getString(1);
                            sourcenote1.setText(toShow);

                        }


                    }

                    else{
                        addsourceNotes.setText("Done");
                        typeSpaceSource.setVisibility(View.VISIBLE);

                       // if(myDB.getNotes(id) !=  null) {
                        //    typeSpaceSource.setText(myDB.getNotes(id).getString(1));
                       // }
                       // else{
                            typeSpaceSource.setText("Hi");
                       // }
                    }
                }
            });

            siteNotes1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(typeSpaceSite1.getVisibility() == View.VISIBLE){
                        siteNotes1.setText("+ Add Notes");
                        typeSpaceSite1.setVisibility(View.GONE);
                    }
                    else{
                        siteNotes1.setText("Done");
                        typeSpaceSite1.setVisibility(View.VISIBLE);

                    }
                }
            });

            siteNotes2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(typeSpaceSite2.getVisibility() == View.VISIBLE){
                        siteNotes2.setText("+ Add Notes");
                        typeSpaceSite2.setVisibility(View.GONE);
                    }
                    else{
                        siteNotes2.setText("Done");
                        typeSpaceSite2.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}

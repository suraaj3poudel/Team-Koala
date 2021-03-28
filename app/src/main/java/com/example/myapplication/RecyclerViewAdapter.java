package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

        holder.site2name.setText(mDriverNames.get(position).getSite2());
        holder.site2containercode.setText(mDriverNames.get(position).getSite2Code());
        holder.site2address.setText(mDriverNames.get(position).getSite2Address());
        holder.site2city.setText(mDriverNames.get(position).getSite2City());
        holder.site2state.setText(mDriverNames.get(position).getSite2State());
        holder.site2zip.setText(mDriverNames.get(position).getSite2ZIP());
        holder.site2p.setText(mDriverNames.get(position).getSite2Product());
        holder.site2pd.setText(mDriverNames.get(position).getSite2ProductDesc());







    }

    @Override
    public int getItemCount() {
        return mDriverNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView driverName, driverCode, truckid, transport, tripid, tripname, sourcename, sourcecode;
        TextView address, scity, sstate, szipcode, site, sitecode, siaddress, sicity, sistate, sizip;
        TextView sourcenote1, sourcenote2, sourcenote3;
        TextView site2name, site2containercode, site2address, site2city, site2state, site2zip,site2p, site2pd;
        ProgressBar progressBar;
        LinearLayout parentLayout;
        Button addsourceNotes, siteNotes1, siteNotes2;
        EditText typeSpaceSource, typeSpaceSite1, typeSpaceSite2;
        DatabaseSQLite myDB;




        public ViewHolder(@NonNull final View itemView) {
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

            site2name = itemView.findViewById(R.id.site1);
            site2containercode = itemView.findViewById(R.id.siteCode1);
            site2address = itemView.findViewById(R.id.siteAddress1);
            site2city = itemView.findViewById(R.id.siteCity1);
            site2state = itemView.findViewById(R.id.siteState1);
            site2zip = itemView.findViewById(R.id.siteZipcode1);
            site2p = itemView.findViewById(R.id.productCode1);
            site2pd = itemView.findViewById(R.id.productDesc1);

            parentLayout = itemView.findViewById(R.id.parent_layout);

            myDB = new DatabaseSQLite(itemView.getContext());

            addsourceNotes = itemView.findViewById(R.id.sourceNotes);

            typeSpaceSource = itemView.findViewById(R.id.typeSpaceSource);
            typeSpaceSite1 = itemView.findViewById(R.id.typeSpaceSite1);
            typeSpaceSite2 = itemView.findViewById(R.id.typeSpaceSite2);
            siteNotes1 = itemView.findViewById(R.id.siteNotes1);
            siteNotes2 = itemView.findViewById(R.id.siteNotes2);

            site2name = itemView.findViewById(R.id.site1);
            site2containercode = itemView.findViewById(R.id.siteCode1);


            typeSpaceSource.setVisibility(View.GONE);
            typeSpaceSite1.setVisibility(View.GONE);
            typeSpaceSite2.setVisibility(View.GONE);

            sourcenote1 = itemView.findViewById(R.id.sourcenotes1);
            sourcenote2 = itemView.findViewById(R.id.sourcenotes2);
            sourcenote3 = itemView.findViewById(R.id.sourcenotes3);

            final String sourceID = driverCode.getText().toString().trim()+tripid.getText().toString().trim()+sourcecode.getText().toString().trim();
            final String siteID1 = driverCode.getText().toString().trim()+tripid.getText().toString().trim()+sitecode.getText().toString().trim();
            final String siteID2 = driverCode.getText().toString().trim()+tripid.getText().toString().trim()+site2containercode.getText().toString().trim();
            final String[] sourceNotes = {myDB.getNotes(sourceID),myDB.getNotes(siteID1),myDB.getNotes(siteID2)};

            if(!sourceNotes[0].equals("")) {
                String toShow = "Notes: \n";
                toShow += sourceNotes[0];
                sourcenote1.setText(Html.fromHtml("<b>Notes: </b> <p> " +sourceNotes[0] + "</p>"));
            }
            else{
                sourcenote1.setVisibility(View.GONE);
            }

            if(!sourceNotes[1].equals("")) {
                String toShow = "Notes: \n";
                toShow += sourceNotes[1];
                sourcenote2.setText(Html.fromHtml("<b>Notes: </b> <p> " +sourceNotes[1] + "</p>"));
            }
            else{
                sourcenote2.setVisibility(View.GONE);
            }

            if(!sourceNotes[2].equals("")) {
                String toShow = "Notes: \n";
                toShow += sourceNotes[2];
                sourcenote3.setText(Html.fromHtml("<b>Notes: </b> <p> " +sourceNotes[2] + "</p>"));
            }
            else{
                sourcenote3.setVisibility(View.GONE);
            }




            addsourceNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notes;
                    //String oldnote = "EMPTY";
                    if(typeSpaceSource.getVisibility() == View.VISIBLE){

                        addsourceNotes.setText("+ Add Notes");
                        typeSpaceSource.setVisibility(View.GONE);
                        sourcenote1.setVisibility(View.VISIBLE);
                        String type = typeSpaceSource.getText().toString();
                        if(!myDB.getNotes(sourceID).equals("")){
                            myDB.updateNotes(sourceID, type);
                        }
                        else {
                            boolean isInserted = myDB.addData(sourceID,
                                    type);
                        }
                        String text = myDB.getNotes(sourceID);
                        if(!text.equals("")) {
                            String toShow = text;
                            sourcenote1.setText(Html.fromHtml("<b>Notes: </b> <p> " + toShow + "</p>"));
                        }
                    }

                    else{

                        addsourceNotes.setText("Done");
                        typeSpaceSource.setVisibility(View.VISIBLE);
                        sourcenote1.setVisibility(View.GONE);
                        notes= myDB.getNotes(sourceID);
                        if(notes !=  null) {
                            typeSpaceSource.setText(notes);
                       }
                       else{
                            typeSpaceSource.setHint("Your Notes..");
                       }
                    }
                }
            });

            siteNotes1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notes;
                    //String oldnote = "EMPTY";

                    if(typeSpaceSite1.getVisibility() == View.VISIBLE){

                        siteNotes1.setText("+ Add Notes");
                        typeSpaceSite1.setVisibility(View.GONE);
                        sourcenote2.setVisibility(View.VISIBLE);
                        String type = typeSpaceSite1.getText().toString();
                        if(!myDB.getNotes(siteID1).equals("")){
                            myDB.updateNotes(siteID1, type);
                        }
                        else {
                            boolean isInserted = myDB.addData(siteID1,
                                    type);
                            Log.i(" Status", ""+isInserted);
                        }
                        String text = myDB.getNotes(siteID1);
                        if(!text.equals("")) {
                            String toShow = text;
                            sourcenote2.setText(Html.fromHtml("<b>Notes: </b> <p> " + toShow + "</p>"));
                        }
                    }

                    else{

                        siteNotes1.setText("Done");
                        typeSpaceSite1.setVisibility(View.VISIBLE);
                        sourcenote2.setVisibility(View.GONE);
                        notes= myDB.getNotes(siteID1);

                        Log.i("INFO: ", siteID1 +" "+ sourceID);
                        if(notes !=  null) {
                            typeSpaceSite1.setText(notes);
                        }
                        else{
                            typeSpaceSite1.setHint("Your Notes..");
                        }
                    }
                }
            });

            siteNotes2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notes;
                    //String oldnote = "EMPTY";

                    if(typeSpaceSite2.getVisibility() == View.VISIBLE){

                        siteNotes2.setText("+ Add Notes");
                        typeSpaceSite2.setVisibility(View.GONE);
                        sourcenote3.setVisibility(View.VISIBLE);
                        String type = typeSpaceSite2.getText().toString();
                        if(!myDB.getNotes(siteID2).equals("")){
                            myDB.updateNotes(siteID2, type);
                        }
                        else {
                            boolean isInserted = myDB.addData(siteID2,
                                    type);
                            Log.i(" Status", ""+isInserted);
                        }
                        String text = myDB.getNotes(siteID2);
                        if(!text.equals("")) {
                            //String toShow = "Notes: \n";
                            String toShow = text;
                            sourcenote3.setText(Html.fromHtml("<b>Notes: </b> <p> " + toShow + "</p>"));
                        }
                    }

                    else{

                        siteNotes2.setText("Done");
                        typeSpaceSite2.setVisibility(View.VISIBLE);
                        sourcenote3.setVisibility(View.GONE);
                        notes= myDB.getNotes(siteID2);

                        Log.i("INFO: ", siteID2 +" "+ sourceID);
                        if(notes !=  null) {
                            typeSpaceSite2.setText(notes);
                        }
                        else{
                            typeSpaceSite2.setHint("Your Notes..");
                        }
                    }

                }
            });
        }

        public void hideKeyboard(Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}

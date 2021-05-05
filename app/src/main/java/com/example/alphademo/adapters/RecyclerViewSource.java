package com.example.alphademo.adapters;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alphademo.MapTemp;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseSQLite;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.forms.SourceForm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

public class RecyclerViewSource extends RecyclerView.Adapter<RecyclerViewSource.ViewHolder>{

    LayoutInflater inflator;
    private ArrayList<SourceObject> mSourceInfo = new ArrayList<>();
    String message ="";
    int pos;
    View mapFrag;
    Context context;
    SharedPreferences.Editor editor;
    public static final String MapPrefs = "MapPrefs" ;
    SharedPreferences sharedpreferences,mapi;
    public static final String MyPREFERENCE = "TRIP_STATUS" ;

    public RecyclerViewSource( Context context, ArrayList<SourceObject> sourceInfo){
        mSourceInfo = sourceInfo;
        inflator= LayoutInflater.from(context);
        this.context=context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        mapi = context.getSharedPreferences(MapPrefs, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sourceinfo,parent,false);
        mapFrag = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2,parent,false);

        return new ViewHolder(view);
    }

    public String getLocation(){
        String latLon = "0 0";
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                int speeds = (int) Math.floor(locationGPS.getSpeed());
                //speed.setText(speeds+"");
                String latitude = String.valueOf(lat);
                String longitude = String.valueOf(longi);
                latLon = latitude+" "+longitude;

            } else {
                Toast.makeText(context, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
        return latLon;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String location = getLocation();
        final String[] latLon = location.split(" ");

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.moreinfo);
                TextView scc = (TextView) dialog.findViewById(R.id.siteContainerCode);
                TextView scd = (TextView) dialog.findViewById(R.id.siteContaienrDesc);
                TextView drn = (TextView) dialog.findViewById(R.id.deliveryReqNo);
                TextView pid= (TextView) dialog.findViewById(R.id.productID);
                TextView fill = (TextView) dialog.findViewById(R.id.fill);
                scc.setText(mSourceInfo.get(position).getScc());
                scd.setText(mSourceInfo.get(position).getScd());
                drn.setText(mSourceInfo.get(position).getDrn());
                pid.setText(mSourceInfo.get(position).getPid());
                fill.setText(mSourceInfo.get(position).getFillInfo());



                Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(true);

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });


        pos = position;

        holder.sourcename.setText(mSourceInfo.get(position).getSource().trim());
        holder.sourceT.setText(mSourceInfo.get(position).getSource().trim());

        holder.sourcecode.setText(mSourceInfo.get(position).getSourceCode());
        holder.address.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity().trim()+", "+mSourceInfo.get(position).getSourceState().trim());
        holder.addressT.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity().trim()+", "+mSourceInfo.get(position).getSourceState().trim());
        holder.szipcode.setText(mSourceInfo.get(position).getSourceZIP());
        holder.site2p.setText(mSourceInfo.get(position).getSourceProduct());
        holder.site2pd.setText(mSourceInfo.get(position).getFuelType());
        holder.rq.setText(mSourceInfo.get(position).getQuantity() == null ? "N/A": mSourceInfo.get(position).getQuantity());
        String check = mSourceInfo.get(position).getFuelType()==null ? "N/A" :  mSourceInfo.get(position).getFuelType().trim() ;
        if((check.charAt(check.length()-1)+"").equals("."))
            check = check.substring(0,check.length()-2).trim();
        holder.ft.setText(check);

        //final String sourceID = holder.sourcecode.getText().toString().trim()+position;

        final String sourceID = holder.sourcecode.getText().toString().trim();
        final Intent intent= new Intent(context, SourceForm.class);
        intent.putExtra("fuelType",mSourceInfo.get(position).getFuelType());
        intent.putExtra("id",sourceID);

        holder.sourceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
            }
        });

        final String sourceNotes =holder.myDB.getNotes(sourceID);

        if (!sourceNotes.equals("")) {
            String toShow = "Notes: \n";
            toShow += sourceNotes;
            holder.sourcenote1.setText(Html.fromHtml("<b>Notes: </b> <p> " + sourceNotes + "</p>"));
        } else {
            holder.sourcenote1.setVisibility(View.GONE);
        }


        holder.addsourceNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes;

                if (holder.typeSpaceSource.getVisibility() == View.VISIBLE) {

                    holder.addsourceNotes.setText("+ Add Notes");
                    holder.typeSpaceSource.setVisibility(View.GONE);
                    holder.sourcenote1.setVisibility(View.VISIBLE);
                    String type = holder.typeSpaceSource.getText().toString();
                    if (!holder.myDB.getNotes(sourceID).equals("")) {
                        Log.i("Updating: ", sourceID + " " + type, null);
                        holder.myDB.updateNotes(sourceID, type);
                    } else {
                        Log.i("Adding: ", sourceID + " " + type, null);
                        boolean isInserted = holder.myDB.addData(sourceID,
                                type);
                    }
                    String text = holder.myDB.getNotes(sourceID);
                    if (!text.equals("")) {
                        String toShow = text;
                        holder.sourcenote1.setText(Html.fromHtml("<b>Notes: </b> <p> " + toShow + "</p>"));
                    }
                } else {
                    holder.addsourceNotes.setText("Done");
                    holder.typeSpaceSource.setVisibility(View.VISIBLE);
                    holder.sourcenote1.setVisibility(View.GONE);
                    notes = holder.myDB.getNotes(sourceID);
                    if (notes != null) {
                        holder.typeSpaceSource.setText(notes);
                    } else {
                        holder.typeSpaceSource.setHint("Your Notes..");
                    }
                }
            }
        });

        if(sharedpreferences.getString("status"+sourceID,"not_complete").equals("complete")) {
            holder.complete.setVisibility(View.VISIBLE);
            holder.card.setBackgroundColor(Color.GRAY);
            holder.hidden_layout.setBackgroundColor(Color.GRAY);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               holder.hidden_layout.setVisibility(View.VISIBLE);
               holder.show_layout.setVisibility(View.GONE);
            }
        });

        holder.arrowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.hidden_layout.setVisibility(View.GONE);
                holder.show_layout.setVisibility(View.VISIBLE);
            }
        });

        final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return true;
            }

        };

        holder.navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = mapFrag.findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);
                AppCompatActivity activity = (AppCompatActivity) mapFrag.getContext();
                Fragment fragment = null;
                fragment = new MapTemp();

                FragmentManager manager = activity.getSupportFragmentManager();

                final FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragment_container,fragment,"map");
                //transaction.replace(R.id.fragment_container,fragment);
                Bundle args = new Bundle();

                editor = mapi.edit();
                editor.putString("d1", mSourceInfo.get(pos).getLatitude()+"").apply();
                editor.putString("d2", mSourceInfo.get(pos).getLongitude()+"").apply();
                editor.putString("lat", latLon[0]).apply();
                editor.putString("lon", latLon[1]).apply();
                Log.i("HGTY",latLon[0]+ " "+latLon[1]+" "+mSourceInfo.get(pos).getLatitude()+" "+mSourceInfo.get(pos).getLongitude());
                editor.commit();
                args.putDouble("d1", mSourceInfo.get(pos).getLatitude());
                args.putDouble("d2", mSourceInfo.get(pos).getLongitude());
                args.putString("Message",mSourceInfo.get(position).getSource()+"\n"+mSourceInfo.get(position).getSourceAddress()+"\n"+mSourceInfo.get(position).getSourceCity());
                Log.i("Long Sent", mSourceInfo.get(pos).getLatitude()+"");
                fragment.setArguments(args);
                transaction.commit();


                //holder.showMessage("Important!","Coming Soon");

            }
        });



    }

    @Override
    public int getItemCount() {
        return mSourceInfo.size();
    }

    LocationManager locationManager;


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sourcename, sourcecode;
        TextView address,szipcode;
        TextView sourcenote1,sourceT,addressT;
        LinearLayout hidden_layout,show_layout,complete,card;
        ImageView arrowdown,arrowup;
        Button addsourceNotes, sourceForm;
        EditText typeSpaceSource;
        DatabaseSQLite myDB;
        ExtendedFloatingActionButton navi;
        TextView ft, rq,site2p,site2pd;
        ImageView moreInfo;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            complete = itemView.findViewById(R.id.complete);
            navi = itemView.findViewById(R.id.navSource);
            site2p = itemView.findViewById(R.id.productCode1);
            site2pd = itemView.findViewById(R.id.productDesc1);
            moreInfo = itemView.findViewById(R.id.moreinfo);
            ft = itemView.findViewById(R.id.productDesc);
            rq = itemView.findViewById(R.id.productQty);
            card= itemView.findViewById(R.id.card_viewSource);
//            progressBar = itemView.findViewById(R.id.spin_kit1);
            sourcename = itemView.findViewById(R.id.source);
            sourcecode = itemView.findViewById(R.id.sourceCode);
            address = itemView.findViewById(R.id.address);
            szipcode = itemView.findViewById(R.id.zipcode);
            arrowdown = itemView.findViewById(R.id.arrowdown);
            show_layout=itemView.findViewById(R.id.show_layout);
            arrowup = itemView.findViewById(R.id.arrowup);

            sourceT = itemView.findViewById(R.id.sourceT);

            addressT = itemView.findViewById(R.id.addressT);

            sourceForm = itemView.findViewById(R.id.sourceForm);

            hidden_layout = itemView.findViewById(R.id.hidden_layout);
            show_layout = itemView.findViewById(R.id.show_layout);

            myDB = new DatabaseSQLite(itemView.getContext());

            addsourceNotes = itemView.findViewById(R.id.sourceNotes);

            typeSpaceSource = itemView.findViewById(R.id.typeSpaceSource);

            typeSpaceSource.setVisibility(View.GONE);

            sourcenote1 = itemView.findViewById(R.id.sourcenotes1);


        }

    }

}

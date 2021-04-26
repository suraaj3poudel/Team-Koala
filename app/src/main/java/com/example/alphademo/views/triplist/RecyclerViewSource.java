package com.example.alphademo.views.triplist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphademo.MapFragmentTemp;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseSQLite;
import com.example.alphademo.database.SourceObject;

import java.util.ArrayList;

public class RecyclerViewSource extends RecyclerView.Adapter<RecyclerViewSource.ViewHolder>{

    LayoutInflater inflator;
    private ArrayList<SourceObject> mSourceInfo = new ArrayList<>();
    String message ="";
    int pos;
    View mapFrag;
    Context v;

    public RecyclerViewSource( Context context, ArrayList<SourceObject> sourceInfo){
        mSourceInfo = sourceInfo;
        inflator= LayoutInflater.from(context);
        v = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sourceinfo,parent,false);
        mapFrag = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2,parent,false);
        return new ViewHolder(view);
    }

    public void onClickAction(){

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.sourceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new SiteDeliveryFormFragment();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.commit();

                //Intent intent = new Intent(view.getContext(), MainActivity4.class);
                //view.getContext().startActivity(intent);
            }
        });


        pos = position;
        holder.sourcename.setText(mSourceInfo.get(position).getSource());
        holder.sourceT.setText(mSourceInfo.get(position).getSource());

        holder.sourcecode.setText(mSourceInfo.get(position).getSourceCode());
        holder.address.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity()+", "+mSourceInfo.get(position).getSourceState());
        holder.addressT.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity()+", "+mSourceInfo.get(position).getSourceState());
        holder.szipcode.setText(mSourceInfo.get(position).getSourceZIP());

        message = "SourceData\n\n"+mSourceInfo.get(position).getSource()+"\n"+mSourceInfo.get(position).getSourceAddress()
                +"\n"+mSourceInfo.get(position).getSourceCity()+"\n"+mSourceInfo.get(position).getLatitude()+"\n"+mSourceInfo.get(position).getLongitude();

        final String sourceID = holder.sourcecode.getText().toString().trim()+position;

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
                //sourceID = driverCode.getText().toString().trim()+tripid.getText().toString().trim()+sourcecode.getText().toString().trim();

                //String oldnote = "EMPTY";
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





        holder.arrowdown.setOnClickListener(new View.OnClickListener() {
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

        holder.navicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.showMessage("Important!","Coming Soon");
                AppCompatActivity activity = (AppCompatActivity) mapFrag.getContext();
                Fragment fragment = new MapFragmentTemp();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                Bundle args = new Bundle();
                args.putDouble("d1", mSourceInfo.get(pos).getLatitude());
                args.putDouble("d2", mSourceInfo.get(pos).getLongitude());
                args.putString("Message",mSourceInfo.get(position).getSource()+"\n"+mSourceInfo.get(position).getSourceAddress()+"\n"+mSourceInfo.get(position).getSourceCity());
                Log.i("Long Sent", mSourceInfo.get(pos).getLatitude()+"");
                fragment.setArguments(args);
                transaction.commit();
            }
        });

        holder.navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.showMessage("Important!","Coming Soon");
                AppCompatActivity activity = (AppCompatActivity) mapFrag.getContext();
                Fragment fragment = new MapFragmentTemp();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                Bundle args = new Bundle();
                args.putDouble("d1", mSourceInfo.get(pos).getLatitude());
                args.putDouble("d2", mSourceInfo.get(pos).getLongitude());
                args.putString("Message",mSourceInfo.get(position).getSource()+"\n"+mSourceInfo.get(position).getSourceAddress()+"\n"+mSourceInfo.get(position).getSourceCity());
                Log.i("Long Sent", mSourceInfo.get(pos).getLatitude()+"");
                fragment.setArguments(args);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSourceInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sourcename, sourcecode;
        TextView address,szipcode;
        TextView sourcenote1,sourceT,addressT;
        ProgressBar progressBar;
        LinearLayout hidden_layout,show_layout;
        ImageView arrowdown,arrowup;
        Button addsourceNotes, sourceForm;
        EditText typeSpaceSource;
        DatabaseSQLite myDB;
        Button navi;
        ImageView navicon;




        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            navi = itemView.findViewById(R.id.navSource);
            navicon = itemView.findViewById(R.id.navicon);
            progressBar = itemView.findViewById(R.id.progressBar2);
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

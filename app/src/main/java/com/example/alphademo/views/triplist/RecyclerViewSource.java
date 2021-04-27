package com.example.alphademo.views.triplist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphademo.MapTemp;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseSQLite;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.dummy.SourceForm;
import com.example.alphademo.dummy.MainActivity3;
import com.example.alphademo.views.setting.SettingFragment;
import com.github.ybq.android.spinkit.SpinKitView;
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

    public RecyclerViewSource( Context context, ArrayList<SourceObject> sourceInfo){
        mSourceInfo = sourceInfo;
        inflator= LayoutInflater.from(context);
        this.context=context;
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
        final Intent intent= new Intent(context, SourceForm.class);
        intent.putExtra("fuelType",mSourceInfo.get(position).getFuelType());
        //myDialogue.setContentView(R.layout.delivery_form);
        //DisplayMetrics dm = new DisplayMetrics();
        //dm = context.getResources().getDisplayMetrics();

        //int height = dm.heightPixels;
        //int width = dm.widthPixels;

        // myDialogue.getWindow().setLayout((int) (width * .9),(int) (height*.9));

        holder.sourceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
            }
        });

        //holder.pbar1.setVisibility(View.INVISIBLE);

        pos = position;

        holder.sourcename.setText(mSourceInfo.get(position).getSource());
        holder.sourceT.setText(mSourceInfo.get(position).getSource());

        holder.sourcecode.setText(mSourceInfo.get(position).getSourceCode());
        holder.address.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity()+", "+mSourceInfo.get(position).getSourceState());
        holder.addressT.setText(mSourceInfo.get(position).getSourceAddress()+", "+mSourceInfo.get(position).getSourceCity()+", "+mSourceInfo.get(position).getSourceState());
        holder.szipcode.setText(mSourceInfo.get(position).getSourceZIP());
        //holder.progressBar.setVisibility(View.INVISIBLE);

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
                Fragment fragment = new MapTemp();
                FragmentManager manager = activity.getSupportFragmentManager();

                final FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                Bundle args = new Bundle();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        SpinKitView pbar1,pbar2;
        TextView sourcename, sourcecode;
        TextView address,szipcode;
        TextView sourcenote1,sourceT,addressT;
        //SpinKitView progressBar;
        LinearLayout hidden_layout,show_layout;
        ImageView arrowdown,arrowup;
        Button addsourceNotes, sourceForm;
        EditText typeSpaceSource;
        DatabaseSQLite myDB;
        ExtendedFloatingActionButton navi;
        CardView card;




        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            navi = itemView.findViewById(R.id.navSource);
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

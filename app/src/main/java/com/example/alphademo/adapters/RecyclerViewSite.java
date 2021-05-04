package com.example.alphademo.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphademo.MainActivity;
import com.example.alphademo.MainActivity2;
import com.example.alphademo.MapTemp;
import com.example.alphademo.database.DatabaseSQLite;
import com.example.alphademo.R;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.forms.DeliveryForm;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class RecyclerViewSite extends RecyclerView.Adapter<RecyclerViewSite.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    LayoutInflater inflator;
    private ArrayList<SiteObject> mSiteInfo = new ArrayList<>();
    View mapFrag,more;
    Context context,how;
    ViewGroup parents;
    BlurMaskFilter blurMaskFilter;

    public RecyclerViewSite( Context contexts, ArrayList<SiteObject> siteInfo){
        mSiteInfo = siteInfo;
        inflator= LayoutInflater.from(contexts);
        this.context = contexts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_siteinfo,parent,false);
        mapFrag = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2,parent,false);
        parents = parent;
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //final Dialog myDialogue = new Dialog(context);
        final Intent intent= new Intent(context, DeliveryForm.class);
        intent.putExtra("fuelType",mSiteInfo.get(position).getSiteProductDesc());

        //myDialogue.setContentView(R.layout.delivery_form);
        //DisplayMetrics dm = new DisplayMetrics();
        //dm = context.getResources().getDisplayMetrics();

        //int height = dm.heightPixels;
        //int width = dm.widthPixels;

       // myDialogue.getWindow().setLayout((int) (width * .9),(int) (height*.9));

        holder.deliverForm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
                //myDialogue.show();
//                startActivity(new Intent(view.getContext(), DeliveryForm.class));
//                Intent intent = new Intent(view.getContext(), MainActivity4.class);
//                view.getContext().startActivity(intent);
            }
        });


        //notification.setOnClickListener(this);
        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LayoutInflater inflater = (LayoutInflater) parents(Context.LAYOUT_INFLATER_SERVICE);

                //PopupWindow pw = new PopupWindow(LayoutInflater.from(parents.getContext()).inflate(R.layout.moreinfo, null, false),1500,1500, true);

                //pw.showAtLocation(parents, Gravity.CENTER, 0, 0);








//                PopupWindow menuPopup = null;
//                View menuView= parents.getLayoutInflater().inflate(R.layout.moreinfo, null);
//                menuPopup.setContentView(R.layout.moreinfo);
//                menuPopup=new PopupWindow(menuView, 200, 200, false);
//                menuPopup.showAtLocation(menuView, Gravity.TOP | Gravity.RIGHT, 0, 100);

                final Dialog dialog = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.moreinfo);
                //dialog.getWindow().setDimAmount(100);
                TextView scc = (TextView) dialog.findViewById(R.id.siteContainerCode);
                TextView scd = (TextView) dialog.findViewById(R.id.siteContaienrDesc);
                TextView drn = (TextView) dialog.findViewById(R.id.deliveryReqNo);
                TextView pid= (TextView) dialog.findViewById(R.id.productID);
                TextView fill = (TextView) dialog.findViewById(R.id.fill);
                scc.setText(mSiteInfo.get(position).getScc());
                scd.setText(mSiteInfo.get(position).getScd());
                drn.setText(mSiteInfo.get(position).getDrn());
                pid.setText(mSiteInfo.get(position).getPid());
                fill.setText(mSiteInfo.get(position).getFillInfo());
               // holder.showMessage("", "");
                //holder.scc.setText("Test");



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

                //Bitmap map=takeScreenShot(MainActivity.ge);

                //Bitmap fast=fastblur(map, 10);
                //final Drawable draw=new BitmapDrawable(parents.getResources(),fast);
                dialog.setCanceledOnTouchOutside(true);

                dialog.show();
                dialog.getWindow().setAttributes(lp);
                //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            }
        });

        holder.site.setText(mSiteInfo.get(position).getSite());
        holder.sitet.setText(mSiteInfo.get(position).getSite());
        holder.sitecode.setText(mSiteInfo.get(position).getSiteCode());
        holder.siaddress.setText(mSiteInfo.get(position).getSiteAddress()+", "+mSiteInfo.get(position).getSiteCity().trim()+", "+mSiteInfo.get(position).getSiteState());
        holder.siteaddresst.setText(mSiteInfo.get(position).getSiteAddress()+", "+mSiteInfo.get(position).getSiteCity().trim()+", "+mSiteInfo.get(position).getSiteState());
        holder.sizip.setText(mSiteInfo.get(position).getSiteZIP());
        holder.site2p.setText(mSiteInfo.get(position).getSiteProduct());
        holder.site2pd.setText(mSiteInfo.get(position).getSiteProductDesc());
        holder.rq.setText(mSiteInfo.get(position).getQuantity());
        String check = mSiteInfo.get(position).getSiteProductDesc().trim();
        if((check.charAt(check.length()-1)+"").equals("."))
                check = check.substring(0,check.length()-2).trim();
        holder.ft.setText(check);

        //message = "SiteData\n\n"+mSiteInfo.get(position).getSite()+"\n"+mSiteInfo.get(position).getSiteAddress()
                //+"\n"+mSiteInfo.get(position).getSiteCity()+"\n"+mSiteInfo.get(position).getLatitude()+"\n"+mSiteInfo.get(position).getLongitude();

        final String siteID1 = holder.sitecode.getText().toString().trim()+position;

        String siteNotes = holder.myDB.getNotes(siteID1);

        if (!siteNotes.equals("")) {
            String toShow = "Notes: \n";
            toShow += siteNotes;
            holder.sourcenote2.setText(Html.fromHtml("<b>Notes: </b> <p> " + siteNotes + "</p>"));
        } else {
            holder.sourcenote2.setVisibility(View.GONE);
        }


        holder.siteNotes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes;
                //String oldnote = "EMPTY";

                if (holder.typeSpaceSite1.getVisibility() == View.VISIBLE) {

                    holder.siteNotes1.setText("+ Add Notes");
                    holder.typeSpaceSite1.setVisibility(View.GONE);
                    holder.sourcenote2.setVisibility(View.VISIBLE);
                    String type = holder.typeSpaceSite1.getText().toString();
                    if (!holder.myDB.getNotes(siteID1).equals("")) {
                        holder.myDB.updateNotes(siteID1, type);
                    } else {
                        boolean isInserted =holder. myDB.addData(siteID1,
                                type);
                        Log.i(" Status", "" + isInserted);
                    }
                    String text = holder.myDB.getNotes(siteID1);
                    if (!text.equals("")) {
                        String toShow = text;
                        holder.sourcenote2.setText(Html.fromHtml("<b>Notes: </b> <p> " + toShow + "</p>"));
                    }
                } else {

                    holder.siteNotes1.setText("Done");
                    holder.typeSpaceSite1.setVisibility(View.VISIBLE);
                    holder.sourcenote2.setVisibility(View.GONE);
                    notes = holder.myDB.getNotes(siteID1);

                    //Log.i("INFO: ", siteID1 + " " + sourceID);
                    if (notes != null) {
                        holder.typeSpaceSite1.setText(notes);
                    } else {
                        holder.typeSpaceSite1.setHint("Your Notes..");
                    }
                }
                //onBindViewHolder(holder,position);
            }
        });




        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.show_layout.setVisibility(View.GONE);
                holder.hide_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.arrowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.show_layout.setVisibility(View.VISIBLE);
                holder.hide_layout.setVisibility(View.GONE);
            }
        });




        holder.navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) mapFrag.getContext();
                Fragment fragment = new MapTemp();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                Bundle args = new Bundle();
                args.putDouble("d1", mSiteInfo.get(position).getLatitude());
                args.putDouble("d2", mSiteInfo.get(position).getLongitude());
                Log.i("Long Sent", mSiteInfo.get(position).getLatitude()+"");
                fragment.setArguments(args);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSiteInfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView site, sitecode, siaddress, sizip;
        TextView sourcenote2,site2p,site2pd,sitet,siteaddresst;
        LinearLayout siteLayout,show_layout,hide_layout;
        ImageView arrowup,arrowdown;
        Button siteNotes1, deliverForm1;
        EditText typeSpaceSite1;
        DatabaseSQLite myDB;
        ExtendedFloatingActionButton navi;
        ImageView navicon;
        CardView card;
        TextView scc, scd,drn,pid,info;
        TextView ft, rq;
        ImageView moreInfo;
        ImageView img;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            how = itemView.getContext();
            more = LayoutInflater.from(parents.getContext()).inflate(R.layout.moreinfo,parents,false);
            moreInfo = itemView.findViewById(R.id.moreinfo);
            ft = itemView.findViewById(R.id.productDesc);
            rq = itemView.findViewById(R.id.productQty);
            card = itemView.findViewById(R.id.card_viewSite);
            navi = itemView.findViewById(R.id.navSite);
            show_layout =itemView.findViewById(R.id.show_layout_site);
            hide_layout = itemView.findViewById(R.id.hidden_layout_site);
            arrowdown = itemView.findViewById(R.id.arrowdownSite);
            arrowup = itemView.findViewById(R.id.arrowupSite);
            deliverForm1 = itemView.findViewById(R.id.deliverForm1);
            scc = more.findViewById(R.id.siteContainerCode);
            //deliverForm2 = itemView.findViewById(R.id.deliverForm2);
            site = itemView.findViewById(R.id.site);
            sitet = itemView.findViewById(R.id.siteT);
            sitecode = itemView.findViewById(R.id.siteCode);
            siaddress = itemView.findViewById(R.id.siteAddress);
            siteaddresst = itemView.findViewById(R.id.siteAddressT);
            sizip = itemView.findViewById(R.id.siteZipcode);
            site2p = itemView.findViewById(R.id.productCode1);
            site2pd = itemView.findViewById(R.id.productDesc1);


            siteLayout = itemView.findViewById(R.id.siteCard_layout);

            myDB = new DatabaseSQLite(itemView.getContext());


            typeSpaceSite1 = itemView.findViewById(R.id.typeSpaceSite1);
            siteNotes1 = itemView.findViewById(R.id.siteNotes1);


            typeSpaceSite1.setVisibility(View.GONE);


            sourcenote2 = itemView.findViewById(R.id.sourcenotes2);

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
            builder.setView(R.layout.moreinfo);
            builder.setMessage(message);

            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

}
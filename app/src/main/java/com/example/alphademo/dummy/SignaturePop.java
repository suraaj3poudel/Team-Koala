package com.example.alphademo.dummy;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseJson;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.views.triplist.MyAdapter;
import com.example.alphademo.views.triplist.RecyclerViewSite;
import com.example.alphademo.views.triplist.RecyclerViewSource;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignaturePop extends DeliveryForm {

    private SignaturePad signature_pad;
    private Button clear;
    private Button save,done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popsignaturepad);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        // initiate variable for signature pad
        signature_pad = (SignaturePad) findViewById(R.id.signature_pad);
        clear = (Button) findViewById(R.id.clearSign);
        save = (Button) findViewById(R.id.saveSign);
        done = findViewById(R.id.done);

        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(SignaturePop.this, "Start Signing", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                save.setEnabled(true);
                clear.setEnabled(true);
            }

            @Override
            public void onClear() {
                save.setEnabled(false);
                clear.setEnabled(false);
            }

        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature_pad.clear();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature_pad.getSignatureBitmap();
                Toast.makeText(SignaturePop.this, "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendData();
                Toast.makeText(getApplicationContext(),"Sending Data to the Severs", Toast.LENGTH_SHORT);
                finish();
            }
        });
    }

//    public void sendData(){
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//
//        String URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/TripProductPickupPut/";
//        String data = "D-112/r-123/123/2:09/3:09/1200/800";
//        String apikey = "?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL+data+apikey, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                try{
//            }
//                catch (JSONException e){
//                    e.printStackTrace();
//                }
//
//            }
//
//        });
//    }

}

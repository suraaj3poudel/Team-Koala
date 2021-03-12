package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TripDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TripInfoClass> driverNames;
    private static String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef&Code=%7BCode%7D&Active=true";
    RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        recyclerView = findViewById(R.id.driverList);
        driverNames = new ArrayList<TripInfoClass>();
        extractDriverNames();



    }

    private void extractDriverNames() {
        RequestQueue  queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject driverObject = response.getJSONObject(i);
                        Toast.makeText(getApplicationContext(),"fetching",Toast.LENGTH_SHORT).show();
                        TripInfoClass trip = new TripInfoClass();

                        trip.setDriverName(driverObject.getString("data".toString()));

                        driverNames.add(trip);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                adapter = new RecyclerViewAdapter(getApplicationContext(), driverNames);

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: ");
            }
        });

        queue.add(jsonArrayRequest);
    }
}

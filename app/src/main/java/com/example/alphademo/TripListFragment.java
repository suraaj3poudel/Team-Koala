package com.example.alphademo.;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphademo.R;
import com.example.alphademo.RecyclerViewAdapter;
import com.example.alphademo.TripInfoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TripListFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<TripInfoClass> driverNames;
    ProgressBar pbar;
    private static String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/D1?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
    RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        recyclerView = findViewById(R.id.driverList);
        pbar = findViewById(R.id.progressBar2);
        driverNames = new ArrayList<TripInfoClass>();


        extractDriverNames();
        onClickAddNotes();


    }

    private void onClickAddNotes() {

    }

    private void extractDriverNames() {
        RequestQueue  queue = Volley.newRequestQueue(this);

        pbar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(),"fetching all data",Toast.LENGTH_SHORT).show();
                //for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject driverObject = response.getJSONObject("data".toString());
                    JSONArray driverInfo = driverObject.getJSONArray("resultSet1".toString());

                    TripInfoClass trip = new TripInfoClass(driverInfo);

                    driverNames.add(trip) ;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //}

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                adapter = new RecyclerViewAdapter(getApplicationContext(), driverNames);

                recyclerView.setAdapter(adapter);

                pbar.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: ");
            }
        });

        queue.add(jsonObjectRequest);
    }
}

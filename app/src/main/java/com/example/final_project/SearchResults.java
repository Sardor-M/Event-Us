package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.adapters.EventAdapter;
import com.example.final_project.models.Event;
import com.google.gson.JsonArray;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {
    String keyword, location, distance, segmentId, latlong, unit;

    private ArrayList<Event> eventList;

    private EventAdapter eventAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\'black\'>" + "Search Results" + "</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.searchRecyclerView);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        keyword = bundle.getString("keyword", "");
        location = bundle.getString("location", "");
        latlong = bundle.getString("latlong", "");
        distance = bundle.getString("distance", "10");
        segmentId = bundle.getString("category", "");
        unit = bundle.getString("unit", "");
        eventList = new ArrayList<>();

        getData();
    }

    private void getData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ticketserver2571-new.wl.r.appspot.com/api/search?keyword=" + keyword
                + "&latlong=" + latlong + "&location=" + location + "&segmentId=" + segmentId + "&unit=" + unit
                + "&radius=" + distance;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            eventList.add(new Event(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("Event"),
                                    jsonObject.getString("Date"),
                                    jsonObject.getString("Category"),
                                    jsonObject.getString("Venue")
                            ));
                            Toast.makeText(this, jsonObject.getString("Category") + jsonObject.getString("Venue"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    buildRecyclerView();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(jsonArrayRequest);
    }

    private void buildRecyclerView() {
        eventAdapter = new EventAdapter(eventList, SearchResults.this, false);
        // eventAdapter = new EventAdapter(eventList, SearchResults.this, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(eventAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
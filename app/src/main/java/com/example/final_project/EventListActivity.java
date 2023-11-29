package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.final_project.adapters.EventListAdapter;
import com.example.final_project.models.Event;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private ArrayList<Event> events;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // populating the data fetched from the Eventbrite API
        adapter = new EventListAdapter(events);
        recyclerView.setAdapter(adapter);


        Event selectedEvent = events.get(position);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("eventDetails", (CharSequence) selectedEvent);
        startActivity(intent);

    }


    // additional methods for api calls and updating the ui

}
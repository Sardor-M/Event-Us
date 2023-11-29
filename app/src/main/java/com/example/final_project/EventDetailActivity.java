package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.final_project.models.Event;

public class EventDetailActivity extends AppCompatActivity {

    public Event event;
    private TextView eventName, eventDescription, eventDate, eventVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_actvity);

        // Initializing the views
        eventName = findViewById(R.id.eventName);
        eventDescription = findViewById(R.id.eventDescription);
        eventDate = findViewById(R.id.eventDate);
        eventVenue = findViewById(R.id.eventVenue);

        if (getIntent() != null && getIntent().hasExtra("eventDetails")){
            event = (Event) getIntent().getSerializableExtra("eventDetails");

            // populating the views with the data from the event object
            eventName.setText(event.getName());
            eventDescription.setText(event.getDescription());
            eventDate.setText(event.getDate());
            eventVenue.setText(event.getVenue());
        }
    }
}
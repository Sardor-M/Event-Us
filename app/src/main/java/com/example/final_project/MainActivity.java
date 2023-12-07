package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.final_project.adapters.EventListAdapter;
import com.example.final_project.models.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchService();
    }

    public void fetchService() {
        EventbriteApiService apiService = RetrofitClient
                .getClient("https://www.eventbriteapi.com/v3/")
                .create(EventbriteApiService.class);


        double latitude = 37.773972;
        double longitude = -122.431297;

        // Will change the token with a valid one
        Call<EventResponse> call = apiService.searchEvents("Api_Token_WillBeReplaced", "music", latitude, longitude, "10km");

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body().getEvents();
                    EventListAdapter adapter = new EventListAdapter(new ArrayList<>(events));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("API ERROR Encountered", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                Log.e("API ERROR", t.getMessage());
            }
        });
    }
}
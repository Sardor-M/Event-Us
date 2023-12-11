package com.example.final_project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_project.services.FavoriteService;
import com.example.final_project.R;
import com.example.final_project.adapters.EventAdapter;
import com.example.final_project.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {


    private EventAdapter eventAdapter;
    private FavoriteService favoriteService;
    private ArrayList<Event> favoritesArray;
    private RecyclerView favoriteRecyclerView;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoriteRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        favoriteService = new FavoriteService(getContext());
        buildRecyclerView();
        return view;
    }

    private void buildRecyclerView() {

        favoritesArray = favoriteService.getFavoritesArray();
        Toast.makeText(getContext(), favoritesArray.toString(), Toast.LENGTH_SHORT).show();
        eventAdapter = new EventAdapter(favoritesArray, getContext(), true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        favoriteRecyclerView.setHasFixedSize(true);

        favoriteRecyclerView.setLayoutManager(linearLayoutManager);

        favoriteRecyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FAV", "onResume");
        buildRecyclerView();
    }
}
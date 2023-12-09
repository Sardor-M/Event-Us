package com.example.final_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private ArrayList<Event> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDate;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewName = (TextView) view.findViewById(R.id.textView);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        }

        public void bind(Event event) {
            textViewName.setText(event.getName());
            textViewDescription.setText(event.getDescription());
            textViewDate.setText(event.getDate());
        }
    }

    public EventListAdapter(ArrayList<Event> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Event event = localDataSet.get(position);
        viewHolder.bind(event);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void updateEvents(List<Event> newEvents) {
        localDataSet.clear();
        localDataSet.addAll(newEvents);
        notifyDataSetChanged();
    }

}

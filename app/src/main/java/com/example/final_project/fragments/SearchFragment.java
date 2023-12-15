package com.example.final_project.fragments;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import static java.security.AccessController.checkPermission;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.SearchResults;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;


public class SearchFragment extends Fragment {


    private String latlong = "";
    private FusedLocationProviderClient fusedLocationProviderClient;

    private EditText keyword, distance, location;

    private Spinner category, unit;

    private Button search, clear;

    private String[] segmentIds = {"","KZFzniwnSyZfZ7v7nJ","KZFzniwnSyZfZ7v7nE","KZFzniwnSyZfZ7v7na","KZFzniwnSyZfZ7v7nn","KZFzniwnSyZfZ7v7n1"};

    private String[] units = {"miles", "km"};
    private RadioGroup locationRadioGroup;

    @RequiresApi(api = Build.VERSION_CODES.M)

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        getLocation();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        keyword = getView().findViewById(R.id.keyword);
        distance = getView().findViewById(R.id.distance);
        location = getView().findViewById(R.id.location);
        category = getView().findViewById(R.id.category);
        unit = getView().findViewById(R.id.unit);
        locationRadioGroup = getView().findViewById(R.id.locationRadioGroup);
        search = getView().findViewById(R.id.search);
        clear = getView().findViewById(R.id.clear);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.here) {
                    location.setEnabled(false);
                    location.setText("");
                } else {
                    location.setEnabled(true);
                }
            }
        });
        //Populating Category Spinner
        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(getContext(),R.array.categories, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoriesAdapter);

        //Populating Unit Spinner
        ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter.createFromResource(getContext(),R.array.units, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitsAdapter);

        search.setOnClickListener(v -> {

            boolean validationSuccess = fieldValidation();

            if (validationSuccess) {

                int categoryPosition  = category.getSelectedItemPosition();
                int unitPosition = unit.getSelectedItemPosition();

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), SearchResults.class);
                bundle.putString("keyword", keyword.getText().toString());

                if (location.isEnabled()) {
                    bundle.putString("Location", location.getText().toString());
                } else {
                    bundle.putString("Location", latlong);
                }

                bundle.putString("distance", distance.getText().toString());
                bundle.putString("category", segmentIds[categoryPosition]);
                bundle.putString("unit", units[unitPosition]);

                intent.putExtras(bundle);
                startActivity(intent);

            } else {
                Toast.makeText(getContext(), "Validation Failed", Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword.setText("");
                distance.setText("");
                location.setText("");
                category.setSelection(0);
                unit.setSelection(0);
                locationRadioGroup.check(R.id.here);
            }
        });
    }

    private boolean fieldValidation(){
        boolean keyword_fail = false, location_fail=false;

        if (keyword.getText().toString().trim().equals("")){
            keyword.setError("Please enter mandatory field");
            keyword_fail=true;
        }
        if (location.isEnabled() && location.getText().toString().trim().equals("")){
            location.setError("Please enter mandatory field");
            location_fail=true;
        }
        if(!keyword_fail && !location_fail){
            return true;
        }
        else{
            return false;
        }
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        if (checkPermissions()) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        latlong = task.getResult().getLatitude() + "," + task.getResult().getLongitude();
                    }
                }
            });

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkPermissions() {
        return getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();
        keyword.setText("");
        keyword.setError(null);
        location.setText("");
        location.setError(null);
        category.setSelection(0);
        unit.setSelection(0);
        distance.setText("10");
        locationRadioGroup.check(R.id.here);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
package com.soumen.rideshearing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DestinationFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    Button btnSubmit;
    EditText edtStart, edtDesti;
    ImageView liveLocation;

    public DestinationFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        edtStart = view.findViewById(R.id.edtStart);
        edtDesti = view.findViewById(R.id.edtEnd);
        btnSubmit = view.findViewById(R.id.btnSubmit4);
        liveLocation = view.findViewById(R.id.live);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        btnSubmit.setOnClickListener(v -> {
            String start = edtStart.getText() != null ? edtStart.getText().toString().trim() : "";
            String end = edtDesti.getText() != null ? edtDesti.getText().toString().trim() : "";

            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(getContext(), "Please enter both locations", Toast.LENGTH_SHORT).show();
                return;
            }
            if (start.toUpperCase().equals(end.toUpperCase())) {
                Toast.makeText(getContext(), "Resource and destination is not equal", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(requireContext(), CarChoiceActivity.class);
            intent.putExtra("START_LOCATION", start);
            intent.putExtra("END_LOCATION", end);
            startActivity(intent);
        });

        liveLocation.setOnClickListener(v -> checkLocationPermission());
        liveLocation.setOnLongClickListener(v ->
        {
            Toast.makeText(getContext(), "From Your Live Location", Toast.LENGTH_SHORT).show();
            return true;
        });

        return view;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                String cityName = getCityName(location.getLatitude(), location.getLongitude());
                edtStart.setText(cityName);
                Toast.makeText(getContext(), "Location Set: " + cityName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCityName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown City";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

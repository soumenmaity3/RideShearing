package com.soumen.rideshearing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RideActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_PERMISSION =1 ;
    Button btnBook, btnCancel, btnComplete, btnShare;
    ImageView imgCar;
    String carNumber;
    String carType;
    String owner;
    String contactNumber;
    int carImg;

    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView txtCarType,txtOwner,txtCarNumber,txtContactNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        btnBook = findViewById(R.id.btnBook);
        btnComplete = findViewById(R.id.btnComplete);
        btnCancel = findViewById(R.id.btnCancel);
        btnShare = findViewById(R.id.btnShare);

        txtCarNumber=findViewById(R.id.txtCarNumber2);
        txtCarType=findViewById(R.id.txtCarType2);
        txtOwner=findViewById(R.id.txtOwnerName2);
        txtContactNumber=findViewById(R.id.txtContactNumber2);
        imgCar=findViewById(R.id.imageView);

        Intent intent=getIntent();
         carNumber=intent.getStringExtra("carNumber");
         carType=intent.getStringExtra("carType");
         owner=intent.getStringExtra("carName");
         contactNumber=intent.getStringExtra("contactNumber");
         carImg=intent.getIntExtra("carLogo",R.drawable.car);


        txtContactNumber.setText(contactNumber);
        txtOwner.setText(owner);
        txtCarType.setText(carType);
        txtCarNumber.setText(carNumber);
        imgCar.setImageResource(carImg);

        // Initialize location client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permissions
        requestLocationPermissions();

        btnBook.setOnClickListener(v -> {
            btnBook.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
            btnComplete.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
        });

        btnCancel.setOnClickListener(v -> {
            btnBook.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
        });

        btnShare.setOnClickListener(v -> {
            enableLocationSettings();
            shareLiveLocation();
        });

        txtContactNumber.setOnClickListener(v->{
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contactNumber));
            startActivity(callIntent);
        });

    }

    // Request location permissions
    private void requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void enableLocationSettings() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ((ResolvableApiException) e).startResolutionForResult(RideActivity.this, 1001);
                } catch (IntentSender.SendIntentException sendEx) {
                    sendEx.printStackTrace();
                }
            }
        });
    }

    private void shareLiveLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        shareLocation(latitude, longitude);
                    } else {
                        Toast.makeText(RideActivity.this, "Unable to get location!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(RideActivity.this, "Error getting location!", Toast.LENGTH_SHORT).show());
    }

    private void shareLocation(double latitude, double longitude) {
        String locationUri = "https://www.google.com/maps?q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Details Of Car-\n"+
                "Car Type- "+carType+
                "\n Car Owner Name- "+owner+
                "\n Rider's Phone Number is- "+contactNumber+
                "\nMy Live Location: " + locationUri);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

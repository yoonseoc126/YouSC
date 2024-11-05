package com.example.yousc;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.yousc.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = getIntent();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tommyTrojan = new LatLng(34.0206, -118.2854);
        mMap.addMarker(new MarkerOptions()
                .position(tommyTrojan)
                .title("Marker in Tommy Trojan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
        );
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tommyTrojan));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_pin));

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.event_details_window, null);

        // Get references to the TextViews and buttons
        TextView titleView = dialogView.findViewById(R.id.eventTitle);
        titleView.setText(marker.getTitle()); // Set the title to the marker's title

        Button viewCommentsButton = dialogView.findViewById(R.id.viewCommentsButton);
        Button checkButton = dialogView.findViewById(R.id.checkButton);
        Button xButton = dialogView.findViewById(R.id.xButton);
        ImageButton closeButton = dialogView.findViewById(R.id.eventCloseButton);

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView) // Set the custom layout
                .create();

        // Set up the button click listeners
        viewCommentsButton.setOnClickListener(v -> {
            // Handle the button click (e.g., navigate to comments activity)
            startActivity(new Intent(this, CommentsActivity.class));
            dialog.dismiss(); // Dismiss the dialog after the button is clicked
        });

        // Change marker back to red when the dialog is closed
        closeButton.setOnClickListener(v -> {
            // Set the marker's icon back to red
            dialog.dismiss(); // Dismiss the dialog on cancel
        });
        // Change marker back to red when the dialog is dismissed
        dialog.setOnDismissListener(dialogInterface -> {
            // Set the marker's icon back to red
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
        });

        dialog.show(); // Show the dialog

        return true; // Return true to indicate we have handled the click
    }
}
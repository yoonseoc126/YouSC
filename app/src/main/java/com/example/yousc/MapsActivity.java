package com.example.yousc;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.yousc.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button addEventButton;
    private List<Event> eventList;
    private Geocoder geocoder;

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

        addEventButton = findViewById(R.id.addEventButton);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, AddEventActivity.class);
                startActivity(intent);
                finish();
            }
        });

        geocoder = new Geocoder(this, Locale.getDefault());
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //TODO: finish, but tentative code for how we will call get request from db for events
        eventList = new ArrayList<>();
        DatabaseReference ref = mDatabase.child("events");

        // Once the map loads, get a list of all the events already in the database
        // Because the request is asynchronous, perform pin generation inside the try block
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    System.out.println("Printing event: " + event.name);
                    eventList.add(event);
                }

                // Iterate through the list of events we retrieved and add markers for each one
                for (Event e : eventList) {
                    String latitude, longitude;
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(e.location, 1);
                        Address location = addresses.get(0);
                        LatLng ePos = new LatLng(location.getLatitude(), location.getLongitude());
                        System.out.println("Current event pos: " + ePos.toString());
                        mMap.addMarker(new MarkerOptions()
                                .position(ePos)
                                .title("Marker in " + e.name)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        LatLng tommyTrojan = new LatLng(34.0206, -118.2854);
//        mMap.addMarker(new MarkerOptions()
//                .position(tommyTrojan)
//                .title("Marker in Tommy Trojan")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
//        );
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tommyTrojan));
        mMap.setOnMapClickListener(point -> {
            if(point.latitude < 34.010860 || point.latitude > 34.031064 || point.longitude < -118.300248 || point.longitude > -118.264672){
                Toast.makeText(MapsActivity.this, "Marker must be inside USC Fryft Zone", Toast.LENGTH_SHORT).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tommyTrojan, 16));            }else{
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("New Marker")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
                Marker marker = mMap.addMarker(markerOptions);
                mMap.setOnMarkerClickListener(this);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                // Show the coordinates (for debugging)
                System.out.println("Latitude: " + point.latitude + ", Longitude: " + point.longitude);
            }
        });
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_pin));

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.events_details_window, null);

        // Get references to the TextViews and buttons
        TextView titleView = dialogView.findViewById(R.id.eventTitle);
        titleView.setText(marker.getTitle()); // Set the title to the marker's title

        Button viewCommentsButton = dialogView.findViewById(R.id.viewCommentsButton);
        Button checkButton = dialogView.findViewById(R.id.checkButton);
        Button xButton = dialogView.findViewById(R.id.xButton);
        ImageButton closeButton = dialogView.findViewById(R.id.eventCloseButton);
        Button routeMeButton = dialogView.findViewById(R.id.routeMeButton);

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

        routeMeButton.setOnClickListener(v -> {
            // Use the marker's position for routing
            LatLng markerPosition = marker.getPosition();
            String uri = "http://maps.google.com/maps?daddr=" + markerPosition.latitude + "," + markerPosition.longitude;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            // Check if there's an app that can handle the intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(MapsActivity.this, "No application found to open maps", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // Show the dialog

        return true; // Return true to indicate we have handled the click
    }

}
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Event> eventList;
    private List<String> eventIdList;
    private Geocoder geocoder;
    private Map<String, Event> eventToPinMap;
    private Map<Event, String> eventToEventId;
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = getIntent();
        com.example.yousc.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hashmap  that maps event pin IDs to event models so we can populate with specific information
        eventToPinMap = new HashMap<>();
        eventToEventId = new HashMap<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button addEventButton = findViewById(R.id.addEventButton);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, AddEventActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if (item.getItemId() == R.id.discover) {
                   return true;
               }
               else {
                   Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                   startActivity(intent);
                   finish();

               }
                return true;
            }
        });
    }

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
                // add event ID's and actual event Objects to list so we can iterate through them
                // later
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventId = snapshot.getKey();
                    Event event = snapshot.getValue(Event.class);
                    System.out.println("Printing event: " + eventId);
                    // only add event if date time after current date time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date currentDate = new Date();
                    String eventDateTime = event.getDate() + " " + event.getTime();
                    System.out.println("Printing date: " + eventDateTime);
                    try {
                        Date eventDate = dateFormat.parse(eventDateTime);
                        if (eventDate.after(currentDate)) {
                            eventList.add(event);
                            eventToEventId.put(event, eventId);
                        }
                    } catch (ParseException e) {
                        // error for invalid formats
                        Log.e("error parsing event", "error parsing date for event" + eventId);
                    }
                }

                // iterate through the list of events we retrieved and add markers for each one
                // also grab
                for (Event e : eventList) {
                    String latitude, longitude;
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(e.location, 1);
                        Address location = addresses.get(0);
                        LatLng ePos = new LatLng(location.getLatitude(), location.getLongitude());
                        System.out.println("Current event pos: " + ePos.toString());
                        Marker m = mMap.addMarker(new MarkerOptions()
                                .position(ePos)
                                .title("Marker in " + e.name)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
                        );
                        String eventId = eventToEventId.get(e);
                        m.setTag(eventId);
                        eventToPinMap.put(m.getId(), e);

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

                System.out.println("Latitude: " + point.latitude + ", Longitude: " + point.longitude);
            }
        });
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        Event e = eventToPinMap.get(marker.getId());

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_pin));

        View dialogView = getLayoutInflater().inflate(R.layout.events_details_window, null);

        TextView titleView = dialogView.findViewById(R.id.eventTitle);
        titleView.setText(e.getName());

        TextView dateView = dialogView.findViewById(R.id.date);
        String dateTime = e.getDate() + " " + e.getTime();
        dateView.setText(dateTime);

        Button checkButton = dialogView.findViewById(R.id.checkButton);
        checkButton.setText(e.getUpvotes().toString());

        Button xButton = dialogView.findViewById(R.id.xButton);
        xButton.setText(e.getDownvotes().toString());

        TextView addressView = dialogView.findViewById(R.id.address);
        addressView.setText(e.getLocation());

        Button viewCommentsButton = dialogView.findViewById(R.id.viewCommentsButton);
        Integer numComments = e.getNumComments();
        viewCommentsButton.setText(String.format(Locale.getDefault(), "View %d Comments", numComments));

        Button detailsButton = dialogView.findViewById(R.id.detailsButton);

        ImageButton closeButton = dialogView.findViewById(R.id.eventCloseButton);
        Button routeMeButton = dialogView.findViewById(R.id.routeMeButton);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView) // Set the custom layout
                .create();

        viewCommentsButton.setOnClickListener(v -> {
            String eventId = (String) marker.getTag();
            Intent i = new Intent(this, CommentsActivity.class);
            i.putExtra("eventId", eventId);
            startActivity(i);
            dialog.dismiss();
        });

        xButton.setOnClickListener(v -> {
            e.downvote();
            Log.d("Upvote", "Upvotes: " + e.getUpvotes() + " Downvotes: " + e.getDownvotes());
            checkButton.setText(String.valueOf(e.getUpvotes()));
            xButton.setText(String.valueOf(e.getDownvotes()));
            updateVoteCountsInFirebase(e);
        });

        checkButton.setOnClickListener(v -> {
            e.upvote();
            Log.d("Upvote", "Upvotes: " + e.getUpvotes() + " Downvotes: " + e.getDownvotes());
            checkButton.setText(String.valueOf(e.getUpvotes()));
            xButton.setText(String.valueOf(e.getDownvotes()));
            updateVoteCountsInFirebase(e);
        });

        detailsButton.setOnClickListener(v -> {
            View eventDescriptionView = getLayoutInflater().inflate(R.layout.event_description_window, null);
            ImageButton closeDescButton = eventDescriptionView.findViewById(R.id.closeDescButton);

            TextView titleView2 = eventDescriptionView.findViewById(R.id.eventTitle);
            titleView2.setText(e.getName()); // Set the title to the marker's title

            TextView dateView2 = eventDescriptionView.findViewById(R.id.date);
            String dateTime2 = e.getDate() + " " + e.getTime();
            dateView2.setText(dateTime2);

            TextView addressView2 = eventDescriptionView.findViewById(R.id.address);
            addressView2.setText(e.getLocation());

            Button routeMeButton2 = eventDescriptionView.findViewById(R.id.routeMeButton);
            routeMeButton2.setOnClickListener(c -> {
                LatLng markerPosition = marker.getPosition();
                String uri = "http://maps.google.com/maps?daddr=" + markerPosition.latitude + "," + markerPosition.longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MapsActivity.this, "No application found to open maps", Toast.LENGTH_SHORT).show();
                }
            });

            TextView description = eventDescriptionView.findViewById(R.id.description_text);
            description.setText(e.getDetails());

            Button editButton = eventDescriptionView.findViewById(R.id.editButt);
            Button deleteButton = eventDescriptionView.findViewById(R.id.deleteButt);

            AlertDialog eventDescriptionDialog = new AlertDialog.Builder(this)
                    .setView(eventDescriptionView)
                    .create();

            Objects.requireNonNull(eventDescriptionDialog.getWindow()).setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

            closeDescButton.setOnClickListener(c -> eventDescriptionDialog.dismiss());

            deleteButton.setOnClickListener(c -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userEmail = user.getEmail();
                String eventAuthor = e.getAuthorEmail();
                if (!Objects.equals(userEmail, eventAuthor)) {
                    Toast.makeText(MapsActivity.this, "Only the author may delete the event.", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton("Yes", (window, which) -> {
                            // delete event
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference ref = mDatabase.child("events").child((String)marker.getTag());
                            ref.removeValue()
                                    .addOnSuccessListener(eSuccess -> {
                                        // remove marker
                                        marker.remove();
                                        eventDescriptionDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(eFail -> {
                                        Toast.makeText(this, "Failed to delete event", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("No", (window, which) -> {
                            window.dismiss();
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });

            editButton.setOnClickListener(c -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userEmail = user.getEmail();
                String eventAuthor = e.getAuthorEmail();
                if (!Objects.equals(userEmail, eventAuthor)) {
                    Toast.makeText(MapsActivity.this, "Only the author may edit the event.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, EditEventActivity.class);
                intent.putExtra("ID", (String) marker.getTag());
                intent.putExtra("NAME", e.getName());
                intent.putExtra("DATE", e.getDate());
                intent.putExtra("TIME", e.getTime());
                intent.putExtra("LOCATION", e.getLocation());
                intent.putExtra("DETAILS", e.getDetails());
                startActivity(intent);
                dialog.dismiss();
                eventDescriptionDialog.dismiss();
            });

            eventDescriptionDialog.show();
        });

        closeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialogInterface -> {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
        });

        routeMeButton.setOnClickListener(v -> {
            LatLng markerPosition = marker.getPosition();
            String uri = "http://maps.google.com/maps?daddr=" + markerPosition.latitude + "," + markerPosition.longitude;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(MapsActivity.this, "No application found to open maps", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

        return true;
    }

    private void updateVoteCountsInFirebase(Event e)
    {
        String eventId = eventToEventId.get(e);
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference()
                .child("events")  // Assuming you have a collection of events in Firebase
                .child(eventId); // The unique ID of the event
        // Update the upvotes and downvotes in Firebase
        eventRef.child("upvotes").setValue(e.getUpvotes());
        eventRef.child("downvotes").setValue(e.getDownvotes());
    }

    private void reloadMap() {
        mMap.clear();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventId = snapshot.getKey();
                    Event event = snapshot.getValue(Event.class);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date currentDate = new Date();
                    String eventDateTime = event.getDate() + " " + event.getTime();
                    try {
                        Date eventDate = dateFormat.parse(eventDateTime);
                        if (eventDate.after(currentDate)) {
                            eventList.add(event);
                            eventToEventId.put(event, eventId);
                        }
                    } catch (ParseException e) {
                        Log.e("error parsing event", "error parsing date for event" + eventId);
                    }
                }

                for (Event e : eventList) {
                    String latitude, longitude;
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(e.location, 1);
                        Address location = addresses.get(0);
                        LatLng ePos = new LatLng(location.getLatitude(), location.getLongitude());
                        Marker m = mMap.addMarker(new MarkerOptions()
                                .position(ePos)
                                .title("Marker in " + e.name)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
                        );
                        String eventId = eventToEventId.get(e);
                        m.setTag(eventId);
                        eventToPinMap.put(m.getId(), e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DatabaseError", "Error fetching data: " + databaseError.getMessage());
            }
        });
    }

}
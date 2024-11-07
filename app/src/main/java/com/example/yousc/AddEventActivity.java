package com.example.yousc;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    TextInputEditText editEvent, editTime, editLocation, editDetails, editDate;
    Button createEvent;
    ImageButton eventClose;
    private FirebaseAuth mAuth;
    private Geocoder geocoder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_events_modal);

        String event, time, location, details, date;

        TextInputLayout eventLayout = findViewById(R.id.eventName);
        editEvent = (TextInputEditText) eventLayout.getEditText();
        event = String.valueOf(editEvent.getText());

        TextInputLayout locationLayout = findViewById(R.id.location);
        editLocation = (TextInputEditText) locationLayout.getEditText();
        location = String.valueOf(editLocation.getText());

        TextInputLayout dateLayout = findViewById(R.id.eventDate);
        editDate = (TextInputEditText) dateLayout.getEditText();
        date = String.valueOf(editDate.getText());

        TextInputLayout timeLayout = findViewById(R.id.eventTime);
        editTime = (TextInputEditText) timeLayout.getEditText();
        time = String.valueOf(editTime.getText());

        TextInputLayout detailLayout = findViewById(R.id.Details);
        editDetails = (TextInputEditText) detailLayout.getEditText();
        details = String.valueOf(editDetails.getText());

        createEvent = findViewById(R.id.createEventButton);
        eventClose = findViewById(R.id.eventCloseButton);

        geocoder = new Geocoder(this, Locale.getDefault());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event, time, location, details, date;

                event = String.valueOf(editEvent.getText());
                location = String.valueOf(editLocation.getText());
                date = String.valueOf(editDate.getText());
                time = String.valueOf(editTime.getText());
                details = String.valueOf(editDetails.getText());

                if (TextUtils.isEmpty(event) || TextUtils.isEmpty(location) || TextUtils.isEmpty(time) || TextUtils.isEmpty(details)) {
                    Toast.makeText(AddEventActivity.this, "Please fill out all required forms", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (date.charAt(2) != '/' || date.charAt(5) != '/' || date.length() != 10) {
                    Toast.makeText(AddEventActivity.this, "Date must be formatted in mm/dd/yyyy", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time.charAt(2) != ':' ||time.length() != 5) {
                    Toast.makeText(AddEventActivity.this, "Time must be formatted in hh:mm", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(location, 1);
                    System.out.println(addresses.size());
                    if (addresses.size() == 0) {
                        Toast.makeText(AddEventActivity.this, "Please enter a valid location.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Address locationObject = addresses.get(0);

                    Double latitude = locationObject.getLatitude();
                    Double longitude = locationObject.getLongitude();
                    if(latitude < 34.010860 || latitude > 34.031064 || longitude < -118.300248 || longitude > -118.264672){
                        Toast.makeText(AddEventActivity.this, "Event must be within USC Fryft zone", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        Event newEvent = new Event(event, location, date, time, details, 0, 0);

                        mDatabase.child("events").push().setValue(newEvent);
                        Intent intent = new Intent(AddEventActivity.this, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }




                //TODO: check if location and time are valid - use google maps location validation api for this
                //TODO: check if event already exists in the db
                //TODO: find way to keep track of eventID

            }
        });
        eventClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
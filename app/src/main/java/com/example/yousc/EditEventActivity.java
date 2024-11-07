package com.example.yousc;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity {

    TextInputEditText editEvent, editTime, editLocation, editDetails, editDate;
    Button updateEvent;
    ImageButton eventClose;
    private FirebaseAuth mAuth;
    String eventId;
    private Geocoder geocoder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_events_modal);

        geocoder = new Geocoder(this, Locale.getDefault());


        String event, time, location, details, date;
        Intent intent = getIntent();
        eventId = intent.getStringExtra("ID");
        event = intent.getStringExtra("NAME");
        date = intent.getStringExtra("DATE");
        time = intent.getStringExtra("TIME");
        location = intent.getStringExtra("LOCATION");
        details = intent.getStringExtra("DETAILS");

        TextInputLayout eventLayout = findViewById(R.id.eventName);
        editEvent = (TextInputEditText) eventLayout.getEditText();
        if (editEvent != null) editEvent.setText(event); // Set initial text

        TextInputLayout locationLayout = findViewById(R.id.location);
        editLocation = (TextInputEditText) locationLayout.getEditText();
        if (editLocation != null) editLocation.setText(location);

        TextInputLayout dateLayout = findViewById(R.id.eventDate);
        editDate = (TextInputEditText) dateLayout.getEditText();
        if (editDate != null) editDate.setText(date);

        TextInputLayout timeLayout = findViewById(R.id.eventTime);
        editTime = (TextInputEditText) timeLayout.getEditText();
        if (editTime != null) editTime.setText(time);

        TextInputLayout detailLayout = findViewById(R.id.Details);
        editDetails = (TextInputEditText) detailLayout.getEditText();
        if (editDetails != null) editDetails.setText(details);

        Button gobackButton = findViewById(R.id.goBackButt);


        updateEvent = findViewById(R.id.updateButt);
        eventClose = findViewById(R.id.eventCloseButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        updateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event, time, location, details, date;

                event = String.valueOf(editEvent.getText());
                location = String.valueOf(editLocation.getText());
                date = String.valueOf(editDate.getText());
                time = String.valueOf(editTime.getText());
                details = String.valueOf(editDetails.getText());

                if (TextUtils.isEmpty(event) || TextUtils.isEmpty(location) || TextUtils.isEmpty(time) || TextUtils.isEmpty(details)) {
                    Toast.makeText(EditEventActivity.this, "Please fill out all required forms", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: check if location and time are valid - use google maps location validation api for this
                //TODO: check if event already exists in the db
                //TODO: find way to keep track of eventID

                if (date.charAt(2) != '/' || date.charAt(5) != '/' || date.length() != 10) {
                    Toast.makeText(EditEventActivity.this, "Date must be formatted in mm/dd/yyyy", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time.charAt(2) != ':' ||time.length() != 5) {
                    Toast.makeText(EditEventActivity.this, "Time must be formatted in hh:mm", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(location, 1);
                    System.out.println(addresses.size());
                    if (addresses.size() == 0) {
                        Toast.makeText(EditEventActivity.this, "Please enter a valid location.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Address locationObject = addresses.get(0);

                    Double latitude = locationObject.getLatitude();
                    Double longitude = locationObject.getLongitude();
                    if(latitude < 34.010860 || latitude > 34.031064 || longitude < -118.300248 || longitude > -118.264672){
                        Toast.makeText(EditEventActivity.this, "Event must be within USC Fryft zone", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        Event updatedEvent = new Event(event, location, date, time, details, 0, 0, "hi");
                        DatabaseReference eventRef = mDatabase.child("events").child(eventId);
                        eventRef.setValue(updatedEvent);
                        finish();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        eventClose.setOnClickListener(c -> {
            finish();
        });

        gobackButton.setOnClickListener(c -> {
            finish();
        });


    }
}

package com.example.yousc;

import android.content.Intent;
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

public class EditEventActivity extends AppCompatActivity {

    TextInputEditText editEvent, editTime, editLocation, editDetails, editDate;
    Button updateEvent;
    ImageButton eventClose;
    private FirebaseAuth mAuth;
    String eventId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_events_modal);

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
                Event updatedEvent = new Event(event, location, date, time, details, 0, 0);
                DatabaseReference eventRef = mDatabase.child("events").child(eventId);
                eventRef.setValue(updatedEvent);
                finish();
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

package com.example.yousc;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    TextInputEditText editEvent, editTime, editLocation, editDetails, editDate;
    Button createEvent;
    ImageButton eventClose;
    private FirebaseAuth mAuth;

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
                //TODO: check if location and time are valid - use google maps location validation api for this
                //TODO: check if event already exists in the db
                //TODO: find way to keep track of eventID

                Event newEvent = new Event(event, location, date, time, details, 0, 0);
                mDatabase.child("events").push().setValue(newEvent);

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

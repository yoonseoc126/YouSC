package com.example.yousc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Comment> commentsList;
    private CommentAdapter commentAdapter;
    private TextInputEditText editComment;
    private ImageView commentSubmission;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.comments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.commentsMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // retrieve the event Id passed in through the intent
        String eventId = getIntent().getStringExtra("eventId");

        // initializing recycler view (for comments)
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // retrieve user's comment
        TextInputLayout commentLayout = findViewById(R.id.commentTextInput);
        editComment = (TextInputEditText) commentLayout.getEditText();

        // retrieve from database a list of comments associated to the event
        commentsList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("events").child(eventId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //TODO: fetch comments, but figure out adding comments first
                    Event e = task.getResult().getValue(Event.class);
                    commentsList = e.getComments();
                }
            }
        });


//        commentsList.add(new Comment("Jimmy", "03:00 PM", "Just got your sign up for paint night!"));
//        commentsList.add(new Comment("Kaitlyn", "04:32 PM", "Just got your sign up for paint night!"));

        commentAdapter = new CommentAdapter(commentsList);
        recyclerView.setAdapter(commentAdapter);

        // Reference to the close button (make sure this is in the correct layout)
        ImageButton closeButton = findViewById(R.id.closeCommentsButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        commentSubmission = findViewById(R.id.commentSubmitButton);
        commentSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize comment instance
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String commentValue = String.valueOf(editComment.getText());
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = format.format(calendar.getTime());
                Comment c = new Comment(user.getDisplayName(), "formattedTime", commentValue);

                //Retrieve event associated with this event and change the comment list
                DatabaseReference eventRef = mDatabase.child("events").child(eventId);
                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event e = dataSnapshot.getValue(Event.class);
                        // if this is the first comment getting added to the event:
                        e.comments.add(c);
                        eventRef.setValue(e);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
            }
        });


        // Set the close button click listener
        closeButton.setOnClickListener(v -> {
            // Close the CommentsActivity and return to the MapsActivity
            finish(); // This will finish the current activity and return to the previous one in the stack
        });


    }
}
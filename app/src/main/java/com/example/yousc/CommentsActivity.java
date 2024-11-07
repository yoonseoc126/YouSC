package com.example.yousc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private FirebaseUser user;
    private String userEmail;
    private TextView numCommentsHeaderView;
    private String numCommentsHeader;

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

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        String eventId = getIntent().getStringExtra("eventId");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextInputLayout commentLayout = findViewById(R.id.commentTextInput);
        editComment = (TextInputEditText) commentLayout.getEditText();

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
                    Integer commentsListSize = commentsList.size();
                    numCommentsHeaderView = findViewById(R.id.commentsTitle);
                    numCommentsHeaderView.setText("Comments (" + commentsListSize.toString() + ")");
                    commentAdapter = new CommentAdapter(commentsList, userEmail);
                    recyclerView.setAdapter(commentAdapter);
                    System.out.println("SUCCESS GRABBING COMMENTS");
                }
            }
        });

        ImageButton closeButton = findViewById(R.id.closeCommentsButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        commentSubmission = findViewById(R.id.commentSubmitButton);
        commentSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentValue = String.valueOf(editComment.getText());
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String formattedTime = format.format(calendar.getTime());
                Comment c = new Comment(user.getEmail(), formattedTime, commentValue);

                DatabaseReference eventRef = mDatabase.child("events").child(eventId);
                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event e = dataSnapshot.getValue(Event.class);
                        e.comments.add(c);
                        eventRef.setValue(e);

                        commentsList.add(c);
                        numCommentsHeaderView = findViewById(R.id.commentsTitle);
                        Integer size = commentsList.size();
                        numCommentsHeaderView.setText("Comments (" + size.toString() + ")");
                        commentAdapter.notifyItemInserted(commentsList.size() - 1);
                        editComment.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
            }
        });

        closeButton.setOnClickListener(v -> {
            finish();
        });


    }
}

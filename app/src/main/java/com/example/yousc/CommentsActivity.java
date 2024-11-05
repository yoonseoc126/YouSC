package com.example.yousc;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Comment> commentsList;
    private CommentAdapter commentAdapter;


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

        // initializing recycler view (for comments)
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize and add to comments list
        commentsList = new ArrayList<>();
        commentsList.add(new Comment("Jimmy", "03:00 PM", "Just got your sign up for paint night!"));
        commentsList.add(new Comment("Kaitlyn", "04:32 PM", "Just got your sign up for paint night!"));

        commentAdapter = new CommentAdapter(commentsList);
        recyclerView.setAdapter(commentAdapter);

        // Reference to the close button (make sure this is in the correct layout)
        ImageButton closeButton = findViewById(R.id.closeCommentsButton);

        // Set the close button click listener
        closeButton.setOnClickListener(v -> {
            // Close the CommentsActivity and return to the MapsActivity
            finish(); // This will finish the current activity and return to the previous one in the stack
        });


    }
}
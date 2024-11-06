package com.example.yousc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class voteActivity extends AppCompatActivity {

    private Button upvoteButt, downvoteButt;
    private TextView upvoteCount, downvoteCount;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.event_description_window);

        event = new Event();

        upvoteButt = findViewById(R.id.checkButton);
        downvoteButt = findViewById(R.id.xButton);
        upvoteCount = findViewById(R.id.upvoteCount);
        downvoteCount = findViewById(R.id.downvoteCount);

        //display vote counts
        upvoteCount.setText(String.valueOf(event.getUpvotes()));
        downvoteCount.setText(String.valueOf(event.getDownvotes()));



        upvoteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.upvote();
                upvoteCount.setText(String.valueOf(event.getUpvotes()));
                downvoteCount.setText(String.valueOf(event.getDownvotes()));
                updateButtColors();
            }
        });

        downvoteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.downvote();
                upvoteCount.setText(String.valueOf(event.getUpvotes()));
                downvoteCount.setText(String.valueOf(event.getDownvotes()));
                updateButtColors();
            }
        });
    }

    private void updateButtColors()
    {
        if (event.userHasUpvoted)
        {
            upvoteButt.setBackgroundColor(Color.WHITE); // Indicate active upvote
            downvoteButt.setBackgroundColor(Color.GRAY);
        }
        else if (event.userHasDownvoted)
        {
            downvoteButt.setBackgroundColor(Color.RED); // Indicate active downvote
            upvoteButt.setBackgroundColor(Color.GRAY);
        }
        else
        {
            upvoteButt.setBackgroundColor(Color.GRAY); // Neutral state
            downvoteButt.setBackgroundColor(Color.GRAY);
        }
    }
}

package com.example.yousc;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;


//TODO: add comment functionality
//event model class
public class Event{
    public String name;
    public String location;
    public String date;
    public String time;
    public String details;
    public Integer upvotes;
    public Integer downvotes;
    public List<Comment> comments;
    public boolean userHasUpvoted;
    public boolean userHasDownvoted;



    public Event() {
        this.upvotes = 0;
        this.downvotes = 0;
        comments = new ArrayList<>();
    }


    public Event(String name, String location, String date, String time, String details, Integer upvotes, Integer downvotes) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.details = details;
        this.upvotes = 0;
        this.downvotes = 0;
        this.userHasUpvoted = false;
        this.userHasDownvoted = false;
        comments = new ArrayList<>();
    }

    public String getName ()
    {
        return this.name;
    }
    public String getLocation ()
    {
        return this.location;
    }

    public String getDetails () {
        return this.details;
    }
    public String getDate () {
        return this.date;
    }
    public String getTime() {
        return this.time;
    }
    public Integer getUpvotes () {
        return this.upvotes;
    }
    public Integer getDownvotes() {
        return this.downvotes;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public Integer getNumComments() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

//
//    public void upvote() {
//        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference().child("events").child(eventId);
//
//        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Event e = dataSnapshot.getValue(Event.class);  // Retrieve event object
//
//                if (e != null) {
//                    // Check if the user has already upvoted or downvoted
//                    if (!e.userHasUpvoted) {
//                        if (e.userHasDownvoted) {
//                            e.downvotes--;  // Undo the downvote
//                            e.userHasDownvoted = false;
//                        }
//                        e.upvotes++;  // Increment upvotes
//                        e.userHasUpvoted = true;
//                    } else {
//                        e.upvotes--;  // Decrement upvotes
//                        e.userHasUpvoted = false;
//                    }
//
//                    // Save the updated event back to the database
//                    //eventRef.setValue(e);
//                    eventRef.child("upvotes").setValue(e.getUpvotes());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors
//                Log.e("FirebaseError", "Error updating upvotes: " + databaseError.getMessage());
//            }
//        });
//    }
//
//
//    public void downvote() {
//        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference().child("events").child(eventId);
//
//        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Event e = dataSnapshot.getValue(Event.class);  // Retrieve event object
//
//                if (e != null) {
//                    // Check if the user has already downvoted or upvoted
//                    if (!e.userHasDownvoted) {
//                        if (e.userHasUpvoted) {
//                            e.upvotes--;  // Undo the upvote
//                            e.userHasUpvoted = false;
//                        }
//                        e.downvotes++;  // Increment downvotes
//                        e.userHasDownvoted = true;
//                    } else {
//                        e.downvotes--;  // Decrement downvotes
//                        e.userHasDownvoted = false;
//                    }
//
//                    // Save the updated event back to the database
//                    //eventRef.setValue(e);
//                    eventRef.child("downvotes").setValue(e.getDownvotes());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors
//                Log.e("FirebaseError", "Error updating downvotes: " + databaseError.getMessage());
//            }
//        });
//    }







//    public void upvote(){
//        if(!userHasUpvoted) {
//            if (userHasDownvoted) {
//                downvotes--;
//                userHasDownvoted = false;
//            }
//            upvotes++;
//            userHasUpvoted = true;
//        }
//        else
//        {
//            upvotes--;
//            userHasUpvoted = false;
//        }
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events")
//
//                .child(getId());
//        ref.child("upvotes").setValue(upvotes);
//    }
//    public void downvote()
//    {
//        if (!userHasDownvoted)
//        {
//            if (userHasUpvoted)
//            {
//                upvotes--;
//                userHasUpvoted = false;
//            }
//            downvotes++;
//            userHasDownvoted = true;
//        }
//        else {
//            downvotes--;
//            userHasDownvoted = false;
//        }
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child(getId());
//        ref.child("downvotes").setValue(downvotes);
//    }

}

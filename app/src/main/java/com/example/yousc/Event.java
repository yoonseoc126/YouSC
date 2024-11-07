package com.example.yousc;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
    private String authorEmail;



    public Event() {
        this.upvotes = 0;
        this.downvotes = 0;
        comments = new ArrayList<>();
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Event(String name, String location, String date, String time, String details, Integer upvotes, Integer downvotes, String authorEmail) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.details = details;
        this.upvotes = 0;
        this.downvotes = 0;
        this.userHasUpvoted = false;
        this.userHasDownvoted = false;
        this.authorEmail = authorEmail;
        comments = new ArrayList<>();
    }

    public String getName () {
        return this.name;
    }
    public String getLocation () {
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
    public String getAuthorEmail() {return this.authorEmail;}
    public List<Comment> getComments() {
        return comments;
    }
    public Integer getNumComments() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }
    public void upvote(){
        if(!userHasUpvoted)
        {
            if(userHasDownvoted)
            {
                downvotes--;
                userHasDownvoted = false;
            }
            upvotes++;
            userHasUpvoted = false;
        }
        else{
            upvotes--;
            userHasUpvoted = false;
        }
    }
    public void downvote()
    {
        if (!userHasDownvoted)
        {
            if (userHasUpvoted)
            {
                upvotes--;
                userHasUpvoted = false;
            }
            downvotes++;
            userHasDownvoted = true;
        }
        else {
            downvotes--;
            userHasDownvoted = false;
        }
    }


}

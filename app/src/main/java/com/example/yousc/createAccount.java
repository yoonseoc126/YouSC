package com.example.yousc;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createAccount extends AppCompatActivity {
    TextInputEditText editEmail, editPass;
    Button signUp;
    TextView signIn;
    Integer userPK;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        TextInputLayout emailLayout = findViewById(R.id.email);
        editEmail = (TextInputEditText) emailLayout.getEditText();

        TextInputLayout passwordLayout = findViewById(R.id.pass);
        editPass = (TextInputEditText) passwordLayout.getEditText();

        signIn = findViewById(R.id.loginLink);
        signUp = findViewById(R.id.createAccountButt);

        userPK = 1;

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createAccount.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email, password;
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPass.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(createAccount.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(createAccount.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference myRef = database.getReference("users");
                //check if account already exists in the DB
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //redirect to sign in page
                                    Intent intent = new Intent(createAccount.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
//                                  updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(),  "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }
                            }
                        });

                //if not, add to DB and redirect to sign in page

//                User user = new User(email, pass);
//                myRef.child(userPK.toString()).setValue(user);
//                userPK+=1;
//                Intent intent = new Intent(createAccount.this, MainActivity.class);
//                startActivity(intent);
//                finish();

            }
        });

    }

}

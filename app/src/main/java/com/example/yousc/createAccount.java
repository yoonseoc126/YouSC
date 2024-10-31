package com.example.yousc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class createAccount extends AppCompatActivity {
    TextInputEditText editEmail, editPass;
    Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.pass);
        signIn = findViewById(R.id.loginLink);
        signUp = findViewById(R.id.createAccountButt);

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
                String email, pass;
                email = String.valueOf(editEmail.getText());
                pass = String.valueOf(editPass.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(createAccount.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(createAccount.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

    }

}

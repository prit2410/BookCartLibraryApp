package com.example.myapplicationlibrary2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RetrunBookUserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button submit;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    EditText loginUsername, borrowedbookname;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retrun_book_user);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginUsername = findViewById(R.id.login_username);
        loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()) {
                } else {
                    checkUser();
                }
            }
        });
//        signupRedirectText.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(BorrowBookActivity.this, UserRegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
//        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference userRef = mDatabase.child("Users").child(userUsername);
        DatabaseReference userbook = mDatabase.child("Users").child(userUsername);
        // Attach a ValueEventListener to read the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the username and password from the DataSnapshot
                String username = dataSnapshot.child("username").getValue(String.class);

                // Handle the retrieved data
                if (username != null) {
                    Log.d(TAG, "Username: " + username);
                    if (username.equals(userUsername)){
                        Toast.makeText(RetrunBookUserActivity.this, "Found User", Toast.LENGTH_SHORT).show();


                        Intent fp=new Intent(getApplicationContext(),DeleteBookActivity.class);
                        fp.putExtra("mytext",userUsername);
                        startActivity(fp);
//                        Intent myIntent = new Intent(BorrowBookActivity.this, BorrowBookPageActivity.class);
//                        myIntent.putExtra("username", userUsername);
//                        startActivity(myIntent);


                    }
                    // Here you can use the username and password as needed
                } else {
                    Log.d(TAG, "Username or password not found");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });



    }
}
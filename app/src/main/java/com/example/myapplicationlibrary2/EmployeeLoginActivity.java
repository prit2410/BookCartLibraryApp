package com.example.myapplicationlibrary2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLoginActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    Button submit;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loginButton = findViewById(R.id.login_button);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }



                Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeSelectActivity.class);
                startActivity(intent);
            }
        });




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
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }




    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference userRef = mDatabase.child("Employee");

        // Attach a ValueEventListener to read the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the username and password from the DataSnapshot
                String username = dataSnapshot.child("username").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);


                //to save our data with key and value.

                // Handle the retrieved data
                if (username != null && password != null) {
                    Log.d(TAG, "Username: " + username);
                    Log.d(TAG, "Password: " + password);
                    if (username.equals(userUsername) && password.equals(userPassword)) {
                        Toast.makeText(EmployeeLoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeSelectActivity.class);
                        startActivity(intent);
                        finish();
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
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class BorrowBookActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    Button submit;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    EditText loginUsername, borrowedbookname;

    Button loginButton;
    TextView signupRedirectText, availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_borrow_book);




        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginUsername = findViewById(R.id.login_username);
        loginButton = findViewById(R.id.login_button);
        borrowedbookname = findViewById(R.id.borrowedbookname);
        availability = findViewById(R.id.availability);

        String avail = "Yes";
        try {
            FileInputStream fileInputStream = openFileInput("borrowbook.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = inputReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            inputReader.close();
            Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            borrowedbookname.setText("" + stringBuilder.toString());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        availability.setText("Availability: " + String.format("%s", avail));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()) {
                } else {
                    checkUser();
                }

                loginButton.setEnabled(false);
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
                        Toast.makeText(BorrowBookActivity.this, "Found User", Toast.LENGTH_SHORT).show();
                        String bookname = borrowedbookname.getText().toString();
                        HashMap map = new HashMap();
                        map.put("bookname", bookname);
                        userbook.updateChildren(map);

                        Intent fp=new Intent(getApplicationContext(),EmployeeSelectActivity.class);
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
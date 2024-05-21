package com.example.myapplicationlibrary2;

import static android.content.ContentValues.TAG;

import android.content.Context;
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class DeleteBookActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button submit;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    TextView loginUsername;
    Button loginButton, pastbooks;
    TextView signupRedirectText;
    Context context = this;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_book);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginUsername = findViewById(R.id.login_username);
        loginButton = findViewById(R.id.login_button);
        pastbooks = findViewById(R.id.pastbooks);


        checkUser();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    deletBook();
            }
        });
//        signupRedirectText.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(BorrowBookActivity.this, UserRegisterActivity.class);
//                startActivity(intent);
//            }
//        });

        checkUser();
        pastbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fp=new Intent(getApplicationContext(),PastBooksActivity.class);
                startActivity(fp);
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

    public void checkUser(){
String username = getIntent().getStringExtra("mytext");
        DatabaseReference userRef = mDatabase.child("Users").child(username);
        // Attach a ValueEventListener to read the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the username and password from the DataSnapshot
                String username = dataSnapshot.child("username").getValue(String.class);
                String bookname = dataSnapshot.child("bookname").getValue(String.class);

                // Handle the retrieved data

                    Log.d(TAG, "Bookname: " + bookname);

//                        Toast.makeText(DeleteBookActivity.this, "Found User", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DeleteBookActivity.this, "Book Found", Toast.LENGTH_SHORT).show();
                        loginUsername.setText(bookname);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

//                        Intent fp=new Intent(getApplicationContext(),BorrowBookNameActivity.class);
//                        startActivity(fp);
//                        Intent myIntent = new Intent(BorrowBookActivity.this, BorrowBookPageActivity.class);
//                        myIntent.putExtra("username", userUsername);
//                        startActivity(myIntent);

    public void deletBook(){
        String userUsername = loginUsername.getText().toString().trim();
//        String userPassword = loginPassword.getText().toString().trim();
        String username = getIntent().getStringExtra("mytext");

        DatabaseReference userRef = mDatabase.child("Users").child(username);
        DatabaseReference userbook = mDatabase.child("Users").child(username);
        // Attach a ValueEventListener to read the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the username and password from the DataSnapshot
                String username = dataSnapshot.child("username").getValue(String.class);
                String bookname = dataSnapshot.child("bookname").getValue(String.class);

                // Handle the retrieved data


                        Toast.makeText(DeleteBookActivity.this, "Book Returned Successfully", Toast.LENGTH_SHORT).show();



                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                        outputStreamWriter.write(bookname);
                        outputStreamWriter.close();
                    }
                    catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }


                        HashMap map = new HashMap();
                        map.put("bookname", "No Books");
                        userbook.updateChildren(map);

                        loginUsername.setText(bookname);

                        Intent fp=new Intent(getApplicationContext(),EmployeeSelectActivity.class);
                        startActivity(fp);
//                        Intent myIntent = new Intent(BorrowBookActivity.this, BorrowBookPageActivity.class);
//                        myIntent.putExtra("username", userUsername);
//                        startActivity(myIntent);



                    // Here you can use the username and password as needed

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });


    }
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent fp=new Intent(getApplicationContext(),EmployeeSelectActivity.class);
            startActivity(fp);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
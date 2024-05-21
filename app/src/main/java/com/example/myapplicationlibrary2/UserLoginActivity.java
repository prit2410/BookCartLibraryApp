package com.example.myapplicationlibrary2;



import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;



import androidx.activity.EdgeToEdge;

public class UserLoginActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    Button submit;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;


    String email, password;

    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);




        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
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

        DatabaseReference userRef = mDatabase.child("Users").child(userUsername);

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

                        try {
                            FileOutputStream fileOutputStream = openFileOutput("username.txt", Context.MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
                            outputWriter.write(username);
                            outputWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(UserLoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserLoginActivity.this, BookHome.class);
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












    public void showNotification(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.bookmark_light)
                    .setContentTitle("SUBSCRIBE")
                    .setContentText("MOBILE APP DEVELOPMENT")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }

        Notification notification;
        notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID,notification);

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CharSequence channelName = "My Channel";
            String channelDiscription = "My Channel Discreption";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channelName,importance);
            channel.setDescription(channelDiscription);

            notificationManager.createNotificationChannel(channel);
        }

















//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    loginUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//                    if (passwordFromDB.equals(userPassword)) {
//                        loginUsername.setError(null);
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("username", usernameFromDB);
//                        intent.putExtra("password", passwordFromDB);
//                        startActivity(intent);
//                    } else {
//                        loginPassword.setError("Invalid Credentials");
//                        loginPassword.requestFocus();
//                    }
//                } else {
//                    loginUsername.setError("User does not exist");
//                    loginUsername.requestFocus();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });


//        mAuth = FirebaseAuth.getInstance();
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//
//        submit = findViewById(R.id.Login);
//        submit.setOnClickListener(v -> {
//            String email = editTextEmail.getText().toString().trim();
//            String password = editTextPassword.getText().toString().trim();
//
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, task -> {
//                        if (task.isSuccessful()) {
//                            // Log in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(UserLoginActivity.this, "Log in successful.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            // If log in fails, display a message to the user.
//                            Toast.makeText(UserLoginActivity.this, "Log in failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        });
//
//        startActivity(new Intent(this, UserRegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);


    }
}
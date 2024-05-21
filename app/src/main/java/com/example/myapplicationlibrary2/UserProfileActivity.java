package com.example.myapplicationlibrary2;

import static android.content.ContentValues.TAG;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
// ...

    TextView tv_name;
    TextView editTextEmail, useremail, userpassword, bookname;
    ImageView photoup;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String storagepath = "Users_Profile_Cover_image/";
    String uid;
    ImageView set;
    TextView profilepic, editname, editpassword, bookreturndate;
    ProgressDialog pd;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    Uri imageuri;
    String profileOrCoverPhoto;

    Context context = this;
    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;


    Button logoutbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();


//        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yy");
//        Date co = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + co);
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(co);
//        String dt = formattedDate;  // Start date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(sdf.parse(dt));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
//        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
//        String output = sdf1.format(c.getTime());


        tv_name = findViewById(R.id.tv_name);
        editTextEmail = findViewById(R.id.editTextEmail);
        photoup = findViewById(R.id.photoup);
        useremail = findViewById(R.id.useremail);
        userpassword = findViewById(R.id.userpassword);
        bookname = findViewById(R.id.bookname);
        bookreturndate = findViewById(R.id.bookreturndate);
        logoutbutton = findViewById(R.id.logoutbutton);
        tv_name = findViewById(R.id.tv_name);


        try {
            FileInputStream fileInputStream = openFileInput("username.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = inputReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            inputReader.close();

            DatabaseReference userRef = mDatabase.child("Users").child(String.valueOf(stringBuilder));

            // Attach a ValueEventListener to read the user data
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get the username and password from the DataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String booknametext = dataSnapshot.child("bookname").getValue(String.class);

                    // Handle the retrieved data
                    if (username != null && password != null) {
                        Log.d(TAG, "Username: " + username);
                        Log.d(TAG, "Password: " + password);
                        editTextEmail.setText(username);
                        useremail.setText(email);
                        userpassword.setText(password);
                        bookname.setText(booknametext);
                        tv_name.setText(username);


                        if (booknametext == "No Books") {
                            bookreturndate.setText("");
                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yy");
                            Date co = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + co);

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(co);
                            String dt = formattedDate;  // Start date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(dt));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
                            String output = sdf1.format(c.getTime());
                            bookreturndate.setText(formattedDate);
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

            logoutbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UserProfileActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(android.R.drawable.stat_notify_chat)
//                .setContentTitle("New message")
//                .setContentText("You have a new message.")
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        // Create a notification manager object
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Issue the notification
//        notificationManager.notify(1, builder.build());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        String message = "Please Return the Book By Today";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(UserProfileActivity.this, "My Notification");
        builder.setContentTitle("Book Return Reminder");
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.bookmark_light);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(UserProfileActivity.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(1, builder.build());




    }

}
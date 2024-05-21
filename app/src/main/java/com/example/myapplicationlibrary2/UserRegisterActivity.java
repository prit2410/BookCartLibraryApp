package com.example.myapplicationlibrary2;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class UserRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView editTextEmail;
    TextView editTextName;
    TextView editTextPassword;
    Button submit;
    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        changeStatusBarColor();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signupUsername.getText().toString();
                DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username);
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String bookname = "JavaOPT";
                String hold = "NO";
                HelperClass helperClass = new HelperClass(name, email, username, password, bookname, hold);
                userIdReference.setValue(helperClass);
                Toast.makeText(UserRegisterActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });



//        editTextEmail = (TextView) findViewById(R.id.editTextEmail);
//        editTextName = (TextView) findViewById(R.id.editTextName);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        submit = (Button) findViewById(R.id.Login);
//
//        Random r = new Random();
//        List<Integer> codes = new ArrayList<>();
//        for (int i = 0; i < 10; i++)
//        {
//            int x = r.nextInt(9999);
//            while (codes.contains(x))
//                x = r.nextInt(9999);
//            codes.add(x);
//        }
//        Integer str = codes.get(0);
////        Intent myIntent = new Intent(UserRegisterActivity.this, UserProfileActivity.class);
////        myIntent.putExtra("intVariableName", str);
////        startActivity(myIntent);
//
//        editTextEmail.setText("" + str);
//
//
//
//        submit.setOnClickListener(v -> {
//            String email = editTextName.getText().toString().trim();
//            String password = editTextPassword.getText().toString().trim();
//
//            class UserData {
//                public String name;
//                public Integer UserID;
//                public String password;
//
//                // Constructor with parameters
//                public UserData(String email, Integer UserID, String password) {
//                    this.name = email;
//                    this.UserID = UserID;
//                    this.password = password;
//                }
//
//                // Constructor without parameters for Firebase database
//                public UserData() {
//                    this.name = email;
//                    this.password = password;
//                }
//
//                // Getters and Setters
//                public String getName() {
//                    return name;
//                }
//
//                public void setName(String name) {
//                    this.name = name;
//                }
//
//                public Integer getUserid() {
//                    return UserID;
//                }
//
//                public void setUserid(Integer str) {
//                }
//
//                public String getPassword() {
//                    return password;
//                }
//
//                public void setPassword() {
//                    this.password = password;
//                }
//            }
//
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            FirebaseUser user = auth.getCurrentUser();
//            if (user != null) {
//                DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference()
//                        .child("Users").child(user.getUid());
//                UserData UserData = new UserData(email, codes.get(0), password);
//
//                userIdReference.setValue(UserData);
//            }
//        });
//
//
//
//
//
//




//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, task -> {
//                        if (task.isSuccessful()) {
//                            mFirebaseAnalytics.setUserId(String.valueOf(codes.get(0)));
//                            // Sign up success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(UserRegisterActivity.this, "Sign up successful.",
//                                    Toast.LENGTH_SHORT).show();
//                                submit.setOnClickListener(new View.OnClickListener() {
//                                    public void onClick(View v)
//                                    {
//                                        // starting background task to update product
//                                        Intent fp=new Intent(getApplicationContext(),BookHome.class);
//                                        startActivity(fp);
//                                    }
//                                });
//                        } else {
//                            // If sign up fails, display a message to the user.
//                            Toast.makeText(UserRegisterActivity.this, "Sign up failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });



    }




    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, UserLoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }




}
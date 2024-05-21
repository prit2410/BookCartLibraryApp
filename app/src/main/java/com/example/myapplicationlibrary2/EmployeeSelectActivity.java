package com.example.myapplicationlibrary2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class EmployeeSelectActivity extends AppCompatActivity {

    Button login_button, return_book, logoutbutton;
    Context context = this;
    ImageView holdbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_select);

        login_button = findViewById(R.id.login_button);
        return_book = findViewById(R.id.returnbook);
        holdbutton = findViewById(R.id.holdbutton);
        logoutbutton = findViewById(R.id.logoutbutton);

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // starting background task to update product
                Intent intent=new Intent(getApplicationContext(),BorrowBookActivity.class);
                startActivity(intent);
            }
        });


        return_book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // starting background task to update product
                Intent fp=new Intent(getApplicationContext(),RetrunBookUserActivity.class);
                startActivity(fp);
            }
        });

        holdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(EmployeeSelectActivity.this, HoldButtonActivity.class);
                startActivity(intent);
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeeSelectActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


//        try {
//            FileInputStream fileInputStream = openFileInput("mytextfile.txt");
//            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
//            StringBuilder stringBuilder = new StringBuilder();
//            int character;
//            while ((character = inputReader.read()) != -1) {
//                stringBuilder.append((char) character);
//            }
//            inputReader.close();
//
//            Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                builder1.setMessage("Please Hold this Book: " + stringBuilder.toString());
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }



}
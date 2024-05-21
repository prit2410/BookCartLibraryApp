package com.example.myapplicationlibrary2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PastBooksActivity extends AppCompatActivity {

    TextView login_username;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_past_books);

        login_username = findViewById(R.id.login_username);

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yy");
                Date co = Calendar.getInstance().getTime();
                System.out.println("Current time => " + co);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(co);
                String dt = formattedDate;
                login_username.setText(ret + "Returned Date: " + dt);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yy");
            Date co = Calendar.getInstance().getTime();
            System.out.println("Current time => " + co);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(co);
            String dt = formattedDate;
            FileInputStream fileInputStream = openFileInput("borrowbook.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = inputReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            inputReader.close();
            Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            login_username.setText("" + stringBuilder.toString() +  "Returned Date: " + dt);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
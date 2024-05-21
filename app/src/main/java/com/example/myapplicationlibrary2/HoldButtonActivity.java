package com.example.myapplicationlibrary2;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HoldButtonActivity extends AppCompatActivity {



    TextView login_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hold_button);


        login_username = findViewById(R.id.login_username);


        try {
            FileInputStream fileInputStream = openFileInput("mytextfile.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = inputReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            inputReader.close();

            login_username.setText("Hold The Book fot the user "+ "prit: " + stringBuilder);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
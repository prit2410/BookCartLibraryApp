package com.example.myapplicationlibrary2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalizeBookSelectActivity extends AppCompatActivity {


    CheckBox checkbox_action;
    CheckBox checkbox_adventure;
    CheckBox checkBox_comedy;
    CheckBox checkbox_horror;
    CheckBox checkbox_romance;
    CheckBox checkbox_mystry;
    Button submit;

    private int selectedCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personalize_book_select);


        checkbox_action = findViewById(R.id.checkbox_action);
        checkbox_adventure = findViewById(R.id.checkbox_adventure);
        checkBox_comedy = findViewById(R.id.checkbox_comedy);
        checkbox_horror = findViewById(R.id.checkbox_horror);
        checkbox_romance = findViewById(R.id.checkbox_romance);
        checkbox_mystry = findViewById(R.id.checkbox_mystry);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if exactly 3 checkboxes are selected
                if (selectedCount == 3) {
                    // Perform action when 3 checkboxes are selected
                    // For example, display a message
                    Toast.makeText(PersonalizeBookSelectActivity.this, "Selection valid", Toast.LENGTH_SHORT).show();
                } else {
                    // If not exactly 3 checkboxes selected, display an error message
                    Toast.makeText(PersonalizeBookSelectActivity.this, "Please select exactly 3 options", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener for each checkbox
        checkbox_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });

        checkbox_adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });

        checkBox_comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });

        checkbox_horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });
        checkbox_romance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });
        checkbox_mystry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
            }
        });
    }
}
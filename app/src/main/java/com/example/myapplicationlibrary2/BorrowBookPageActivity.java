package com.example.myapplicationlibrary2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class BorrowBookPageActivity extends AppCompatActivity {
    //creating variables for strings,text view, image views and button.
    String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink, buyLink;
    int pageCount;
    private ArrayList<String> authors;

    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV;
    Button buyBtn;
    private ImageView bookIV;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_borrow_book_page);


        mDatabase = FirebaseDatabase.getInstance().getReference();


                //initializing our views..
                titleTV = findViewById(R.id.idTVTitle);
                subtitleTV = findViewById(R.id.idTVSubTitle);
                publisherTV = findViewById(R.id.idTVpublisher);
                descTV = findViewById(R.id.idTVDescription);
                pageTV = findViewById(R.id.idTVNoOfPages);
                publishDateTV = findViewById(R.id.idTVPublishDate);
                buyBtn = findViewById(R.id.idBtnBuy);
                bookIV = findViewById(R.id.idIVbook);

                //getting the data which we have passesd from our adapter class.
                title = getIntent().getStringExtra("title");
                subtitle = getIntent().getStringExtra("subtitle");
                publisher = getIntent().getStringExtra("publisher");
                publishedDate = getIntent().getStringExtra("publishedDate");
                description = getIntent().getStringExtra("description");
                pageCount = getIntent().getIntExtra("pageCount", 0);
                thumbnail = getIntent().getStringExtra("thumbnail");
                previewLink = getIntent().getStringExtra("previewLink");
                infoLink = getIntent().getStringExtra("infoLink");
                buyLink = getIntent().getStringExtra("buyLink");

                //after getting the data we are setting that data to our text views and image view.
                titleTV.setText(title);
                subtitleTV.setText(subtitle);
                publisherTV.setText(publisher);
                publishDateTV.setText("Published On : " + publishedDate);
                descTV.setText(description);
                pageTV.setText("No Of Pages : " + pageCount);
                Picasso.get().load(thumbnail).into(bookIV);

                //adding on click listner for our preview button.
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                //initializing on click listner for buy button.
                String text = getIntent().getStringExtra("username");
        buyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference()
                                .child("Users");
                        String title = getIntent().getStringExtra("title");
//                        HelperClass helperClass = new HelperClass(title);
//                        userIdReference.setValue(helperClass);
                        Toast.makeText(BorrowBookPageActivity.this, "Book Borrowed", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }

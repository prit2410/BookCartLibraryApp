package com.example.myapplicationlibrary2;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class BookDetails extends AppCompatActivity {
    //creating variables for strings,text view, image views and button.
    String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink, buyLink;
    int pageCount;
    private ArrayList<String> authors;

    private DatabaseReference mDatabase;

    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV;
    Button previewBtn, buyBtn;
    private ImageView bookIV;
    ImageButton profilebutton;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        //initializing our views..
        titleTV = findViewById(R.id.idTVTitle);
        subtitleTV = findViewById(R.id.idTVSubTitle);
        publisherTV = findViewById(R.id.idTVpublisher);
        descTV = findViewById(R.id.idTVDescription);
        pageTV = findViewById(R.id.idTVNoOfPages);
        publishDateTV = findViewById(R.id.idTVPublishDate);
        previewBtn = findViewById(R.id.idBtnPreview);
        buyBtn = findViewById(R.id.idBtnBuy);
        bookIV = findViewById(R.id.idIVbook);

        mDatabase = FirebaseDatabase.getInstance().getReference();


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
        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previewLink.isEmpty()){
                    //below toast message is displayed when preview link is not present.
                    Toast.makeText(BookDetails.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if the link is present we are opening that link via an intent.
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });




      buyBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                  FileOutputStream fileOutputStream = openFileOutput("mytextfile.txt", Context.MODE_PRIVATE);
                  OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
                  outputWriter.write(title);


                  FileOutputStream fileOutputStream1 = openFileOutput("borrowbook.txt", Context.MODE_PRIVATE);
                  OutputStreamWriter outputWriter1 = new OutputStreamWriter(fileOutputStream1);
                  outputWriter1.write("Title: "+title + " Author: " + publisher);


                  outputWriter.close();
                  outputWriter1.close();
                  Toast.makeText(getApplicationContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                  buyBtn.setEnabled(false);
              } catch (Exception e) {
                  e.printStackTrace();
              }


          }
      });





//        String data = "";
//        StringBuffer sbuffer = new StringBuffer();
//        InputStream is = this.getResources().openRawResource(+
//                R.a);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        if (is != null) {
//            try {
//                while ((data = reader.readLine()) != null) {
//                    sbuffer.append(data + "\n");
//                }
//                is.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            data = sbuffer.toString();
//        }











    }



    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent fp=new Intent(getApplicationContext(),BookHome.class);
            startActivity(fp);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
package com.example.myapplicationlibrary2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookHome extends AppCompatActivity {

    //creating variables for our requestqueue, array list, progressbar, edittext, image button and our recycler view. 
    public RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoArrayList;
    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn, profilebutton;
    RecyclerView idRVBooks;

    Context context = this;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_home);


        //initializing our views. 
        progressBar = findViewById(R.id.idLoadingPB);
        searchEdt = findViewById(R.id.idEdtSearchBooks);
        searchBtn = findViewById(R.id.idBtnSearch);
        idRVBooks = findViewById(R.id.idRVBooks);
        profilebutton = findViewById(R.id.profilebutton);



        getBooksInfo("B");

        //initializing on click listner for our button.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //checking if our edittext field is empty or not.
                if (searchEdt.getText().toString().isEmpty()) {
                    searchEdt.setError("Please enter search query");
                    return;
                }
                //if the search query is not empty then we are calling get book info method to load all the books from the API.
                getBooksInfo(searchEdt.getText().toString());
            }
        });


        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp=new Intent(getApplicationContext(),UserProfileActivity.class);
                startActivity(fp);
            }
        });

    }





    private void getBooksInfo(String query) {
        //creating a new array list.
        bookInfoArrayList = new ArrayList<>();
        //below line is use to initialze the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(BookHome.this);
        //below line is use to clear cache this will be use when our data is being updated.
        mRequestQueue.getCache().clear();
        //below is the url for getting data from API in json format.
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        //below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(BookHome.this);

        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray itemsArray = response.getJSONArray("items");

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String title = volumeObj.optString("title");
                        String subtitle = volumeObj.optString("subtitle");
                        JSONArray authorsArray = volumeObj.getJSONArray("authors");
                        String publisher = volumeObj.optString("publisher");
                        String publishedDate = volumeObj.optString("publishedDate");
                        String description = volumeObj.optString("description");
                        int pageCount = volumeObj.optInt("pageCount");
                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                        assert imageLinks != null;
                        String thumbnail = imageLinks.optString("thumbnail");
                        String previewLink = volumeObj.optString("previewLink");
                        String infoLink = volumeObj.optString("infoLink");
                        JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                        assert saleInfoObj != null;
                        String buyLink = saleInfoObj.optString("buyLink");
                        ArrayList<String> authorsArrayList = new ArrayList<>();
                        if (authorsArray.length() != 0) {
                            for (int j = 0; j < authorsArray.length(); j++) {
                                authorsArrayList.add(authorsArray.optString(i));
                            }
                        }
                        //after extracting all the data we are saving this data in our modal class.
                        BookInfo bookInfo = new BookInfo(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
                        //beloe line is use to pass our modal class in our array list.
                        bookInfoArrayList.add(bookInfo);
                        //below line is use to pass our array list in adapter class.
                        BookAdapter adapter = new BookAdapter(bookInfoArrayList, BookHome.this);
                        //below line is use to add linear layout manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookHome.this, RecyclerView.VERTICAL, false);
                        RecyclerView mRecyclerView = findViewById(R.id.idRVBooks);
                        //in below line we are setting layout manager and adapter to our recycler view.
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //displaying a toast message when we get any erro from API
                    Toast.makeText(BookHome.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            //also displaying error message in toast.
            Toast.makeText(BookHome.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
        });
        //at last we are adding our json object request in our request queue.
        queue.add(booksObjrequest);
    }











//    private void getBooksInfo(String query) {
//        //creating a new array list.
//        bookInfoArrayList = new ArrayList<>();
//        //below line is use to initialze the variable for our request queue.
//        mRequestQueue = Volley.newRequestQueue(BookHome.this);
//        //below line is use to clear cache this will be use when our data is being updated.
//        mRequestQueue.getCache().clear();
//        //below is the url for getting data from API in json format.
//        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
//        //below line we are  creating a new request queue.
//        RequestQueue queue = Volley.newRequestQueue(BookHome.this);
//
//        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray itemsArray = response.getJSONArray("items");
//
//                    for (int i = 0; i < itemsArray.length(); i++) {
//                        JSONObject itemsObj = itemsArray.getJSONObject(i);
//                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
//                        String title = volumeObj.optString("title");
//                        String subtitle = volumeObj.optString("subtitle");
//                        JSONArray authorsArray = volumeObj.getJSONArray("authors");
//                        String publisher = volumeObj.optString("publisher");
//                        String publishedDate = volumeObj.optString("publishedDate");
//                        String description = volumeObj.optString("description");
//                        int pageCount = volumeObj.optInt("pageCount");
//                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
//                        assert imageLinks != null;
//                        String thumbnail = imageLinks.optString("thumbnail");
//                        String previewLink = volumeObj.optString("previewLink");
//                        String infoLink = volumeObj.optString("infoLink");
//                        JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
//                        assert saleInfoObj != null;
//                        String buyLink = saleInfoObj.optString("buyLink");
//                        ArrayList<String> authorsArrayList = new ArrayList<>();
//                        if (authorsArray.length() != 0) {
//                            for (int j = 0; j < authorsArray.length(); j++) {
//                                authorsArrayList.add(authorsArray.optString(i));
//                            }
//                        }
//                        // Generate ID for the book
//                        String bookId = "book_" + i;
//
//                        // Create a BookInfo object
//                        BookInfo bookInfo = new BookInfo(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
//                        // Set the ID to the BookInfo object
//                        bookInfo.setId(bookId);
//                        // Add the BookInfo object to the ArrayList
//                        bookInfoArrayList.add(bookInfo);
//                    }
//
//                    //below line is use to pass our array list in adapter class.
//                    BookAdapter adapter = new BookAdapter(bookInfoArrayList, BookHome.this);
//                    //below line is use to add linear layout manager for our recycler view.
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookHome.this, RecyclerView.VERTICAL, false);
//                    RecyclerView mRecyclerView = findViewById(R.id.idRVBooks);
//                    //in below line we are setting layout manager and adapter to our recycler view.
//                    mRecyclerView.setLayoutManager(linearLayoutManager);
//                    mRecyclerView.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //displaying a toast message when we get any erro from API
//                    Toast.makeText(BookHome.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, error -> {
//            //also displaying error message in toast.
//            Toast.makeText(BookHome.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
//        });
//        //at last we are adding our json object request in our request queue.
//        queue.add(booksObjrequest);
//    }



    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent fp=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(fp);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }








}




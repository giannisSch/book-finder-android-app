package com.example.relativebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.relativebook.recyclerView.CustomAdapter;
import com.example.relativebook.recyclerView.ItemClickListener;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Object> booksList;
    private CustomAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_circle);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        booksList = new ArrayList<>();
        //recyclewView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CustomAdapter(new ItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                Log.d("TAG", item.toString());
                if (item instanceof Book){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    //intent.putExtra("BranchId", ((Store) item).getId());
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);


        //json
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=+inauthor:keyes&key=AIzaSyB6w-x9GDuaPsRv2DCBQcKuQ8zpVpn3-OQ";
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response.toString());
                        progressBar.setVisibility(View.GONE); // make progress bar invisible
                        try {
                            JSONObject booksObject = new JSONObject(response);
                            addBooksToList(booksObject);
                            adapter.submitList(booksList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Παρακαλούμε ελένξτε την σύνδεση στο Internet", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void addBooksToList(JSONObject booksObject) throws JSONException {
        JSONObject jsonobject;
        Book book;
        String publisher;
        String publishedDate;
        int pageCount;

        JSONArray booksArray = booksObject.getJSONArray("items");
        for(int i = 0; i < booksArray.length(); i++){
            jsonobject = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

            // i want at least those 4 keys in order to show a nice recyclerview
            if (jsonobject.has("title") && jsonobject.has("authors")   && jsonobject.has("imageLinks")
                    && jsonobject.has("description")) {

                if (jsonobject.has("publisher")){
                    publisher = jsonobject.getString("publisher");
                }else{
                    publisher = "";
                }

                if (jsonobject.has("publishedDate")){
                    publishedDate = jsonobject.getString("publishedDate");
                }else{
                    publishedDate = "";
                }

                if (jsonobject.has("pageCount")){
                    pageCount = jsonobject.getInt("pageCount");
                }else{
                    pageCount = 0;
                }

                book = new Book(jsonobject.getString("title"),
                                convertJSONArrayToList(jsonobject.getJSONArray("authors")),
                                publisher,
                                publishedDate,
                                pageCount,
                                jsonobject.getJSONObject("imageLinks").getString("thumbnail"),
                                jsonobject.getString("description"));
                booksList.add(book);
            }
        }
    }

    List convertJSONArrayToList(JSONArray jArray) throws JSONException {
        ArrayList<String> listdata = new ArrayList<String>();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                listdata.add(jArray.getString(i));
            }
        }
        return listdata;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);

        return true;
    }



}
